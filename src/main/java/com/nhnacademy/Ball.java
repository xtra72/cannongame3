package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Ball implements Regionable, Paintable {
    static final Color DEFAULT_COLOR = Color.BLUE;
    Point location;
    int radius;
    Color color;

    public Ball(Point location, int radius) {
        this(location, radius, DEFAULT_COLOR);
    }

    public Ball(Point location, int radius, Color color) {
        this.location = location;
        this.radius = radius;
        this.color = color;
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
}
