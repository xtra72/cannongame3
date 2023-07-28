package com.nhnacademy;

public class MovableWorld extends World {
    long dt;
    int moveCount;
    int maxMoveCount;

    public MovableWorld() {
        super();
        dt = 1000;
        moveCount = 0;
        maxMoveCount = 0;
    }

    void move() {
        for (int i = 0; i < getCount(); i++) {
            Regionable object = get(i);
            if (object instanceof Movable) {
                ((Movable) object).move();
            }
        }
        repaint();
    }

    public void run() {
        for (int i = 0; (maxMoveCount == 0) || (i < getMaxMoveCount()); i++) {
            move();
            try {
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

    public int getMaxMoveCount() {
        return maxMoveCount;
    }

    public void setMaxMoveCount(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }
}
