package com.nhnacademy;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.JLabel;

public class WindChangeListener implements ChangeListener {
    Motion motion;
    JLabel label;

    public WindChangeListener(Motion motion, JLabel label) {
        this.motion = motion;
        this.label = label;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        motion.set(Motion.createPosition(((JSlider) e.getSource()).getValue(), 0));
        label.setText(((JSlider) e.getSource()).getValue() + "");
    }
}
