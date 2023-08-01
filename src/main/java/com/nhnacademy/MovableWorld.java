package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;

public class MovableWorld extends World implements Runnable {
    Thread thread;
    long dt;
    int moveCount;
    List<Motion> effectList;

    public MovableWorld() {
        super();
        thread = new Thread(this, this.getClass().getSimpleName());
        dt = 1000;
        moveCount = 0;
        effectList = new ArrayList<>();
    }

    void move() {
        for (int i = 0; i < getCount(); i++) {
            Regionable object = get(i);
            if (object instanceof Movable) {
                ((Movable) object).move();
                for (Motion effect : effectList) {
                    ((Movable) object).getMotion().add(effect);
                }
            }
        }
        repaint();
    }

    @Override
    public void add(Regionable object) {
        super.add(object);

        if (object instanceof Movable) {
            ((Movable) object).addEffect(() -> {
                Motion effect = Motion.createPosition(0, 0);

                for (Motion e : effectList) {
                    effect.add(e);
                }

                return effect;
            });
        }
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    @Override
    public void run() {
        for (int i = 0; i < getCount(); i++) {
            Regionable object = get(i);
            if (object instanceof Movable) {
                ((Movable) object).setDT(getDT());
                ((Movable) object).start();
            }
        }

        while (!Thread.interrupted()) {
            try {
                repaint();
                Thread.sleep(getDT());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setDT(long dt) {
        this.dt = dt;
    }

    public long getDT() {
        return dt;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void addEffect(Motion effect) {
        if (!effectList.contains(effect)) {
            effectList.add(effect);
        }
    }

    public int getEffectCount() {
        return effectList.size();
    }

    public Motion getEffect(int index) {
        return effectList.get(index);
    }

    public void removeEffect(int index) {
        effectList.remove(index);
    }

    public void removeEffect(Motion effect) {
        effectList.remove(effect);
    }
}
