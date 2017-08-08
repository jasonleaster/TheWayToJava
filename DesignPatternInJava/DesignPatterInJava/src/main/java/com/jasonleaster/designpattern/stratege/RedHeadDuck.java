package com.jasonleaster.designpattern.stratege;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 *  红头鸭
 */
public class RedHeadDuck extends Duck {

    public RedHeadDuck() {
        super();
    }

    @Override
    public void display() {
        System.out.println("I am Red Head Duck");
    }

    @Override
    public void quack() {
        System.out.println("My quack different from others");
    }
}
