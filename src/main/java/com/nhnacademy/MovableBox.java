package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.util.function.Supplier;

public class MovableBox extends Box implements Movable {
    Motion motion = new Motion(0, 0);
    Thread thread;
    long dt = 0;
    MovableWorld world;
    Supplier<Motion> effects;
    boolean enableEffect = true;

    public MovableBox(Point location, int width, int height, Type type) {
        super(location, width, height, type);
        thread = new Thread(this, this.getClass().getSimpleName() + "-" + getId());
    }

    public MovableBox(Point location, int width, int height) {
        super(location, width, height);
        thread = new Thread(this, this.getClass().getSimpleName() + "-" + getId());
    }

    public MovableBox(Point location, int width, int height, Color color, Type type) {
        super(location, width, height, color, type);
        thread = new Thread(this, this.getClass().getSimpleName() + "-" + getId());
    }

    public MovableBox(Point location, int width, int height, Color color) {
        super(location, width, height, color);
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
        logger.trace("Box({}, {})", getRegion().getX(), getRegion().getY());
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
            if (enableEffect) {
                applyEffect();
            }
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

    public void enableEffect(boolean enable) {
        this.enableEffect = enable;
    }

    public void addEffect(Supplier<Motion> effects) {
        this.effects = effects;
    }

    public void applyEffect() {
        getMotion().add(effects.get());
    }
}
