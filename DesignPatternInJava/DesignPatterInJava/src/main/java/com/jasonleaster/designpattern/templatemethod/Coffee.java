package com.jasonleaster.designpattern.templatemethod;

/**
 * Author: jasonleaster
 * Date  : 2017/4/19
 * Email : jasonleaster@gmail.com
 */
public class Coffee extends RefreshBeverage {

    @Override
    protected void brew() {
        System.out.println("用沸水冲泡咖啡");
    }

    protected void addCondiments() {
        System.out.println("加入糖和牛奶");
    }
}
