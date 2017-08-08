package com.jasonleaster.designpattern.stratege;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 *  超类，所有的鸭子都要继承此类
 *  抽象了鸭子的行为: 显示和鸣叫
 */
public abstract class Duck {

    private FlyingStrategy flyingStrategy;

    /**
     * 鸭子发出叫声
     * 通用行为，由超类实现
     */
    public void quack(){
        System.out.println("GuaGuaGua");
    }

    /**
     * 显示鸭子的外观
     * 鸭子的外观各不相同，声明为abstract，由子类实现
     */
    public abstract void display();

    public void fly() {
        flyingStrategy.performFly();
    }

    public FlyingStrategy getFlyingStrategy() {
        return flyingStrategy;
    }

    public void setFlyingStrategy(FlyingStrategy flyingStrategy) {
        this.flyingStrategy = flyingStrategy;
    }
}
