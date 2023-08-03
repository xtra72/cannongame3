package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Box implements Regionable, Paintable {
    static final Color DEFAULT_COLOR = Color.BLUE;
    static int ballCount = 0;
    int id;
    Rectangle region;
    Color color;
    Type type;
    Logger logger;
    EventListenerList eventListenerList;

    public Box(Point location, int width, int height) {
        this(location, width, height, DEFAULT_COLOR);
    }

    public Box(Point location, int width, int height, Type type) {
        this(location, width, height, DEFAULT_COLOR, type);
    }

    public Box(Point location, int width, int height, Color color) {
        this.region = new Rectangle((int) location.getX() - width / 2, (int) location.getY() - height / 2,
                width, height);
        this.color = color;
        ++ballCount;
        this.id = ballCount;
        type = Type.WALL;
        logger = LogManager.getLogger(this.getClass().getSimpleName());
        eventListenerList = new EventListenerList();
    }

    public Box(Point location, int width, int height, Color color, Type type) {
        this(location, width, height, color);
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Point getLocation() {
        return new Point((int) (region.getX() + region.getWidth() / 2), (int) (region.getY() + region.getHeight() / 2));
    }

    public int getWidth() {
        return (int) region.getWidth();
    }

    public int getHeight() {
        return (int) region.getHeight();
    }

    public Color getColor() {
        return color;
    }

    void setLocation(Point newLocation) {
        region.setLocation((int) (newLocation.getX() - getWidth() / 2D), (int) (newLocation.getY() - getHeight() / 2D));
    }

    @Override
    public Rectangle getRegion() {
        return region;
    }

    @Override
    public void paint(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillRect((int) region.getX(), (int) region.getY(), (int) region.getWidth(), (int) region.getHeight());
        g.setColor(oldColor);
    }

    public void addCollisionEventListener(CollisionEventListener listener) {
        eventListenerList.add(CollisionEventListener.class, listener);
    }
}
