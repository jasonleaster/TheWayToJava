package com.jasonleaster.designpattern.observer;

/**
 * Author: jasonleaster
 * Date  : 2017/4/16
 * Email : jasonleaster@gmail.com
 */
public interface Observer {

    /**
     * 更新观察者接口。
     * 观察者模式分为推模型和拉模型，关键就在于该接口的参数。
     * 1.如果是把整个目标对象如本例一样传送给订阅者，让实现Observer接口的
     * 订阅者的update接口内部自己去取所需的数据，则为拉模型。
     * 2.如果是按需，具体的把某一个具体的信息传入update接口(即不是全部的Subject对象)，
     * 则为推模型。
     *
     * 两种模型的比较：
     *      推模型是假定目标对象知道观察者需要的数据，
     *      拉模型是目标对象不知道观察者具体需要什么数据，于是干脆把自身所有的信息
     *      传递给观察者，由观察者来取值。
     *
     *      推模型，可能会造成会是观察者难以服用，而拉模型则不会。
     * @param subject 传入目标对象，方便获取相应的目标对象
     */
    void update(Subject subject);
}
