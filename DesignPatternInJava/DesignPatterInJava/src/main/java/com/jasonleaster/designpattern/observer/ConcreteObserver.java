package com.jasonleaster.designpattern.observer;

/**
 * 具体的观察者对象，实现更新方法，使自身的状态和目标保持一致
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public class ConcreteObserver implements Observer {

    /**
     * 观察者的状态
     */
    private String observerState;

    /**
     * 获取目标类的状态，同步到观察者的状态中
     * @param subject 传入目标对象，方便获取相应的目标对象
     */
    public void update(Subject subject) {
        observerState = ((ConcreteSubject) subject).getSubjectState();
    }
}
