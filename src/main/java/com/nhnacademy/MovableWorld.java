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
    public Regionable add(Regionable object) {
        for (int i = 0; i < getCount(); i++) {
            Regionable otherObject = get(i);
            if (object != otherObject) {
                if (object instanceof Bounded) {
                    ((Bounded) object).addRegion(otherObject);
                }
                if (otherObject instanceof Bounded) {
                    ((Bounded) otherObject).addRegion(object);
                }
            }
        }

        if (object instanceof Movable) {
            ((Movable) object).setDT(dt);
            ((Movable) object).addEffect(() -> effectList.stream().reduce(new Motion(), Motion::add));
            // ((Movable) object).addEffect(() -> {
            // Motion sumOfEffect = new Motion();

            // for (Motion effect : effectList) {
            // sumOfEffect.add(effect);
            // }

            // return sumOfEffect;
            // });

            if (thread.isAlive()) {
                ((Movable) object).start();
            }
        }

        return super.add(object);
    }

    @Override
    public void remove(Regionable object) {
        if (object instanceof Movable) {
            ((Movable) object).stop();
        }
        super.remove(object);

        for (int i = 0; i < getCount(); i++) {
            Regionable otherObject = get(i);
            if (otherObject instanceof Bounded) {
                ((Bounded) otherObject).removeRegion(object);
            }
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

        for (Regionable object : objectList) {
            if (object instanceof Movable) {
                ((Movable) object).stop();
            }
        }

        objectList.clear();
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
