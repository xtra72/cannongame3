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
        if (getRegion().getMinX() < getBounds().getMinX()) {
            Point newLocation = new Point((int) getBounds().getMinX() + getRadius(),
                    (int) getLocation().getY());
            setLocation(newLocation);
            getMotion().turnDX();
        } else if (getBounds().getMaxX() < getRegion().getMaxX()) {
            setLocation(new Point((int) getBounds().getMaxX() - getRadius(),
                    (int) getLocation().getY()));
            getMotion().turnDX();
        }

        if (getRegion().getMinY() < getBounds().getMinY()) {
            setLocation(new Point((int) getLocation().getX(),
                    (int) getBounds().getMinY() + getRadius()));
            getMotion().turnDY();
        } else if (getBounds().getMaxY() < getRegion().getMaxY()) {
            setLocation(new Point((int) getLocation().getX(),
                    (int) getBounds().getMaxY() - getRadius()));
            getMotion().turnDY();
        }
    }

    public void addRegion(Regionable regionable) {
        if ((this != regionable) && !regionableList.contains(regionable)) {
            regionableList.add(regionable);
        }
    }

    public void removeRegion(Regionable regionable) {
        if ((this != regionable) && regionableList.contains(regionable)) {
            regionableList.remove(regionable);
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
                boolean collision = false;
                Rectangle intersection = getRegion().intersection(regionable.getRegion());

                if (intersection.getWidth() != getRegion().getWidth()) {
                    collision = true;
                    getMotion().turnDX();
                }

                if (intersection.getHeight() != getRegion().getHeight()) {
                    collision = true;
                    getMotion().turnDY();
                }

                if (collision) {
                    logger.trace("Collision target!!!");

                    CollisionEventListener[] listeners = eventListenerList.getListeners(CollisionEventListener.class);
                    for (CollisionEventListener listener : listeners) {
                        listener.collisionEvent(new CollisionEvent(this, regionable));
                    }
                }
            }
        }
    }
}
