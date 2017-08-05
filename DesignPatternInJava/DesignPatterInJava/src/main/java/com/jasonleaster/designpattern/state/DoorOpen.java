package com.jasonleaster.designpattern.state;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class DoorOpen extends DoorState {

    public DoorOpen(Door door) {
        super(door);
    }

    public void touch(){
        door.setState(door.STAYOPEN);
    }

    public void timeout(){
        door.setState(door.CLOSING);
    }
}
