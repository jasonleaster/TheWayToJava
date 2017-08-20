package com.jasonleaster.example.jvm.classloading;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 *      运行代码之后会发现并不会输出"SuperClass init！"
 *      说明没有触发类 {@link SuperClass}的初始化阶段
 *
 */
public class NotInitializationArray {
    /**
     * 被动使用类字段演示二：
     *  通过数组定义未引用类，不会导致子类的初始化
     */
    public static void main(String[] args){
        SuperClass[] sca = new SuperClass[10];
    }
}
