package com.jasonleaster.designpattern.proxy.normal;

/**
 * Author: jasonleaster
 * Date  : 2017/5/22
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class ProxySubject implements OperationInterface {

    private OperationInterface delegate;

    public ProxySubject(OperationInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    public void operation() {
        actionBefore();
        delegate.operation();
        actionAfter();
    }

    private void actionBefore(){
        System.out.println("Action Before delegated object");
    }

    private void actionAfter(){
        System.out.println("Action After delegated object");
    }
}
