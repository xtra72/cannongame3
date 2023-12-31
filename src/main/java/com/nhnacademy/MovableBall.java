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
        this.motion = new Motion(motion);
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
        long startTime = System.nanoTime();
        long nextTime = startTime + dt * 1000000;

        while (!Thread.interrupted()) {
            move();
            applyEffect();
            try {
                long currentTime = System.nanoTime();
                if (nextTime < currentTime) {
                    nextTime += (currentTime - nextTime + dt * 1000000 - 1) / (dt * 1000000) * (dt * 1000000);
                }

                long remainTime = nextTime - currentTime;
                Thread.sleep(remainTime / 1000000, (int) (remainTime % 1000000));
                nextTime += dt * 1000000;

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
