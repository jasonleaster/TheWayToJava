package com.jasonleaster.designpattern.state;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class DoorClosed extends DoorState{

    public DoorClosed(Door door) {
        super(door);
    }

    @Override
    public void touch() {
        door.setState(door.OPENING);
    }
}
