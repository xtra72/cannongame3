package com.nhnacademy;

import java.awt.Insets;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class CannonGame extends JFrame implements Runnable {

    static final int DEFAULT_WIDTH = 1500;
    static final int DEFAULT_HEIGHT = 800;
    static final int BUTTON_WIDTH = 100;
    static final int BUTTON_HEIGHT = 50;
    static final int SLIDER_WIDTH = 300;
    static final int SLIDER_HEIGHT = 24;
    static final int LEFT_MARGIN = 10;
    static final int RIGHT_MARGIN = 10;
    static final int TOP_MARGIN = 10;
    static final int BOTTOM_MARGIN = 10;
    BoundedWorld world;
    Thread thread;
    Insets insets;
    Motion wind;
    Motion gravity;
    int power = 10;
    int angle = -45;

    public CannonGame() {
        super();

        thread = new Thread(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);

        setVisible(true);
        setVisible(false);
        insets = getInsets();

        wind = Motion.createDisplacement(0, 0);
        gravity = Motion.createDisplacement(1, 90);

        world = new BoundedWorld();
        world.setSize(getValidWidth(), getValidHeight() - BUTTON_HEIGHT);
        world.setDT(10);
        world.addEffect(wind);
        world.addEffect(gravity);
        add(world);

        JButton fireButton = new JButton("Fire!");
        fireButton.setBounds(getValidMinX(), getValidHeight() - BUTTON_HEIGHT,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        fireButton.addActionListener(e -> {
            BoundedBall ball = new BoundedBall(new Point(100, (int) world.getBounds().getMaxY() - 20), 20);
            ball.setMotion(Motion.createDisplacement(power, angle));
            ball.addCollisionEventListener(e1 -> {
                Regionable region = e1.getDestination();
                if (region.getType() == Regionable.Type.WETLAND) {
                    // ball.getMotion().add(Motion.createDisplacement(ball.getMotion().getMagnitude()
                    // * -1,
                    // ball.getMotion().getTheta()));
                    ball.stop();
                }
            });
            world.add(ball);
        });
        add(fireButton);

        JLabel windDisplay = new JLabel(wind.getMagnitude() + "");
        windDisplay.setBounds((int) fireButton.getBounds().getMaxX() + SLIDER_WIDTH / 2 - 50,
                getValidHeight() - BUTTON_HEIGHT + SLIDER_HEIGHT,
                100, SLIDER_HEIGHT);
        add(windDisplay);

        JSlider windSlider = new JSlider(-10, 10, 0);
        windSlider.setOrientation(SwingConstants.HORIZONTAL);
        windSlider.setBounds((int) fireButton.getBounds().getMaxX(), getValidHeight() - BUTTON_HEIGHT,
                SLIDER_WIDTH, SLIDER_HEIGHT);
        // windSlider.addChangeListener(e -> {
        // wind.set(Motion.createPosition(((JSlider) e.getSource()).getValue(), 0));
        // windDisplay.setText(((JSlider) e.getSource()).getValue() + "");
        // });
        windSlider.addChangeListener(new WindChangeListener(wind, windDisplay));
        windSlider.setValue(wind.getDX());
        add(windSlider);

        JSlider powerSlider = new JSlider(0, 50, 0);
        powerSlider.setOrientation(SwingConstants.HORIZONTAL);
        powerSlider.setBounds((int) fireButton.getBounds().getMaxX() + SLIDER_WIDTH, getValidHeight() - BUTTON_HEIGHT,
                SLIDER_WIDTH, SLIDER_HEIGHT);
        powerSlider.setValue(power);
        powerSlider.addChangeListener(e -> {
            power = ((JSlider) e.getSource()).getValue();
        });
        add(powerSlider);

        setLayout(null);
    }

    public int getValidMinY() {
        return (getInsets().top) + TOP_MARGIN;
    }

    public int getValidMaxY() {
        return getHeight() - insets.bottom - BOTTOM_MARGIN;
    }

    public int getValidMinX() {
        return (insets.left) + LEFT_MARGIN;
    }

    public int getValidMaxX() {
        return getWidth() - getInsets().right - RIGHT_MARGIN;
    }

    public int getValidHeight() {
        return getHeight() - (getInsets().bottom + getInsets().top) - TOP_MARGIN - BOTTOM_MARGIN;
    }

    public int getValidWidth() {
        return getWidth() - (getInsets().left + getInsets().right) - LEFT_MARGIN - RIGHT_MARGIN;
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    @Override
    public void run() {
        world.start();
        setVisible(true);
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        CannonGame game = new CannonGame();

        game.start();
    }
}
