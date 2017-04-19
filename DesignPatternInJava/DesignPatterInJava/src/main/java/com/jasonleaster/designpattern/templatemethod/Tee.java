package com.jasonleaster.designpattern.templatemethod;

/**
 * Author: jasonleaster
 * Date  : 2017/4/19
 * Email : jasonleaster@gmail.com
 */
public class Tee extends RefreshBeverage {

    @Override
    protected void brew() {
        System.out.println("用80度的热水冲泡茶叶五分钟");
    }

    @Override
    protected void addCondiments() {
        System.out.println("加入柠檬");
    }

    /**
     * 可能有的用户并不希望在茶里面加入柠檬
     *
     * 子类通过覆盖的形式，选择挂载钩子函数
     * 并且实现了一个符合自己需求的实现
     * @return
     */
    @Override
    protected boolean isCustomWantsCondiments() {
        return false;
    }
}
