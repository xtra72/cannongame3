package com.nhnacademy;

import java.awt.Rectangle;

public class BoundedWorld extends MovableWorld {
    @Override
    public Regionable add(Regionable object) {
        if (object instanceof Bounded) {
            ((Bounded) object)
                    .setBounds(new Rectangle(0, 0, (int) getBounds().getWidth(), (int) getBounds().getHeight()));
        }

        return super.add(object);
    }
}
