package com.nhnacademy;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class BoundedWorldTest2 {
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

        BoundedWorld world = new BoundedWorld();
        world.setDT(100);
        world.addEffect(Motion.createPosition(0, -2));
        world.addEffect(Motion.createPosition(-2, 0));
        frame.add(world);

        MovableBall ball = new MovableBall(new Point(WorldTest.FRAME_WIDTH / 2, WorldTest.FRAME_HEIGHT / 2), 50);
        ball.setMotion(new Motion(10, 5));
        world.add(ball);

        frame.setVisible(true);

        world.run();

    }
}
