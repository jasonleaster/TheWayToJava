package com.jasonleaster.designpattern.stratege.impl;

import com.jasonleaster.designpattern.stratege.FlyingStrategy;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class FlyWithWin implements FlyingStrategy {

    @Override
    public void performFly() {
        System.out.println("Flying high");
    }
}
