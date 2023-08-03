package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BoundedBox extends MovableBox implements Bounded {
    Rectangle bounds;
    List<Regionable> regionableList;
    boolean enableRegion = true;

    public BoundedBox(Point location, int width, int height, Type type) {
        this(location, width, height);
        this.type = type;
    }

    public BoundedBox(Point location, int width, int height) {
        super(location, width, height);

        regionableList = new ArrayList<>();
        bounds = new Rectangle((int) (getLocation().getX() - getWidth() / 2d),
                (int) (getLocation().getY() - getHeight() / 2d), getWidth(), getHeight());
    }

    public BoundedBox(Point location, int width, int height, Color color, Type type) {
        this(location, width, height, color);
        this.type = type;
    }

    public BoundedBox(Point location, int width, int height, Color color) {
        super(location, width, height, color);
        regionableList = new ArrayList<>();

        bounds = new Rectangle((int) (getLocation().getX() - getWidth() / 2d),
                (int) (getLocation().getY() - getHeight() / 2d), getWidth(), getHeight());
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
            Point newLocation = new Point((int) (getBounds().getMinX() + getWidth() / 2d),
                    (int) getLocation().getY());
            setLocation(newLocation);
            getMotion().turnDX();
        } else if (getBounds().getMaxX() < getRegion().getMaxX()) {
            setLocation(new Point((int) (getBounds().getMaxX() - getWidth() / 2),
                    (int) getLocation().getY()));
            getMotion().turnDX();
        }

        if (getRegion().getMinY() < getBounds().getMinY()) {
            setLocation(new Point((int) getLocation().getX(),
                    (int) (getBounds().getMinY() + getHeight() / 2)));
            getMotion().turnDY();
        } else if (getBounds().getMaxY() < getRegion().getMaxY()) {
            setLocation(new Point((int) getLocation().getX(),
                    (int) (getBounds().getMaxY() - getHeight() / 2)));
            getMotion().turnDY();
        }
    }

    public void addRegion(Regionable regionable) {
        if (enableRegion) {
            synchronized (regionableList) {
                if ((this != regionable) && !regionableList.contains(regionable)) {
                    regionableList.add(regionable);
                }
            }
        }
    }

    public void removeRegion(Regionable regionable) {
        if (enableRegion) {
            synchronized (regionableList) {
                if ((this != regionable) && regionableList.contains(regionable)) {
                    regionableList.remove(regionable);
                }
            }
        }
    }

    public void enableRegion(boolean enable) {
        enableRegion = enable;
    }

    @Override
    public void move() {
        super.move();
        if (isOutOfBounds()) {
            bounce();
        }

        List<Regionable> collisionList = new ArrayList<>();
        synchronized (regionableList) {
            for (Regionable regionable : regionableList) {
                if (getRegion().intersects(regionable.getRegion())) {
                    boolean collision = false;
                    Rectangle intersection = getRegion().intersection(regionable.getRegion());

                    if (intersection.getWidth() != getRegion().getWidth()) {
                        collision = true;
                        if (getRegion().getMinX() < intersection.getMinX()) {
                            setLocation(new Point((int) (intersection.getMinX() - getWidth() / 2),
                                    (int) getLocation().getY()));
                        } else {
                            setLocation(new Point((int) (intersection.getMaxX() + getWidth() / 2),
                                    (int) getLocation().getY()));
                        }
                        getMotion().turnDX();
                    }

                    if (intersection.getHeight() != getRegion().getHeight()) {
                        collision = true;
                        if (getRegion().getMinY() < intersection.getMinY()) {
                            setLocation(new Point((int) getLocation().getX(),
                                    (int) (intersection.getMinY() - getHeight() / 2)));
                        } else {
                            setLocation(new Point((int) getLocation().getX(),
                                    (int) (intersection.getMaxY() + getHeight() / 2)));
                        }
                        getMotion().turnDY();
                    }

                    if (collision) {
                        collisionList.add(regionable);
                    }
                }
            }

            for (Regionable object : collisionList) {
                CollisionEventListener[] listeners = eventListenerList
                        .getListeners(CollisionEventListener.class);
                for (CollisionEventListener listener : listeners) {
                    listener.collisionEvent(new CollisionEvent(this, object));
                }

            }
        }
    }
}
