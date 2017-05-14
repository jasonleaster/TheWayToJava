package org.jasonleaster.seckill.cache;

import org.jasonleaster.seckill.model.Stock;
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
    public Stock getOrder(long commodityId){
        try{
            //使用了redis的客户端Jedis来操作缓存
            Jedis jedis = jedisPool.getResource();
            try {
                String key= "stock:"+ commodityId;

                String jsonstr = jedis.get(key);
                if (jsonstr != null){
                    return (Stock) JsonUtil.toObject(jsonstr, Stock.class);
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
     * @param stock
     * @return
     */
    public String putOrder (Stock stock){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key ="stock:"+ stock.getCommodityId();
                //序列化，第三个参数是缓冲器
                String jsonValue = JsonUtil.toJson(stock);
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
