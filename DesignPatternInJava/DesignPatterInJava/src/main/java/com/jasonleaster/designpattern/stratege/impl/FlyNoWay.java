package com.jasonleaster.designpattern.stratege.impl;

import com.jasonleaster.designpattern.stratege.FlyingStrategy;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 *     如果突然有一种鸭子需要实现不会飞的需求，创建一种新的 “不会飞”的策略即可
 */
public class FlyNoWay implements FlyingStrategy {

    @Override
    public void performFly() {
        System.out.println("I can't fly");
    }
}
