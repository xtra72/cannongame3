package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;

public class MovableBall extends Ball implements Movable {
    Motion motion = new Motion(0, 0);

    public MovableBall(Point location, int radius) {
        super(location, radius);
    }

    public MovableBall(Point location, int radius, Color color) {
        super(location, radius, color);
    }

    @Override
    public void setMotion(Motion motion) {
        this.motion = motion;
    }

    @Override
    public Motion getMotion() {
        return motion;
    }

    @Override
    public void move() {
        setLocation(new Point((int) (getLocation().getX() + motion.getDX()),
                (int) (getLocation().getY() + motion.getDY())));
    }

    @Override
    public void moveTo(Point location) {
        setLocation(location);
    }

}
