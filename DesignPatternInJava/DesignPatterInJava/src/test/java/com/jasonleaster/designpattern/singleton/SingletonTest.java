package com.jasonleaster.designpattern.singleton;

import org.junit.Assert;
import org.junit.Test;

/**
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public class SingletonTest {


    @Test
    public void getInstance() throws Exception {
        Singleton singletonA = Singleton.getInstance();
        Singleton singletonB = Singleton.getInstance();

        Assert.assertEquals(singletonA, singletonB);
    }

}