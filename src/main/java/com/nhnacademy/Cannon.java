package com.nhnacademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cannon implements Regionable, Paintable {
    Rectangle region;
    Motion[] points;
    int angle = 45;

    public Cannon(Point location, int radius) {
        region = new Rectangle((int) location.getX() - radius, (int) location.getY() - radius, 2 * radius, 2 * radius);
        points = new Motion[4];
        points[0] = Motion.createPosition(radius / 4, 10);
        points[1] = Motion.createPosition(radius / 4, -10);
        points[2] = Motion.createPosition(radius, -15);
        points[3] = Motion.createPosition(radius, 15);

        for (Motion point : points) {
            point.addTheta(-angle);
        }
    }

    @Override
    public Rectangle getRegion() {
        return region;
    }

    @Override
    public Type getType() {
        return Type.WALL;
    }

    public void setAngle(int angle) {
        for (Motion point : points) {
            point.addTheta(this.angle);
        }

        this.angle = angle;
        for (Motion point : points) {
            point.addTheta(-this.angle);
        }
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.GREEN);
        int[] xs = { (int) (region.getCenterX() + points[0].getDX()), (int) (region.getCenterX() + points[1].getDX()),
                (int) (region.getCenterX() + points[2].getDX()), (int) (region.getCenterX() + points[3].getDX()) };
        int[] ys = { (int) (region.getCenterY() + points[0].getDY()), (int) (region.getCenterY() + points[1].getDY()),
                (int) (region.getCenterY() + points[2].getDY()), (int) (region.getCenterY() + points[3].getDY()) };
        g.fillOval((int) (region.getCenterX() - region.getWidth() / 4),
                (int) (region.getCenterY() - region.getWidth() / 4), (int) (region.getWidth() / 2),
                (int) (region.getWidth() / 2));
        g.fillPolygon(xs, ys, 4);
    }
}
