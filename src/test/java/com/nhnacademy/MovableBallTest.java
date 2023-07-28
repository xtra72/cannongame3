package com.nhnacademy;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Random;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class MovableBallTest {
    Random random = new Random();

    @Test
    public void create() {
        Point point = new Point(100, 100);
        Motion motion = Motion.createPosition(10, 5);

        MovableBall ball = new MovableBall(point, 50);

        ball.setMotion(motion);

        assertEquals(point, ball.getLocation());
        assertEquals(motion, ball.getMotion());

    }

    @Test
    public void move() {
        final int MOVE_COUNT = 10;
        Point point = new Point(100, 100);
        Motion motion = Motion.createPosition(10, 5);

        MovableBall ball = new MovableBall(point, 50);

        ball.setMotion(motion);

        for (int i = 0; i < MOVE_COUNT; i++) {
            ball.move();
            point.translate(motion.getDX(), motion.getDY());
            assertEquals(point, ball.getLocation());
        }

    }

    @RepeatedTest(10)
    public void moveTo() {
        int x1 = random.nextInt(100);
        int y1 = random.nextInt(100);
        int x2 = random.nextInt(100);
        int y2 = random.nextInt(100);
        Point initialPoint = new Point(x1, y1);
        Point targetPoint = new Point(x2, y2);
        Motion motion = Motion.createPosition(10, 5);

        MovableBall ball = new MovableBall(initialPoint, 50);

        ball.setMotion(motion);

        ball.moveTo(targetPoint);
        assertEquals(targetPoint, ball.getLocation());
        assertEquals(50, ball.getRadius());

    }
}
