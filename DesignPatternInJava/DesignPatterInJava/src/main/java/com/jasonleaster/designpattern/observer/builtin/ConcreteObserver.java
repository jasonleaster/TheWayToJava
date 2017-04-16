package com.jasonleaster.designpattern.observer.builtin;

import java.util.Observable;
import java.util.Observer;

/**
 * Author: Administrator
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public class ConcreteObserver implements Observer {
    // 观察者的名词
    private String observerName;

    public void update(Observable o, Object arg) {
        // 第一种是推的方式
        System.out.println( observerName + "Get the message with push model!" + arg);

        // 第二种是拉的方式
        System.out.println( observerName + "Get the message with pull model!" + ((ConcreteWeatherSubject) o).getContent());

    }

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }
}
