package com.nhnacademy;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FireButtonActionListener implements ActionListener {
    World world;
    Point initialLocation;
    int radius;

    public FireButtonActionListener(World world, Point initialLocation, int radius) {
        this.world = world;
        this.initialLocation = initialLocation;
        this.radius = radius;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        world.add(new BoundedBall(initialLocation, radius));
    }

}
