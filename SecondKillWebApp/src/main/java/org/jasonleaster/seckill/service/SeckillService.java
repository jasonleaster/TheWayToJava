package org.jasonleaster.seckill.service;

import java.util.List;
import org.jasonleaster.seckill.dto.Exposer;
import org.jasonleaster.seckill.dto.SecKillExcution;
import org.jasonleaster.seckill.exception.RepeatKillException;
import org.jasonleaster.seckill.exception.SeckillCloseException;
import org.jasonleaster.seckill.exception.SeckillException;
import org.jasonleaster.seckill.model.Seckill;

/**
 * 业务接口：站在使用者角度设计接口
 * 三个方面：方法定义粒度，参数，return类型
 * Created by Troy on 2016/10/2.
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 佛祖而输出系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

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