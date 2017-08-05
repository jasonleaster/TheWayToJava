package com.jasonleaster.designpattern.state;



/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class DoorStayOpen extends DoorState{

    public DoorStayOpen(Door door) {
        super(door);
    }

    @Override
    public void touch() {
        door.setState(door.CLOSING);
    }
}
