package com.nhnacademy;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class BoundedBallTest {
    static final int FRAME_WIDTH = 500;
    static final int FRAME_HEIGHT = 300;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(WorldTest.FRAME_WIDTH, WorldTest.FRAME_HEIGHT);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        MovableWorld world = new MovableWorld();
        world.setDT(100);
        frame.add(world);
        frame.setVisible(true);

        BoundedBall ball = new BoundedBall(new Point(WorldTest.FRAME_WIDTH / 2, WorldTest.FRAME_HEIGHT / 2), 20);
        ball.setMotion(new Motion(20, 15));
        ball.setBounds(world.getBounds());
        world.add(ball);

        world.add(new Ball(new Point(100, 100), 20));

        frame.setVisible(true);

        world.run();

    }
}
