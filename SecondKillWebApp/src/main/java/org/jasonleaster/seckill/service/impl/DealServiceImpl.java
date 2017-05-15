package org.jasonleaster.seckill.service.impl;


import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jasonleaster.seckill.dao.OrderMapper;
import org.jasonleaster.seckill.dao.SuccessDealMapper;
import org.jasonleaster.seckill.cache.RedisDao;
import org.jasonleaster.seckill.dto.BusinessDealInfo;
import org.jasonleaster.seckill.dto.SecKillExcution;
import org.jasonleaster.seckill.enums.DealStateEnum;
import org.jasonleaster.seckill.exception.RepeatKillException;
import org.jasonleaster.seckill.exception.SeckillCloseException;
import org.jasonleaster.seckill.exception.SeckillException;
import org.jasonleaster.seckill.model.Stock;
import org.jasonleaster.seckill.model.SuccessDeal;
import org.jasonleaster.seckill.service.DealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service("dealService")
public class DealServiceImpl implements DealService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service依赖
    @Autowired //@Resource, @Inject
    private OrderMapper orderMapper;

    @Autowired
    private SuccessDealMapper successDealMapper;

    @Autowired
    private RedisDao redisDao;

    //md5盐值字符串，用于混淆md5
    private final String slate = "fj556klsdjp559qwd;krp#W%4$^$R##20239u8yrhfiosdh7#@4jheew53";

    @Override
    public Stock getById(long seckillId) {
        return orderMapper.queryById(seckillId);
    }

    @Override
    public List<Stock> getDealsList(PageInfo pageInfo, String sortColumn) {
        List<Stock> stocks = orderMapper.queryCommodityWithPagination(pageInfo, sortColumn);
        return stocks;
    }

    @Override
    public BusinessDealInfo exportDealUrl(long commodityId) {
        //优化点：缓存优化.用户可能一直再刷页面。越热的产品访问越多
        //超时的基础上维护一致性
        //1.访问redis
        Stock stock = redisDao.getOrder(commodityId);
        if (stock == null) {
            //2.访问数据库
            stock = getById(commodityId);
            if (stock == null) {
                return new BusinessDealInfo(false, commodityId);
            } else {
                //3.放入redis
                redisDao.putOrder(stock);
            }
        }

        Date startTime  = stock.getStartTime();
        Date endTime    = stock.getEndTime();
        Date nowTime    = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new BusinessDealInfo(false, commodityId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //转化特定字符串的过程，不可逆
        String md5 = getMD5(commodityId);
        return new BusinessDealInfo(true, md5, commodityId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slate;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    /**
     * 使用注解控制事务方法的有点：
     * 1、开发团队达成一致约定，明确标注事务方法的编程风格
     * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络 操作 RPC/HTTP请求或者剥离到事务方法外部.
     * 3、不是所有的方法都需要事务。如只有一条修改操作，只读操作不需要事务控制
     */
    @Transactional
    @Override
    public SecKillExcution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();
        try {
            //记录购买行为
            int insertCount = successDealMapper.insertSuccessDeal(seckillId, userPhone);
            //唯一：commodityId,userPhone
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            } else {
                //减库存，热点商品竞争
                int updateCount = orderMapper.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新到记录，秒杀结束
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    //秒杀成功
                    SuccessDeal successDeal = successDealMapper.queryByIdWithSeckill(seckillId, userPhone);
                    return new SecKillExcution(seckillId, DealStateEnum.SUCCESS, successDeal);
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译器异常，转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }

    }

    public SecKillExcution executeSeckillProcedure(long commodityId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException {

        if (md5 == null || !md5.equals(getMD5(commodityId))) {
            throw new SeckillException("seckill data rewrite");
        }

        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("commodityId", commodityId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        //执行存储过程，result被赋值
        try {
            orderMapper.killByProcedure(map);
            //获取result
            int result =  (int) map.get("result");
            if (result == 1) {
                SuccessDeal successDeal = successDealMapper.queryByIdWithSeckill(commodityId, userPhone);
                return new SecKillExcution(commodityId, DealStateEnum.SUCCESS, successDeal);
            } else {
                return new SecKillExcution(commodityId, DealStateEnum.stateOf(result));
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return new SecKillExcution(commodityId, DealStateEnum.INNER_ERROR);
        }
    }


}
