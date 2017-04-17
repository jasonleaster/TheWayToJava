package com.jasonleaster.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 目标对象，它知道观察它的观察者，并提供注册（添加）和删除观察者的接口
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public abstract class Subject {

    private List<Observer> observers = new ArrayList<Observer>();

    /**
     * 添加观察者
     * @param observer
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 删除集合中的指定观察者
     * @param observer
     */
    public void detach(Observer observer){
        observers.remove(observer);
    }

    /**
     * 通知所有注册的观察者对象
     *
     * 这里类似于广播的性质，对所有观察者进行信息传递，这种方式并不是最佳的。
     *
     * 如果某些信息只想特定的观察者接受，那么这种实现就会有问题。
     *
     */
    protected void notifyObservers(){
        for(Observer observer : observers){
            observer.update(this);
        }
    }
}
