package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

public class BoundedBall extends MovableBall implements Bounded {
    Rectangle bounds;
    List<Regionable> regionableList;
    EventListenerList eventListenerList;

    public BoundedBall(Point location, int radius) {
        super(location, radius);

        regionableList = new ArrayList<>();
        eventListenerList = new EventListenerList();
        bounds = new Rectangle((int) (getLocation().getX() - getRadius()),
                (int) (getLocation().getY() - getRadius()), 2 * getRadius(), 2 * getRadius());
    }

    public BoundedBall(Point location, int radius, Color color) {
        super(location, radius, color);

        regionableList = new ArrayList<>();
        eventListenerList = new EventListenerList();
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
            setLocation(new Point((int) (getBounds().getMinX() + getRadius()), (int) getLocation().getY()));
            getMotion().turnDX();
        } else if (getBounds().getMaxX() < getRegion().getMaxX()) {
            setLocation(new Point((int) (getBounds().getMaxX() - getRadius()), (int) getLocation().getY()));
            getMotion().turnDX();
        }

        if (getRegion().getMinY() < getBounds().getMinY()) {
            setLocation(new Point((int) getLocation().getX(), (int) (getBounds().getMinY() + getRadius())));
            getMotion().turnDY();
        } else if (getBounds().getMaxY() < getRegion().getMaxY()) {
            setLocation(new Point((int) getLocation().getX(), (int) (getBounds().getMaxY() - getRadius())));
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

        for (Regionable region : regionableList) {
            if (getRegion().intersects(region.getRegion())) {
                boolean collisionDetected = false;
                Rectangle intersection = getRegion().intersection(region.getRegion());

                if (intersection.getWidth() != getRegion().getWidth()) {
                    getMotion().turnDX();
                    if (getRegion().getMinX() < intersection.getMinX()) {
                        setLocation(
                                new Point((int) (intersection.getMinX() - getRadius()), (int) getLocation().getY()));
                    } else {
                        setLocation(
                                new Point((int) (intersection.getMaxX() + getRadius()), (int) getLocation().getY()));
                    }
                    collisionDetected = true;
                }

                if (intersection.getHeight() != getRegion().getHeight()) {
                    getMotion().turnDY();
                    if (getRegion().getMinY() < intersection.getMinY()) {
                        setLocation(
                                new Point((int) getLocation().getX(), (int) (intersection.getMinY() - getRadius())));
                    } else {
                        setLocation(
                                new Point((int) getLocation().getX(), (int) (intersection.getMaxY() + getRadius())));
                    }
                    collisionDetected = true;
                }

                if (collisionDetected) {
                    CollisionEventListener[] listeners = eventListenerList.getListeners(CollisionEventListener.class);
                    for (CollisionEventListener listener : listeners) {
                        listener.collisionEvent(new CollisionEvent(this, region));
                    }
                }
            }
        }
    }

    @Override
    public void addCollisionEventListener(CollisionEventListener listener) {
        eventListenerList.add(CollisionEventListener.class, listener);
    }
}
