package org.jasonleaster.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jasonleaster.seckill.model.Seckill;
import org.springframework.stereotype.Repository;

@Repository("seckillMapper")
public interface SeckillMapper {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量产寻秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
    //java没有保存形参的记录：queryAll(int offset,int limit)->queryAll(arg0,arg1)

    /**
     * 使用存储过程执行秒杀
     * @param paramsMap
     */
    void killByProcedure(Map<String, Object> paramsMap);
}