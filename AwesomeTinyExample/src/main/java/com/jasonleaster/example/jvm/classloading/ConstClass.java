package com.jasonleaster.example.jvm.classloading;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ConstClass {
    static{
        System.out.println("ConstClass Init!");
    }

    public static final String HELLOWORLD = "hello world";
}
