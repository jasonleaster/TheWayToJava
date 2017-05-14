package org.jasonleaster.seckill.dao;

import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jasonleaster.seckill.model.Stock;
import org.springframework.stereotype.Repository;

@Repository("orderMapper")
public interface OrderMapper {

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Stock queryById(long seckillId);

    /**
     * 根据偏移量产寻秒杀商品列表
     * @param pageInfo 分页信息
     * @return 分页查询后的订单结果
     */
    List<Stock> queryCommodityWithPagination(@Param("pageInfo") PageInfo pageInfo, @Param("sortColumn") String sortColumn);

    /**
     * 减库存
     * @param commodityId 商品id
     * @param payTime     支付时间
     * @return 如果影响行数>1，表示更新的记录行数
     */
    int reduceNumber(@Param("commodityId") long commodityId, @Param("payTime") Date payTime);

    /**
     * 使用存储过程执行秒杀
     * @param paramsMap
     */
    void killByProcedure(Map<String, Object> paramsMap);
}