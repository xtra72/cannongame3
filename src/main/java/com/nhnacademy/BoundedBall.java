package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Function;

public class BoundedBall extends MovableBall implements Bounded {
    Rectangle bounds;
    Function<Movable, Motion> obstacles;

    public BoundedBall(Point location, int radius) {
        super(location, radius);

        bounds = new Rectangle((int) (getLocation().getX() - getRadius()),
                (int) (getLocation().getY() - getRadius()), 2 * getRadius(), 2 * getRadius());
    }

    public BoundedBall(Point location, int radius, Color color) {
        super(location, radius, color);

        bounds = new Rectangle((int) (getLocation().getX() - getRadius()),
                (int) (getLocation().getY() - getRadius()), 2 * getRadius(), 2 * getRadius());
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    boolean isOutOfBounds() {
        Rectangle intersection = bounds.intersection(getRegion());

        return (intersection.getWidth() != getRegion().getWidth())
                || (intersection.getHeight() != getRegion().getHeight());
    }

    void bounce() {
        if ((getRegion().getMinX() < getBounds().getMinX())
                || (getBounds().getMaxX() < getRegion().getMaxX())) {
            getMotion().turnDX();
        }

        if ((getRegion().getMinY() < getBounds().getMinY())
                || (getBounds().getMaxY() < getRegion().getMaxY())) {
            getMotion().turnDY();
        }
    }

    @Override
    public void move() {
        super.move();
        if (isOutOfBounds()) {
            bounce();
        }
        getMotion().set(obstacles.apply(this));
    }

    @Override
    public void setObstacle(Function<Movable, Motion> obstacles) {
        this.obstacles = obstacles;
    }
}
