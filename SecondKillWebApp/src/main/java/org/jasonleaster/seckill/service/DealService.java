package org.jasonleaster.seckill.service;

import com.github.pagehelper.PageInfo;
import java.util.List;
import org.jasonleaster.seckill.dto.BusinessDealInfo;
import org.jasonleaster.seckill.dto.SecKillExcution;
import org.jasonleaster.seckill.exception.RepeatKillException;
import org.jasonleaster.seckill.exception.SeckillCloseException;
import org.jasonleaster.seckill.exception.SeckillException;
import org.jasonleaster.seckill.model.Stock;

/**
 * 业务接口：站在使用者角度设计接口
 * 三个方面：方法定义粒度，参数，return类型
 * Created by Troy on 2016/10/2.
 */
public interface DealService {
    /**
     * 查询单个秒杀记录
     * @param commodityId 商品Id
     * @return 订单详情
     */
    Stock getById(long commodityId);

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Stock> getDealsList(PageInfo pageInfo, String sortColumn);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 佛祖而输出系统时间和秒杀时间
     * @param commodityId 商品Id
     * @return
     */
    BusinessDealInfo exportDealUrl(long commodityId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SecKillExcution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀操作by 存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SecKillExcution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
