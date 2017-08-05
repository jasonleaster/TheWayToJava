package com.jasonleaster.designpattern.state;

import java.util.Observable;

/**
 * Author: jasonleaster
 * Date  : 2017/8/5
 * Email : jasonleaster@gmail.com
 * Description:
 */
public class Door extends Observable {

    public final DoorState CLOSED   = new DoorClosed(this);
    public final DoorState CLOSING  = new DoorClosing(this);
    public final DoorState OPEN     = new DoorOpen(this);
    public final DoorState OPENING  = new DoorOpening(this);
    public final DoorState STAYOPEN = new DoorStayOpen(this);

    private DoorState state = CLOSED;

    public String status() {
        return state.status();
    }

    public void complete(){
        state.complete();
    }

    public void timeout(){
        state.timeout();
    }

    public void touch(){
       state.touch();
    }

    protected void setState(DoorState doorState){
        this.state = doorState;
        setChanged();
        notifyObservers();
    }
}
