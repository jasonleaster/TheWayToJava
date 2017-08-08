package com.jasonleaster.designpattern.stratege;

import com.jasonleaster.designpattern.stratege.impl.FlyWithWin;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 *  绿色的鸭子
 */
public class MallardDuck extends Duck {

    public MallardDuck() {
        super();
        super.setFlyingStrategy(new FlyWithWin());
    }

    @Override
    public void display() {
        System.out.println("I am Green Duck");
    }
}
