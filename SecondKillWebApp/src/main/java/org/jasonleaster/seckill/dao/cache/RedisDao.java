package org.jasonleaster.seckill.dao.cache;

import org.jasonleaster.seckill.model.Order;
import org.jasonleaster.seckill.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Repository
public class RedisDao {

    private static final Logger logger = LoggerFactory.getLogger(RedisDao.class);

    private JedisPool jedisPool;    //redis连接池

    public RedisDao() {}

    public RedisDao(String ip, int port){
        jedisPool = new JedisPool(ip,port);
    }

    /**
     * 从缓存中读取seckill对象
     * @param commodityId
     * @return
     */
    public Order getOrder(long commodityId){
        try{
            //使用了redis的客户端Jedis来操作缓存
            Jedis jedis = jedisPool.getResource();
            try {
                String key= "order:"+ commodityId;

                String jsonstr = jedis.get(key);
                if (jsonstr != null){
                    return (Order) JsonUtil.toObject(jsonstr, Order.class);
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 将数据放入redis缓存
     * @param order
     * @return
     */
    public String putOrder (Order order){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key ="order:"+ order.getOrderId();
                //序列化，第三个参数是缓冲器
                String jsonValue = JsonUtil.toJson(order);
                //超时缓存
                int timeout = 60*60; //1个小时
                String result = jedis.setex(key, timeout, jsonValue);
                return result; //"OK"
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  null;
    }
}
