package org.jasonleaster.utils.cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * Author: jasonleaster
 * Date  : 2017/5/21
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class JedisPoolUtilsTest {

    @Test
    public void setString() throws Exception {
        JedisPoolUtils.getInstance().setString(1, "stringKey", "stringValue");

        String value = JedisPoolUtils.getInstance().getString(1, "stringKey");

        Assert.assertEquals(value, "stringValue");
    }

    @Test
    public void hashSet() throws Exception {
        JedisPoolUtils.getInstance().hashSet(1, "hashKey", "hashField", "hashValue");

        String value = JedisPoolUtils.getInstance().hashGet(1, "hashKey", "hashField");

        Assert.assertEquals(value, "hashValue");
    }

}