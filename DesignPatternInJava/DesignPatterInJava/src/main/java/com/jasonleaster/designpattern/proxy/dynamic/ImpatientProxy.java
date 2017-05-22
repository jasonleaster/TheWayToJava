package com.jasonleaster.designpattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: jasonleaster
 * Date  : 2017/5/22
 * Email : jasonleaster@gmail.com
 * Description:
 *      Dynamic Proxy by JDK
 */
public class ImpatientProxy implements InvocationHandler {

    private static final long longTimeThreshold = 1000;

    private Object delegatedObject;

    public ImpatientProxy(Object delegatedObject) {
        this.delegatedObject = delegatedObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        long startTime = System.currentTimeMillis();
        result = method.invoke(delegatedObject, args);
        long endTime   = System.currentTimeMillis();

        if (endTime - startTime > longTimeThreshold){
            System.out.println("It takes " + (endTime - startTime) + " milliseconds to invoke " + method.getName());
        }

        return result;
    }

    public static Object newInstance(Object obj){
        ClassLoader loader = obj.getClass().getClassLoader();
        Class[] interfaces = obj.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, interfaces, new ImpatientProxy(obj));
    }
}
