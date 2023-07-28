package com.nhnacademy;

import java.awt.Rectangle;

public class BoundedWorld extends MovableWorld {
    boolean outOfBounds(Regionable object) {
        Rectangle intersection = getBounds().intersection(object.getRegion());

        return intersection.getWidth() != object.getRegion().getWidth()
                || intersection.getHeight() != object.getRegion().getHeight();
    }

    void bounce(Movable object) {
        if ((object.getRegion().getMinX() < getBounds().getMinX())
                || getBounds().getMaxX() < object.getRegion().getMaxX()) {
            object.getMotion().turnDX();
        }

        if ((object.getRegion().getMinY() < getBounds().getMinY())
                || getBounds().getMaxY() < object.getRegion().getMaxY()) {
            object.getMotion().turnDY();
        }
    }

    @Override
    void move() {
        super.move();

        for (int i = 0; i < getCount(); i++) {
            Regionable object = get(i);
            if ((object instanceof Movable) && outOfBounds(object)) {
                bounce((Movable) object);
            }
        }
    }
}
