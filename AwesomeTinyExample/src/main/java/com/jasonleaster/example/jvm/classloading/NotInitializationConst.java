package com.jasonleaster.example.jvm.classloading;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class NotInitializationConst {

    /**
     * 非主动使用类字段演示
     *
     * 常量在编译阶段会存入调用类的常量池中，本质上没有引用到定义常量的类，
     * 因此不会触发定义常量类的初始化
     *
     * 下面的代码不会输出"ConstClass Init!"
     *
     * 对于常量 ConstClass.HELLOWORLD 的引用实际都被转化为
     * 对NotInitializationConst 类本身常量池的引用，也就是说，
     * 实际上 NotInitializationConst 的Class文件之中并没有ConstClass类的富豪引用入口，
     * 这两个类在编译之后就不存在任何联系了
     */
    public static void main(String[] args){
        System.out.println(ConstClass.HELLOWORLD);
    }
}
