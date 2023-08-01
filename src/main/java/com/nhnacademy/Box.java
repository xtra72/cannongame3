package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Box implements Regionable, Paintable {
    static final Color DEFAULT_COLOR = Color.BLUE;
    static int ballCount = 0;
    int id;
    Point location;
    int width;
    int height;
    Color color;
    Type type;

    public Box(Point location, int width, int height) {
        this(location, width, height, DEFAULT_COLOR);
    }

    public Box(Point location, int width, int height, Color color) {
        this.location = location;
        this.width = width;
        this.height = height;
        this.color = color;
        ++ballCount;
        this.id = ballCount;
        type = Type.WALL;
    }

    public int getId() {
        return id;
    }

    public Point getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    void setLocation(Point newLocation) {
        location = newLocation;
    }

    @Override
    public Rectangle getRegion() {
        return new Rectangle((int) location.getX() - width / 2, (int) location.getY() - height / 2, width, height);
    }

    @Override
    public void paint(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(color);
        g.fillOval((int) location.getX() - width / 2, (int) location.getY() - height / 2, width, height);
        g.setColor(oldColor);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
