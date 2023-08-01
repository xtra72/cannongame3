package com.nhnacademy;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class BorderLayoutTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(300, 300);
        JButton button2 = new JButton("Button 2");

        frame.add(new JButton("Button 1"), BorderLayout.EAST);
        frame.add(button2, BorderLayout.WEST);

        frame.setVisible(true);
    }
}
