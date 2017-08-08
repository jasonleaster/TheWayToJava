package com.jasonleaster.designpattern.stratege;

import com.jasonleaster.designpattern.stratege.impl.FlyNoWay;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class BigYellowDuck extends Duck {

    public BigYellowDuck() {
        super();
        super.setFlyingStrategy(new FlyNoWay());
    }

    @Override
    public void display() {
        System.out.println("My body is very big");
    }
}
