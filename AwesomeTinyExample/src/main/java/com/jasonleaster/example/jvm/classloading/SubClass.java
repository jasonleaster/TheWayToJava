package com.jasonleaster.example.jvm.classloading;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class SubClass extends SuperClass{
    static {
        System.out.println("SubClass Init");
    }
}

