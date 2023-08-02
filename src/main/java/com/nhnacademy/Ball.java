package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.event.EventListenerList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ball implements Regionable, Paintable {
    static final Color DEFAULT_COLOR = Color.BLUE;
    static int ballCount = 0;
    int id;
    Point location;
    int radius;
    Color color;
    Type type;
    Logger logger;
    EventListenerList eventListenerList;

    public Ball(Point location, int radius) {
        this(location, radius, DEFAULT_COLOR);
    }

    public Ball(Point location, int radius, Color color) {
        this.location = location;
        this.radius = radius;
        this.color = color;
        ++ballCount;
        this.id = ballCount;
        type = Type.WALL;
        logger = LogManager.getLogger(this.getClass().getSimpleName());
        eventListenerList = new EventListenerList();
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
        return location;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    void setLocation(Point newLocation) {
        location = newLocation;
    }

    @Override
    public Rectangle getRegion() {
        return new Rectangle((int) location.getX() - radius, (int) location.getY() - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void paint(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillOval((int) location.getX() - radius, (int) location.getY() - radius, 2 * radius, 2 * radius);
        g.setColor(oldColor);
    }

    public void addCollisionEventListener(CollisionEventListener listener) {
        eventListenerList.add(CollisionEventListener.class, listener);
    }
}
