package com.jasonleaster.designpattern.templatemethod;

/**
 * Author: jasonleaster
 * Date  : 2017/4/19
 * Email : jasonleaster@gmail.com
 *
 * 抽象基类，为所有子类提供一个算法框架
 *
 * 提神饮料
 */
public abstract class RefreshBeverage {

    /**
     * 制备饮料的模板方法
     *
     * 封装了所有子类共同转训的算法框架
     */
    public final void prepareBeverageTemplate(){
        //步骤1 将水煮沸
        boilWater();

        //步骤2 泡制饮料
        brew();

        //步骤3 将饮料倒入杯子中
        porInCup();

        if (isCustomWantsCondiments()){
            //步骤4 加入调味料
            addCondiments();
        }

    }

    // 步骤1，3 是无关具体实现的方法，不管什么饮料都可能有这基本的两个步骤
    //步骤1 将水煮沸
    private void boilWater() {
        System.out.println("将水煮沸");
    }

    //步骤3 将饮料倒入杯子中
    private void porInCup(){
        System.out.println("将饮料倒入杯子中");
    }

    //步骤2 泡制饮料, 抽象的基本方法
    protected abstract void brew();


    // 步骤4 加入调味料
    protected abstract void addCondiments();

    /**
     * Hook，钩子函数，提供一个默认或空的实现
     * 具体的子类可以自行决定是否挂钩以及如何实现
     * 询问用户是否加入调料
     */
    protected boolean isCustomWantsCondiments() {
        return true;
    }

}
