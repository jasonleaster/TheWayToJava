package com.jasonleaster.designpattern.observer.builtin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Author: Administrator
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public class ConcreteWeatherSubjectTest {

    private ConcreteWeatherSubject subject;

    @Before
    public void before() {
        //创建天气为一个目标，也可以说是**被**观察者
        subject = new ConcreteWeatherSubject();

        ConcreteObserver girl = new ConcreteObserver();
        girl.setObserverName("Girl Friend");

        ConcreteObserver mother = new ConcreteObserver();
        mother.setObserverName("Mother");

        subject.addObserver(girl);
        subject.addObserver(mother);
    }

    @Test
    public void getContent() throws Exception {
        subject.setContent("Old Weather!");

        subject.setContent("New Weather!");
    }

}