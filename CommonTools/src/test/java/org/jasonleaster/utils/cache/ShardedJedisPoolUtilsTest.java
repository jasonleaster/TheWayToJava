package org.jasonleaster.utils.cache;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Author: jasonleaster
 * Date  : 2017/5/18
 * Email : jasonleaster@gmail.com
 */
public class ShardedJedisPoolUtilsTest {

    @Test
    public void setString() throws Exception {
        ShardedJedisPoolUtils.getInstance().setString("hello", "world");
    }

    @Test
    public void setString1() throws Exception {
        ShardedJedisPoolUtils.getInstance().setString("GoodBye", "cruel world");
    }

    @Test
    public void setStringIfNotExists() throws Exception {
        ShardedJedisPoolUtils.getInstance().setString("TestStringKey", "TestStringValue");
        ShardedJedisPoolUtils.getInstance().setStringIfNotExists("TestStringKey", "AnotherValue");
        String value = ShardedJedisPoolUtils.getInstance().getString("TestStringKey");
        Assert.assertNotNull(value);
        Assert.assertEquals(value, "TestStringValue");
    }

    @Test
    public void batchStringOperation() throws Exception {
        List<Pair<String, String>> pairs = new LinkedList<Pair<String, String>>();
        pairs.add(new Pair<String, String>("Key1", "value1"));
        pairs.add(new Pair<String, String>("Key2", "value2"));

        List<Object> retVals = ShardedJedisPoolUtils.getInstance().batchSetString(pairs);

        Assert.assertNotNull(retVals);

        List<String> keys = new LinkedList<String>();
        keys.add("Key1");
        keys.add("Key2");

        List<String> retStrs = ShardedJedisPoolUtils.getInstance().batchGetString(keys);

        Assert.assertNotNull(retStrs);
    }
}