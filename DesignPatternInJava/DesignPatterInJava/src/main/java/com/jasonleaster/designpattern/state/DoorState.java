package com.jasonleaster.designpattern.state;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public abstract class DoorState {

    protected Door door;

    public abstract void  touch();

    public void complete(){

    }

    public void timeout(){

    }

    public String status(){
        String s = getClass().getName();
        return s.substring(s.lastIndexOf('.') + 1);
    }

    public DoorState(Door door){
        this.door = door;
    }
}
