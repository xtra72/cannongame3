package com.nhnacademy;

import java.util.EventObject;

public class CollisionEvent extends EventObject {
    Object destination;

    public CollisionEvent(Object source, Object destination) {
        super(source);
        this.destination = destination;
    }

    public Object getDestination() {
        return destination;
    }
}
