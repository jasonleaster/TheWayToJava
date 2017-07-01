package org.jasonleaster.utils.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Author: jasonleaster
 * Date  : 2017/6/28
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class CommonUtils {

    public static <K,V> V getMapValueWithSupplier(Map<K,V> map, K key, Supplier<V> supplier){
        return map.computeIfAbsent(key, k -> supplier.get());
    }

    public static <K,V> Map<K,V> mergeMapVal(Map<K, V> baseMap, Map<K, V> otherMap, BiFunction<V, V, V> function){
        Map<K,V> result = new HashMap<>(baseMap);

//        otherMap.forEach((key, val) ->
//            {
//                baseMap.computeIfAbsent(key,
//                    k -> result.put(k, val));
//
//                baseMap.computeIfPresent(key,
//                    (k, v) -> result.put(k,
//                        function.apply(v, val)));
//            }
//        );

        otherMap.forEach((key, val) -> result.merge(key, val, function));

        return result;
    }

}
