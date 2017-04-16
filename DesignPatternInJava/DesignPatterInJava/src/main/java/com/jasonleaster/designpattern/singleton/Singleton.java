package com.jasonleaster.designpattern.singleton;

/**
 * Author: Administrator
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public class Singleton {
    private Singleton(){
    }

    private static final Singleton singleton = new Singleton();

    public static Singleton getInstance(){
        return singleton;
    }
}
