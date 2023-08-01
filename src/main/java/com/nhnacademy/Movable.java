package com.nhnacademy;

import java.awt.Point;
import java.util.function.Supplier;

public interface Movable extends Regionable, Runnable {
    public void setMotion(Motion motion);

    public Motion getMotion();

    public void move();

    public void moveTo(Point location);

    public long getDT();

    public void setDT(long dt);

    public void start();

    public void stop();

    public void addEffect(Supplier<Motion> effects);
}
