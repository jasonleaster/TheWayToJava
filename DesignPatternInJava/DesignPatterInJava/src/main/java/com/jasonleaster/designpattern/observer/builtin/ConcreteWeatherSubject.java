package com.jasonleaster.designpattern.observer.builtin;

import java.util.Observable;

/**
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */

//天气目标的具体实现
public class ConcreteWeatherSubject extends Observable {

    //天气情况的内容
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;

        // 注意在通知之前，在用Java的Observer模式时候，下面这句话必不可少
        this.setChanged();

        // 如果是推的方式
        this.notifyObservers(content);

        // 如果是拉的方式没我们就用
        //this.notifyObservers();
    }
}
