package com.jasonleaster.designpattern.proxy.normal;

/**
 * Author: jasonleaster
 * Date  : 2017/5/22
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class RealSubject implements OperationInterface {

    @Override
    public void operation() {
        System.out.println("Operations in concrete class");
    }
}
