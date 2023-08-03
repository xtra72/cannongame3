package com.nhnacademy;

public class Motion {
    double dx;
    double dy;

    Motion() {
        dx = 0;
        dy = 0;
    }

    Motion(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    Motion(Motion other) {
        dx = other.getDX();
        dy = other.getDY();
    }

    public static Motion createPosition(int dx, int dy) {
        return new Motion(dx, dy);
    }

    public static Motion createDisplacement(int magnitude, int theta) {
        return new Motion((int) (magnitude * Math.cos(Math.toRadians(theta))),
                (int) (magnitude * Math.sin(Math.toRadians(theta))));
    }

    public void set(Motion other) {
        dx = other.getDX();
        dy = other.getDY();
    }

    public int getDX() {
        return (int) dx;
    }

    public int getDY() {
        return (int) dy;
    }

    public int getMagnitude() {
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public double getMagnitudeR() {
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public void setMagnitude(int magnitude) {
        double newDx = (magnitude * Math.cos(Math.toRadians(getThetaR())));
        double newDy = (magnitude * Math.sin(Math.toRadians(getThetaR())));

        dx = newDx;
        dy = newDy;
    }

    void setMagnitudeR(double magnitude) {
        double newDx = (magnitude * Math.cos(Math.toRadians(getThetaR())));
        double newDy = (magnitude * Math.sin(Math.toRadians(getThetaR())));

        dx = newDx;
        dy = newDy;
    }

    public int getTheta() {
        return (int) Math.toDegrees(Math.atan2(dy, dx));
    }

    double getThetaR() {
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    public void setTheta(int theta) {
        double newDx = (getMagnitudeR() * Math.cos(Math.toRadians(theta)));
        double newDy = (getMagnitudeR() * Math.sin(Math.toRadians(theta)));

        dx = newDx;
        dy = newDy;
    }

    public void addTheta(int theta) {
        double newDx = (getMagnitudeR() * Math.cos(Math.toRadians(getThetaR() + theta)));
        double newDy = (getMagnitudeR() * Math.sin(Math.toRadians(getThetaR() + theta)));

        dx = newDx;
        dy = newDy;
    }

    public void turnDX() {
        dx = -dx;
    }

    public void turnDY() {
        dy = -dy;
    }

    public Motion add(Motion other) {
        dx += other.dx;
        dy += other.dy;

        return this;
    }

    public void multiply(double value) {
        setMagnitudeR(getMagnitudeR() * value);
    }
}
