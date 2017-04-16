package com.jasonleaster.designpattern.singleton;

/**
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public class SingletonLazy {
    private SingletonLazy(){
    }

    private static SingletonLazy singleton;

    public static SingletonLazy getInstance(){
        if(singleton == null){
            synchronized (SingletonLazy.class){
                singleton = new SingletonLazy();
            }
        }
        return singleton;
    }
}
