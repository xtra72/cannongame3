package com.nhnacademy;

import java.util.EventListener;

public interface CollisionEventListener extends EventListener {
    public void collisionEvent(CollisionEvent event);
}
