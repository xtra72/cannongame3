package com.nhnacademy;

import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class WorldTest {
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

        World world = new World();
        frame.add(world);

        Ball ball = new Ball(new Point(WorldTest.FRAME_WIDTH / 2, WorldTest.FRAME_HEIGHT / 2), 50);
        world.add(ball);

        frame.setVisible(true);

    }
}
