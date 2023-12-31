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
    Rectangle region;
    Color color;
    Type type = Type.WALL;
    Logger logger;
    EventListenerList eventListenerList;

    public Ball(Point location, int radius) {
        this(location, radius, DEFAULT_COLOR);
    }

    public Ball(Point location, int radius, Type type) {
        this(location, radius);
        this.type = type;
    }

    public Ball(Point location, int radius, Color color) {
        region = new Rectangle((int) (location.getX() - radius), (int) (location.getY() - radius),
                2 * radius, 2 * radius);
        this.color = color;
        logger = LogManager.getLogger(this.getClass().getSimpleName());
        eventListenerList = new EventListenerList();
        this.id = ++ballCount;
    }

    public Ball(Point location, int radius, Color color, Type type) {
        this(location, radius, color);
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public Point getLocation() {
        return new Point((int) region.getCenterX(), (int) region.getCenterY());
    }

    public int getRadius() {
        return (int) (region.getWidth() / 2);
    }

    public Color getColor() {
        return color;
    }

    void setLocation(Point newLocation) {
        region.translate((int) (newLocation.getX() - region.getCenterX()),
                (int) (newLocation.getY() - region.getCenterY()));
    }

    @Override
    public Rectangle getRegion() {

        return region;
    }

    @Override
    public void paint(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillOval((int) region.getX(), (int) region.getY(), (int) region.getWidth(), (int) region.getHeight());
        g.setColor(oldColor);
    }

    public void addCollisionEventListener(CollisionEventListener listener) {
        eventListenerList.add(CollisionEventListener.class, listener);
    }
}
