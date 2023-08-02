package com.nhnacademy;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class World extends JPanel {
    List<Regionable> objectList;

    public World() {
        objectList = new ArrayList<>();
    }

    public void add(Regionable object) {
        objectList.add(object);
    }

    public int getCount() {
        return objectList.size();
    }

    public Regionable get(int index) {
        return objectList.get(index);
    }

    public void remove(Regionable object) {
        objectList.remove(object);
    }

    @Override
    public void remove(int index) {
        objectList.remove(index);
    }

    public void clear() {
        List<Regionable> removeObjectList = new ArrayList<>();

        for (Regionable object : objectList) {
            if (object instanceof Movable) {
                removeObjectList.add(object);
            }
        }
        for (Regionable object : removeObjectList) {
            ((Movable) object).stop();
            objectList.remove(object);
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < getCount(); i++) {
            Regionable object = get(i);

            if (object instanceof Paintable) {
                ((Paintable) object).paint(g);
            }
        }
    }
}
