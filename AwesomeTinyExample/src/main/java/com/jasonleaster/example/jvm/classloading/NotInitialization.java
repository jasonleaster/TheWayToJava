package com.jasonleaster.example.jvm.classloading;

/**
 * Author: jasonleaster
 * Date  : 2017/8/20
 * Email : jasonleaster@gmail.com
 * Description:
 *    下面的代码运行之后只会输出 {@link SuperClass} 中的打印内容"Super Class init"，
 *    对于静态字段，只有直接定义这个字段的类才会被初始化，因此通过其子类来引用父类中定义
 *    的静态字段，只会触发父类的初始化而不会触发子类的初始化，至于是否要触发子类的加载和验证，
 *    在虚拟机规范中并未明确规定，只取决于虚拟机的实现
 */
public class NotInitialization {

    /**
     * 被动使用类字段演示一：
     *  通过子类引用父类的静态字段，可能不会导致子类的初始化
     */
    public static void main(String[] args){
        System.out.println(SubClass.value);
    }
}
