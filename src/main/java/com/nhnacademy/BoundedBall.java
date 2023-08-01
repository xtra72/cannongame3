package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BoundedBall extends MovableBall implements Bounded {
    Rectangle bounds;
    List<Regionable> regionableList;

    public BoundedBall(Point location, int radius) {
        super(location, radius);

        regionableList = new ArrayList<>();
        bounds = new Rectangle((int) (getLocation().getX() - getRadius()),
                (int) (getLocation().getY() - getRadius()), 2 * getRadius(), 2 * getRadius());
    }

    public BoundedBall(Point location, int radius, Color color) {
        super(location, radius, color);
        regionableList = new ArrayList<>();

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

    public void addRegion(Regionable regionable) {
        if ((this != regionable) && !regionableList.contains(regionable)) {
            regionableList.add(regionable);
        }
    }

    @Override
    public void move() {
        super.move();
        if (isOutOfBounds()) {
            bounce();
        }

        for (Regionable regionable : regionableList) {
            if (getRegion().intersects(regionable.getRegion())) {
                Rectangle intersection = getRegion().intersection(regionable.getRegion());

                if (intersection.getWidth() != getRegion().getWidth()) {
                    getMotion().turnDX();
                }

                if (intersection.getHeight() != getRegion().getHeight()) {
                    getMotion().turnDY();
                }
            }
        }
    }
}
