package com.nhnacademy;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class MotionTest {
    Random random = new Random();

    @RepeatedTest(100)
    public void repeatedTestCreatePosition() {
        final int dx = random.nextInt(100);
        final int dy = random.nextInt(100);

        Motion motion = Motion.createPosition(dx, dy);

        assertEquals(dx, motion.getDX());
        assertEquals(dy, motion.getDY());
    }

    @RepeatedTest(100)
    public void repeatedTestAddMotion() {
        int dx1 = random.nextInt(100);
        int dy1 = random.nextInt(100);
        int dx2 = random.nextInt(100);
        int dy2 = random.nextInt(100);

        Motion motion1 = Motion.createPosition(dx1, dy1);
        Motion motion2 = Motion.createPosition(dx2, dy2);

        motion1.add(motion2);

        assertEquals(dx1 + (long) dx2, motion1.getDX());
        assertEquals(dy1 + (long) dy2, motion1.getDY());
    }

    @Test
    public void comparePositionVsDisplacement() {
        Motion motion = Motion.createPosition(30, 40);

        assertEquals(50, motion.getMagnitude());
    }
}
