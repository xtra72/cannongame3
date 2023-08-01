package com.nhnacademy;

import java.util.EventObject;

public class CollisionEvent extends EventObject {
    Regionable destination;

    public CollisionEvent(Regionable source, Regionable destination) {
        super(source);
        this.destination = destination;
    }

    public Regionable getDestination() {
        return destination;
    }
}
