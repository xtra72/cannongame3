package com.nhnacademy;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import org.junit.jupiter.api.RepeatedTest;

public class BallTest {
    final Random random = new Random();

    @RepeatedTest(10)
    public void defaultConstructor() {
        final int MIN_X = 0;
        final int MAX_X = 500;
        final int MIN_Y = 0;
        final int MAX_Y = 300;
        final int MIN_RADIUS = 10;
        final int MAX_RADIUS = 50;

        int radius = MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS);
        Point location = new Point(MIN_X + random.nextInt(MAX_X - MIN_X),
                MIN_Y + random.nextInt(MAX_Y - MIN_Y));

        Ball ball = new Ball(location, radius);

        assertEquals(location, ball.getLocation());
        assertEquals(radius, ball.getRadius());
        assertEquals(Ball.DEFAULT_COLOR, ball.getColor());
    }

    @RepeatedTest(10)
    public void optionalConstructor() {
        final int MIN_X = 0;
        final int MAX_X = 500;
        final int MIN_Y = 0;
        final int MAX_Y = 300;
        final int MIN_RADIUS = 10;
        final int MAX_RADIUS = 50;
        final Color[] colors = { Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW, Color.GRAY };

        int radius = MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS);
        Point location = new Point(MIN_X + random.nextInt(MAX_X - MIN_X),
                MIN_Y + random.nextInt(MAX_Y - MIN_Y));
        Color color = colors[random.nextInt(colors.length)];

        Ball ball = new Ball(location, radius, color);

        assertEquals(location, ball.getLocation());
        assertEquals(radius, ball.getRadius());
        assertEquals(color, ball.getColor());
    }
}
