package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;

public class Ball {
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
}
