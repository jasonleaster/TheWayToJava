package org.jasonleaster.utils.common;

import java.util.Map;
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

}
