package com.jasonleaster.example.jvm.classloading;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class SuperClass {
    static {
        System.out.println("Super Class init");
    }

    public static int value = 123;
}

