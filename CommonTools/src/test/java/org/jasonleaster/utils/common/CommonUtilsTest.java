package org.jasonleaster.utils.common;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Author: jasonleaster
 * Date  : 2017/6/30
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class CommonUtilsTest {

    @Test
    public void mergeMapVal() throws Exception {
        Map<Integer, Integer> mapA = new HashMap<>();
        Map<Integer, Integer> mapB = new HashMap<>();

        mapA.put(1,1);
        mapA.put(2,2);

        mapB.put(2,2);
        mapB.put(3,3);

        CommonUtils.mergeMapVal(mapA, mapB, Integer::sum);
    }

}