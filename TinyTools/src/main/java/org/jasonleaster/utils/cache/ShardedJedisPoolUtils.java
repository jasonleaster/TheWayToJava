package org.jasonleaster.utils.cache;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jasonleaster.utils.io.Resources;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * Author: jasonleaster
 * Date  : 2017/5/9
 * Email : jasonleaster@gmail.com
 *
 * 从配置文件读取Redis的配置信息
 * //TODO 内存数据库Redis的辅助类，负责对内存数据库的所有操作
 */
public final class ShardedJedisPoolUtils {

    private static final Logger log = Logger.getLogger(ShardedJedisPoolUtils.class);

    private static ShardedJedisPoolUtils instance;

    // 数据源
    private static ShardedJedisPool shardedJedisPool;

    public static ShardedJedisPoolUtils getInstance() throws IOException{

        if(instance != null){
            return instance;
        }

        instance = new ShardedJedisPoolUtils();

        Properties redisProperty = Resources.getResourceAsProperties("sharedredis.properties");

        int maxActive = Integer.valueOf(redisProperty.getProperty("redis.pool.maxActive"));
        int maxIdle = Integer.valueOf(redisProperty.getProperty("redis.pool.maxIdle"));
        long maxWait = Integer.valueOf(redisProperty.getProperty("redis.pool.maxWait"));
        String[] ips = redisProperty.getProperty("redis.ips").split(",");

        if (maxActive > 0 && maxIdle > 0 && maxWait > 0 && ips.length > 0) {
            // 创建jedis池配置实例
            JedisPoolConfig config = new JedisPoolConfig();

            // 设置池配置项值
            config.setMaxTotal(maxActive);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWait);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            // 创建多个redis共享服务，分布式时
            List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
            for (String ip : ips) {
                JedisShardInfo jedisShardInfo = new JedisShardInfo(ip.split(":")[0],
                    Integer.valueOf(ip.split(":")[1]));
                list.add(jedisShardInfo);
            }
            // 根据配置文件,创建shared池实例
            shardedJedisPool = new ShardedJedisPool(config, list);
        }

        return instance;
    }

    /**
     * 执行器， 它保证在执行操作之后释放数据源returnResource(jedis)
     */
    private abstract class Executor<T> {

        ShardedJedis jedis;

        ShardedJedisPool shardedJedisPool;

        Executor(ShardedJedisPool shardedJedisPool) {
            this.shardedJedisPool = shardedJedisPool;
            jedis = this.shardedJedisPool.getResource();
        }

        /**
         * 回调
         *
         * @return 执行结果
         */
        abstract T execute();

        /**
         * 调用{@link #execute()}并返回执行结果 它保证在执行{@link #execute()}  jedis.close()
         *
         * @return 执行结果
         */
        public T getResult() {
            T result = null;
            try {
                result = execute();
            } catch (Throwable e) {
                if (null != jedis) {
                    jedis.close();
                }
                throw new RuntimeException("Redis execute exception", e);
            } finally {
                if (null != jedis) {
                    jedis.close();
                }
            }
            return result;
        }
    }

    /* ======================================Strings====================================== */

    /**
     * 将字符串值 value 关联到 key 。 如果 key 已经持有其他值， setString 就覆写旧值，无视类型。
     * 对于某个原本带有生存时间（TTL）的键来说， 当setString 成功在这个键上执行时， 这个键原有的
     * TTL 将被清除。 时间复杂度：O(1)
     *
     * @param key key
     * @param value string value
     * @return 在设置操作成功完成时，才返回 OK 。
     */
    public String setString(final String key, final String value) {
        return new Executor<String>(shardedJedisPool) {
            @Override
            String execute() {
                return jedis.set(key, value);
            }
        }.getResult();
    }

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 expire (以秒为单位)。 如果 key 已经存在， 将覆写旧值。 类似于以下两个命令: SET key value
     * EXPIRE key expire # 设置生存时间 不同之处是这个方法是一个原子性(atomic)操作，关联值和设置生存时间两个动作会在同一时间内完成，在 Redis
     * 用作缓存时，非常实用。 时间复杂度：O(1)
     *
     * @param key key
     * @param value string value
     * @param expire 生命周期
     * @return 设置成功时返回 OK 。当 expire 参数不合法时，返回一个错误。
     */
    public String setString(final String key, final String value, final int expire) {
        return new Executor<String>(shardedJedisPool) {
            @Override
            String execute() {
                return jedis.setex(key, expire, value);
            }
        }.getResult();
    }
}
//
//    /**
//     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。 时间复杂度：O(1)
//     *
//     * @param key key
//     * @param value string value
//     * @return 设置成功，返回 1 。设置失败，返回 0 。
//     */
//    public Long setStringIfNotExists(final String key, final String value) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.setnx(key, value);
//            }
//        }.getResult();
//    }
//
//    /**
//     * //TODO 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。 时间复杂度：O(1)
//     */
//    public String setObject(final String key, final Serializable value) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                return jedis.set(SafeEncoder.encode(key), SerializeUtil.serialize(value));
//            }
//        }.getResult();
//    }
//
//    /**
//     * //TODO 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 setStringIfNotExists 不做任何动作。 时间复杂度：O(1)
//     */
//    public String setObject(final String key, final Serializable value, final int expire) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                return jedis.setex(key.getBytes(), expire, SerializeUtil.serialize(value));
//            }
//        }.getResult();
//    }
//
//    /**
//     * //TODO 添加方法功能描述
//     */
//    public Long hset(final String key, final String field, final Serializable value) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
//            }
//        }.getResult();
//    }
//
//    public Long hset(final String key, final String field, final Serializable value,
//        final int expire) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<Long> result = pipeline
//                    .hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。 假如 key 储存的值不是字符串类型，返回一个错误，因为 getString 只能用于处理字符串值。
//     * 时间复杂度: O(1)
//     *
//     * @param key key
//     * @return 当 key 不存在时，返回 nil ，否则，返回 key 的值。如果 key 不是字符串类型，那么返回一个错误。
//     */
//    public String getString(final String key) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                return jedis.get(key);
//            }
//        }.getResult();
//    }
//
//    public <T extends Serializable> T get(final String key) {
//        return new Executor<T>(shardedJedisPool) {
//
//            @SuppressWarnings("unchecked")
//            @Override
//            T execute() {
//                Object result = null;
//                byte[] retVal = jedis.get(SafeEncoder.encode(key));
//                if (null != retVal) {
//                    try {
//                        result = SerializeUtil.unserialize(retVal);
//                    } catch (Exception e) {
//                        result = SafeEncoder.encode(retVal);
//                    }
//                }
//                return (T) result;
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的 {@link #setString(String, String)}
//     *
//     * @param pairs 键值对数组{数组第一个元素为key，第二个元素为value}
//     * @return 操作状态的集合
//     */
//    public List<Object> batchSetString(final List<Pair<String, String>> pairs) {
//        return new Executor<List<Object>>(shardedJedisPool) {
//
//            @Override
//            List<Object> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//                for (Pair<String, String> pair : pairs) {
//                    pipeline.set(pair.getKey(), pair.getValue());
//                }
//                return pipeline.syncAndReturnAll();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的 {@link #getString(String)}
//     *
//     * @param keys key数组
//     * @return value的集合
//     */
//    public List<String> batchGetString(final String[] keys) {
//        return new Executor<List<String>>(shardedJedisPool) {
//
//            @Override
//            List<String> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//                List<String> result = new ArrayList<String>(keys.length);
//                List<Response<String>> responses = new ArrayList<Response<String>>(keys.length);
//                for (String key : keys) {
//                    responses.add(pipeline.get(key));
//                }
//                pipeline.sync();
//                for (Response<String> resp : responses) {
//                    result.add(resp.get());
//                }
//                return result;
//            }
//        }.getResult();
//    }
//
//    /* ======================================Hashes====================================== */
//
//    /**
//     * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。 如果域 field
//     * 已经存在于哈希表中，旧值将被覆盖。 时间复杂度: O(1)
//     *
//     * @param key key
//     * @param field 域
//     * @param value string value
//     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
//     */
//    public Long hashSet(final String key, final String field, final String value) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.hset(key, field, value);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 hashSet 操作。 如果域 field
//     * 已经存在于哈希表中，旧值将被覆盖。
//     *
//     * @param key key
//     * @param field 域
//     * @param value string value
//     * @param expire 生命周期，单位为秒
//     * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
//     */
//    public Long hashSet(final String key, final String field, final String value,
//        final int expire) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<Long> result = pipeline.hset(key, field, value);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回哈希表 key 中给定域 field 的值。 时间复杂度:O(1)
//     *
//     * @param key key
//     * @param field 域
//     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
//     */
//    public String hashGet(final String key, final String field) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                return jedis.hget(key, field);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回哈希表 key 中给定域 field 的值。 如果哈希表 key 存在，同时设置这个 key 的生存时间
//     *
//     * @param key key
//     * @param field 域
//     * @param expire 生命周期，单位为秒
//     * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
//     */
//    public String hashGet(final String key, final String field, final int expire) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<String> result = pipeline.hget(key, field);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 时间复杂度: O(N) (N为fields的数量)
//     *
//     * @param key key
//     * @param hash field-value的map
//     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
//     */
//    public String hashMultipleSet(final String key, final Map<String, String> hash) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                return jedis.hmset(key, hash);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。同时设置这个 key 的生存时间
//     *
//     * @param key key
//     * @param hash field-value的map
//     * @param expire 生命周期，单位为秒
//     * @return 如果命令执行成功，返回 OK 。当 key 不是哈希表(hash)类型时，返回一个错误。
//     */
//    public String hashMultipleSet(final String key, final Map<String, String> hash,
//        final int expire) {
//        return new Executor<String>(shardedJedisPool) {
//
//            @Override
//            String execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<String> result = pipeline.hmset(key, hash);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。 时间复杂度: O(N) (N为fields的数量)
//     *
//     * @param key key
//     * @param fields field的数组
//     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
//     */
//    public List<String> hashMultipleGet(final String key, final String... fields) {
//        return new Executor<List<String>>(shardedJedisPool) {
//
//            @Override
//            List<String> execute() {
//                return jedis.hmget(key, fields);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回哈希表 key 中，一个或多个给定域的值。如果给定的域不存在于哈希表，那么返回一个 nil 值。 同时设置这个 key 的生存时间
//     *
//     * @param key key
//     * @param fields field的数组
//     * @param expire 生命周期，单位为秒
//     * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
//     */
//    public List<String> hashMultipleGet(final String key, final int expire,
//        final String... fields) {
//        return new Executor<List<String>>(shardedJedisPool) {
//
//            @Override
//            List<String> execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<List<String>> result = pipeline.hmget(key, fields);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的{@link #hashMultipleSet(String, Map)}，在管道中执行
//     *
//     * @param pairs 多个hash的多个field
//     * @return 操作状态的集合
//     */
//    public List<Object> batchHashMultipleSet(final List<Pair<String, Map<String, String>>> pairs) {
//        return new Executor<List<Object>>(shardedJedisPool) {
//
//            @Override
//            List<Object> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//                for (Pair<String, Map<String, String>> pair : pairs) {
//                    pipeline.hmset(pair.getKey(), pair.getValue());
//                }
//                return pipeline.syncAndReturnAll();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的{@link #hashMultipleSet(String, Map)}，在管道中执行
//     *
//     * @param data Map<String, Map<String, String>>格式的数据
//     * @return 操作状态的集合
//     */
//    public List<Object> batchHashMultipleSet(final Map<String, Map<String, String>> data) {
//        return new Executor<List<Object>>(shardedJedisPool) {
//
//            @Override
//            List<Object> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//                for (Map.Entry<String, Map<String, String>> iter : data.entrySet()) {
//                    pipeline.hmset(iter.getKey(), iter.getValue());
//                }
//                return pipeline.syncAndReturnAll();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的{@link #hashMultipleGet(String, String...)}，在管道中执行
//     *
//     * @param pairs 多个hash的多个field
//     * @return 执行结果的集合
//     */
//    public List<List<String>> batchHashMultipleGet(final List<Pair<String, String[]>> pairs) {
//        return new Executor<List<List<String>>>(shardedJedisPool) {
//
//            @Override
//            List<List<String>> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//                List<List<String>> result = new ArrayList<List<String>>(pairs.size());
//                List<Response<List<String>>> responses = new ArrayList<Response<List<String>>>(
//                    pairs.size());
//                for (Pair<String, String[]> pair : pairs) {
//                    responses.add(pipeline.hmget(pair.getKey(), pair.getValue()));
//                }
//                pipeline.sync();
//                for (Response<List<String>> resp : responses) {
//                    result.add(resp.get());
//                }
//                return result;
//            }
//        }.getResult();
//
//    }
//
//    /**
//     * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。 时间复杂度: O(N)
//     *
//     * @param key key
//     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
//     */
//    public Map<String, String> hashGetAll(final String key) {
//        return new Executor<Map<String, String>>(shardedJedisPool) {
//
//            @Override
//            Map<String, String> execute() {
//                return jedis.hgetAll(key);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回哈希表 key 中，所有的域和值。在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。 同时设置这个 key 的生存时间
//     *
//     * @param key key
//     * @param expire 生命周期，单位为秒
//     * @return 以列表形式返回哈希表的域和域的值。若 key 不存在，返回空列表。
//     */
//    public Map<String, String> hashGetAll(final String key, final int expire) {
//        return new Executor<Map<String, String>>(shardedJedisPool) {
//
//            @Override
//            Map<String, String> execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<Map<String, String>> result = pipeline.hgetAll(key);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 获取hash的field数量
//     */
//    public Long hashLen(final String key) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.hlen(key);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 获取hash的field数量
//     */
//    public Long hashLen(final String key, final int expire) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<Long> result = pipeline.hlen(key);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回 hash 的所有 field
//     */
//    public Set<String> hashKeys(final String key) {
//        return new Executor<Set<String>>(shardedJedisPool) {
//
//            @Override
//            Set<String> execute() {
//                return jedis.hkeys(key);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 返回 hash 的所有 field
//     */
//    public Set<String> hashKeys(final String key, final int expire) {
//        return new Executor<Set<String>>(shardedJedisPool) {
//
//            @Override
//            Set<String> execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<Set<String>> result = pipeline.hkeys(key);
//                pipeline.expire(key, expire);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的{@link #hashGetAll(String)}
//     *
//     * @param keys key的数组
//     * @return 执行结果的集合
//     */
//    public List<Map<String, String>> batchHashGetAll(final String... keys) {
//        return new Executor<List<Map<String, String>>>(shardedJedisPool) {
//
//            @Override
//            List<Map<String, String>> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//                List<Map<String, String>> result = new ArrayList<Map<String, String>>(keys.length);
//                List<Response<Map<String, String>>> responses = new ArrayList<Response<Map<String, String>>>(
//                    keys.length);
//                for (String key : keys) {
//                    responses.add(pipeline.hgetAll(key));
//                }
//                pipeline.sync();
//                for (Response<Map<String, String>> resp : responses) {
//                    result.add(resp.get());
//                }
//                return result;
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的{@link #hashMultipleGet(String, String...)}，与{@link #batchHashGetAll(String...)}不同的是，返回值为Map类型
//     *
//     * @param keys key的数组
//     * @return 多个hash的所有filed和value
//     */
//    public Map<String, Map<String, String>> batchHashGetAllForMap(final String... keys) {
//        return new Executor<Map<String, Map<String, String>>>(shardedJedisPool) {
//
//            @Override
//            Map<String, Map<String, String>> execute() {
//                ShardedJedisPipeline pipeline = jedis.pipelined();
//
//                // 设置map容量防止rehash
//                int capacity = 1;
//                while ((int) (capacity * 0.75) <= keys.length) {
//                    capacity <<= 1;
//                }
//                Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>(
//                    capacity);
//                List<Response<Map<String, String>>> responses = new ArrayList<Response<Map<String, String>>>(
//                    keys.length);
//                for (String key : keys) {
//                    responses.add(pipeline.hgetAll(key));
//                }
//                pipeline.sync();
//                for (int i = 0; i < keys.length; ++i) {
//                    result.put(keys[i], responses.get(i).get());
//                }
//                return result;
//            }
//        }.getResult();
//    }
//
//    /* ======================================List====================================== */
//
//    /**
//     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
//     *
//     * @param key key
//     * @param values value的数组
//     * @return 执行 listPushTail 操作后，表的长度
//     */
//    public Long listPushTail(final String key, final String... values) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.rpush(key, values);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 将一个或多个值 value 插入到列表 key 的表头
//     *
//     * @param key key
//     * @param value string value
//     * @return 执行 listPushHead 命令后，列表的长度。
//     */
//    public Long listPushHead(final String key, final String... values) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.lpush(key, values);
//            }
//        }.getResult();
//    }
//
//    /**
//     * list长度
//     */
//    public Long listLen(final String key) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.llen(key);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 将一个或多个值 value 插入到列表 key 的表头, 当列表大于指定长度是就对列表进行修剪(trim)
//     *
//     * @param key key
//     * @param value string value
//     * @param size 链表超过这个长度就修剪元素
//     * @return 执行 listPushHeadAndTrim 命令后，列表的长度。
//     */
//    public Long listPushHeadAndTrim(final String key, final String value, final long size) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                Pipeline pipeline = jedis.getShard(key).pipelined();
//                Response<Long> result = pipeline.lpush(key, value);
//                // 修剪列表元素, 如果 size - 1 比 end 下标还要大，Redis将 size 的值设置为 end 。
//                pipeline.ltrim(key, 0, size - 1);
//                pipeline.sync();
//                return result.get();
//            }
//        }.getResult();
//    }
//
//    /**
//     * 移除从表尾到表头，第一个 value
//     */
//    public Long listDel(final String key, final String value, final int index) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.lrem(key, index, value);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 批量的{@link #listPushTail(String, String...)}，以锁的方式实现
//     *
//     * @param key key
//     * @param values value的数组
//     * @param delOld 如果key存在，是否删除它。true 删除；false: 不删除，只是在行尾追加
//     */
//    public void batchListPushTail(final String key, final String[] values, final boolean delOld) {
//        new Executor<Object>(shardedJedisPool) {
//
//            @Override
//            Object execute() {
//                if (delOld) {
//                    RedisLock lock = new RedisLock(key, shardedJedisPool);
//                    lock.lock();
//                    try {
//                        Pipeline pipeline = jedis.getShard(key).pipelined();
//                        pipeline.del(key);
//                        for (String value : values) {
//                            pipeline.rpush(key, value);
//                        }
//                        pipeline.sync();
//                    } finally {
//                        lock.unlock();
//                    }
//                } else {
//                    jedis.rpush(key, values);
//                }
//                return null;
//            }
//        }.getResult();
//    }
//
//    /**
//     * 同{@link #batchListPushTail(String, String[], boolean)},不同的是利用redis的事务特性来实现
//     *
//     * @param key key
//     * @param values value的数组
//     * @return null
//     */
//    public Object updateListInTransaction(final String key, final List<String> values) {
//        return new Executor<Object>(shardedJedisPool) {
//
//            @Override
//            Object execute() {
//                Transaction transaction = jedis.getShard(key).multi();
//                transaction.del(key);
//                for (String value : values) {
//                    transaction.rpush(key, value);
//                }
//                transaction.exec();
//                return null;
//            }
//        }.getResult();
//    }
//
//    /**
//     * 在key对应list的尾部部添加字符串元素,如果key存在，什么也不做
//     *
//     * @param key key
//     * @param values value的数组
//     * @return 执行insertListIfNotExists后，表的长度
//     */
//    public Long insertListIfNotExists(final String key, final String[] values) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                RedisLock lock = new RedisLock(key, shardedJedisPool);
//                lock.lock();
//                try {
//                    if (!jedis.exists(key)) {
//                        return jedis.rpush(key, values);
//                    }
//                } finally {
//                    lock.unlock();
//                }
//
//            }
//        }
//    }
//
//
//    // ==================
//
//    /**
//     * 删除模糊匹配的key
//     *
//     * @param likeKey 模糊匹配的key
//     * @return 删除成功的条数
//     */
//    public long delKeysLike(final String likeKey) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                Collection<Jedis> jedisC = jedis.getAllShards();
//                Iterator<Jedis> iter = jedisC.iterator();
//                long count = 0;
//                while (iter.hasNext()) {
//                    Jedis _jedis = iter.next();
//                    Set<String> keys = _jedis.keys(likeKey + "*");
//                    count += _jedis.del(keys.toArray(new String[keys.size()]));
//                }
//                return count;
//            }
//        }.getResult();
//    }
//
//    /**
//     * 删除
//     *
//     * @param key 匹配的key
//     * @return 删除成功的条数
//     */
//    public Long delKey(final String key) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.del(key);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 删除
//     *
//     * @param key 匹配的key
//     * @return 删除成功的条数
//     */
//    public Long delKey(final String key, final String... fileds) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.hdel(key, fileds);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 删除
//     *
//     * @param keys 匹配的key的集合
//     * @return 删除成功的条数
//     */
//    public Long delKeys(final String[] keys) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                Collection<Jedis> jedisC = jedis.getAllShards();
//                Iterator<Jedis> iter = jedisC.iterator();
//                long count = 0;
//                while (iter.hasNext()) {
//                    Jedis _jedis = iter.next();
//                    count += _jedis.del(keys);
//                }
//                return count;
//            }
//        }.getResult();
//    }
//
//    /**
//     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 在 Redis 中，带有生存时间的 key 被称为『可挥发』(volatile)的。
//     *
//     * @param key key
//     * @param expire 生命周期，单位为秒
//     * @return 1: 设置成功 0: 已经超时或key不存在
//     */
//    public Long expire(final String key, final int expire) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                return jedis.expire(key, expire);
//            }
//        }.getResult();
//    }
//
//    /**
//     * 一个跨jvm的id生成器，利用了redis原子性操作的特点
//     *
//     * @param key id的key
//     * @return 返回生成的Id
//     */
//    public long makeId(final String key) {
//        return new Executor<Long>(shardedJedisPool) {
//
//            @Override
//            Long execute() {
//                long id = jedis.incr(key);
//                if ((id + 75807) >= Long.MAX_VALUE) {
//                    // 避免溢出，重置，getSet命令之前允许incr插队，75807就是预留的插队空间
//                    jedis.getSet(key, "0");
//                }
//                return id;
//            }
//        }.getResult();
//    }
//
//    public boolean exists(final String key) {
//        return new Executor<Boolean>(shardedJedisPool) {
//
//            @Override
//            Boolean execute() {
//                return jedis.exists(key);
//            }
//        }.getResult();
//    }
//
//    public boolean exists(final String key, final String field) {
//        return new Executor<Boolean>(shardedJedisPool) {
//
//            @Override
//            Boolean execute() {
//                return jedis.hexists(key, field);
//            }
//        }.getResult();
//    }
//
//
//    /**
//     * 删除模糊匹配的key
//     *
//     * @param likeKey 模糊匹配的key
//     * @return 删除成功的条数
//     */
//    public Set<String> getKeyLike(final String likeKey) {
//        return new Executor<Set<String>>(shardedJedisPool) {
//
//            @Override
//            Set<String> execute() {
//                Collection<Jedis> jedisC = jedis.getAllShards();
//                Iterator<Jedis> iter = jedisC.iterator();
//                Set<String> keys = null;
//                while (iter.hasNext()) {
//                    Jedis _jedis = iter.next();
//                    keys = _jedis.keys(likeKey + "*");
//                }
//                return keys;
//            }
//        }.getResult();
//    }
//}