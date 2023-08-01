package com.nhnacademy;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Point;
import java.awt.Rectangle;

public class BoundedWorld extends MovableWorld {
    Regionable leftWall;
    Regionable rightWall;
    Regionable topWall;
    Regionable bottomWall;

    public BoundedWorld() {
        super();
        leftWall = new Box(new Point(-50, 0), 100, 200);
        rightWall = new Box(new Point(50, 0), 100, 200);
        topWall = new Box(new Point(0, -50), 200, 100);
        bottomWall = new Box(new Point(0, 50), 200, 100);
        bottomWall.setType(Regionable.Type.WETLAND);

        add(leftWall);
        add(rightWall);
        add(topWall);
        add(bottomWall);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                remove(leftWall);
                remove(rightWall);
                remove(topWall);
                remove(bottomWall);

                leftWall = new Box(new Point(-50, (int) getBounds().getHeight() / 2), 100,
                        (int) getBounds().getHeight() + 200);
                rightWall = new Box(new Point((int) getBounds().getWidth() + 50, (int) getBounds().getHeight() / 2),
                        100,
                        (int) getBounds().getHeight() + 200);
                topWall = new Box(new Point((int) getBounds().getWidth() / 2, -50), (int) getBounds().getWidth() + 200,
                        100);
                bottomWall = new Box(new Point((int) getBounds().getWidth() / 2, (int) getBounds().getHeight() + 50),
                        (int) getBounds().getWidth() + 200,
                        100);
                bottomWall.setType(Regionable.Type.WETLAND);
                add(leftWall);
                add(rightWall);
                add(topWall);
                add(bottomWall);
                System.out.println("Resized to " + e.getComponent().getSize());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });
    }

    // @Override
    // public void add(Regionable object) {
    // super.add(object);
    // if (object instanceof Bounded) {
    // ((Bounded) object).setBounds(getBounds());
    // }
    // }
}
