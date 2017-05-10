package org.jasonleaster.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.jasonleaster.seckill.model.SuccessDeal;
import org.springframework.stereotype.Repository;

@Repository("successDealMapper")
public interface SuccessDealMapper {
    /**
     * 插入购买明细，可过滤重复
     * @param commodityId 商品Id
     * @param userPhone   用户手机账号
     * @return 插入的行数
     */
    int insertSuccessDeal(@Param("commodityId") long commodityId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param commodityId 商品Id
     * @param userPhone   用户手机号
     * @return
     */
    SuccessDeal queryByIdWithSeckill(@Param("commodityId") long commodityId,
        @Param("userPhone") long userPhone);
}