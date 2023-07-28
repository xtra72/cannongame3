package com.nhnacademy;

import java.awt.Point;

public interface Movable {
    public void setMotion(Motion motion);

    public Motion getMotion();

    public void move();

    public void moveTo(Point location);
}
