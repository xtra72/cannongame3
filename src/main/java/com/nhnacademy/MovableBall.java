package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.util.function.Supplier;

public class MovableBall extends Ball implements Movable {
    Motion motion = new Motion(0, 0);
    Thread thread;
    long dt = 0;
    MovableWorld world;
    Supplier<Motion> effects;

    public MovableBall(Point location, int radius) {
        super(location, radius);
        thread = new Thread(this, this.getClass().getSimpleName() + "-" + getId());
    }

    public MovableBall(Point location, int radius, Color color) {
        super(location, radius, color);
        thread = new Thread(this, this.getClass().getSimpleName() + "-" + getId());
    }

    @Override
    public void setMotion(Motion motion) {
        this.motion = motion;
    }

    @Override
    public Motion getMotion() {
        return motion;
    }

    @Override
    public void move() {
        setLocation(new Point((int) (getLocation().getX() + motion.getDX()),
                (int) (getLocation().getY() + motion.getDY())));
    }

    @Override
    public void moveTo(Point location) {
        setLocation(location);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            move();
            applyEffect();
            try {
                Thread.sleep(dt);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public long getDT() {
        return dt;
    }

    @Override
    public void setDT(long dt) {
        this.dt = dt;
    }

    @Override
    public void start() {
        thread.start();
    }

    @Override
    public void stop() {
        thread.interrupt();
    }

    public void addEffect(Supplier<Motion> effects) {
        this.effects = effects;
    }

    public void applyEffect() {
        getMotion().add(effects.get());
    }
}
