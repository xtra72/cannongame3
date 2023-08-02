package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CannonGame extends JFrame implements Runnable {
    static final int DEFAULT_WIDTH = 1000;
    static final int DEFAULT_HEIGHT = 800;
    static final long DEFAULT_DELTA_TIME = 100;
    static final int ANGLE_MAX = 90;
    static final int ANGLE_MIN = 0;
    static final int DEFAULT_ANGLE = 45;
    static final int POWER_MAX = 100;
    static final int POWER_MIN = 0;
    static final int DEFAULT_POWER = 10;
    static final int WIND_MAX = 20;
    static final int WIND_MIN = -20;
    static final int DEFAULT_WIND = 0;

    Thread thread;
    Logger logger;
    MovableWorld world;
    long dt = DEFAULT_DELTA_TIME;
    int angle = DEFAULT_ANGLE;
    int power = DEFAULT_POWER;
    Point initialLocation;
    JSlider windSlider;
    JSlider angleSlider;
    JSlider powerSlider;
    Motion wind;
    Motion gravity;
    Random random;
    Regionable target;
    Color[] colors = { Color.BLUE, Color.RED, Color.YELLOW, Color.GRAY,
            Color.GREEN, Color.PINK, Color.MAGENTA };

    public CannonGame() {
        super();
        thread = new Thread(this);
        logger = LogManager.getLogger(this.getClass().getSimpleName());
        initialLocation = new Point(50, 550);
        wind = Motion.createDisplacement(DEFAULT_WIND, 0);
        gravity = Motion.createDisplacement(1, 90);
        random = new Random();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });
    }

    public void prepare() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);
        setVisible(false);

        world = new BoundedWorld();
        world.setBackground(Color.GRAY);
        world.setBounds(0, 0, 900, 600);
        world.setDT(dt);
        world.addEffect(wind);
        world.addEffect(gravity);
        add(world);

        target = new Ball(new Point(850, 550), 30, Color.RED);
        target.setType(Regionable.Type.TARGET);
        world.add(target);

        world.add(new Ball(new Point(450, 250), 40, Color.GREEN));

        JButton fireButton = new JButton("Fire!!!");
        fireButton.setBounds(0, 600, 100, 100);
        fireButton.addActionListener(e -> {
            int colorIndex = random.nextInt(colors.length);
            BoundedBall ball = new BoundedBall(initialLocation, 20, colors[colorIndex]);
            ball.setMotion(Motion.createDisplacement(power, -angle));
            ball.addCollisionEventListener(e1 -> {
                Regionable destination = (Regionable) e1.getDestination();
                if (destination == target) {
                    world.remove(target);
                }
            });

            world.add(ball);
        });
        add(fireButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(100, 600, 100, 100);
        clearButton.addActionListener(e -> world.clear());
        add(clearButton);

        JTextField angleValue = new JTextField(angle + "");
        angleValue.setBounds(900, 600, 100, 50);
        angleValue.addActionListener(e -> {
            try {
                int value = Integer.parseInt(((JTextField) e.getSource()).getText());
                if (value < ANGLE_MIN || ANGLE_MAX < value) {
                    logger.warn("Angle out of range : {} ", value);
                    throw new NumberFormatException();
                }

                angle = Integer.parseInt(((JTextField) e.getSource()).getText());
                angleSlider.setValue(angle);
            } catch (NumberFormatException ignore) {
                ((JTextField) e.getSource()).setText(angle + "");
            }
        });

        add(angleValue);

        angleSlider = new JSlider(SwingConstants.VERTICAL, ANGLE_MIN, ANGLE_MAX, angle);
        angleSlider.setBounds(900, 0, 100, 600);
        angleSlider.setMajorTickSpacing(10);
        angleSlider.setMinorTickSpacing(2);
        angleSlider.setPaintLabels(true);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintTrack(true);
        angleSlider.addChangeListener(e -> {
            angle = ((JSlider) e.getSource()).getValue();
            angleValue.setText(String.valueOf(angle));
        });
        add(angleSlider);

        JLabel powerLabel = new JLabel("Power");
        powerLabel.setBounds(300, 600, 100, 50);
        add(powerLabel);

        JTextField powerValue = new JTextField(power + "");
        powerValue.setBounds(350, 600, 100, 50);
        powerValue.addActionListener(e -> {
            try {
                int value = Integer.parseInt(((JTextField) e.getSource()).getText());
                if (value < POWER_MIN || POWER_MAX < value) {
                    logger.warn("power out of range : {} ", value);
                    throw new NumberFormatException();
                }

                power = Integer.parseInt(((JTextField) e.getSource()).getText());
                powerSlider.setValue(power);
            } catch (NumberFormatException ignore) {
                ((JTextField) e.getSource()).setText(power + "");
            }
        });
        add(powerValue);

        powerSlider = new JSlider(SwingConstants.HORIZONTAL, POWER_MIN, POWER_MAX, power);
        powerSlider.setBounds(200, 650, 300, 50);
        powerSlider.setMajorTickSpacing(10);
        powerSlider.setMinorTickSpacing(2);
        powerSlider.setPaintLabels(true);
        powerSlider.setPaintTicks(true);
        powerSlider.setPaintTrack(true);
        powerSlider.addChangeListener(e -> {
            power = ((JSlider) e.getSource()).getValue();
            powerValue.setText(String.valueOf(power));
        });
        add(powerSlider);

        JLabel windLabel = new JLabel("Wind");
        windLabel.setBounds(600, 600, 100, 50);
        add(windLabel);

        JTextField windValue = new JTextField(wind + "");
        windValue.setBounds(650, 600, 100, 50);
        windValue.addActionListener(e -> {
            try {
                int value = Integer.parseInt(((JTextField) e.getSource()).getText());
                wind.set(Motion.createDisplacement(value, 0));
                windSlider.setValue(wind.getMagnitude());
            } catch (NumberFormatException ignore) {
                logger.warn("invalid input : " + ((JTextField) e.getSource()).getText());
            }
        });

        add(windValue);

        windSlider = new JSlider(SwingConstants.HORIZONTAL, WIND_MIN, WIND_MAX, wind.getMagnitude());
        windSlider.setBounds(500, 650, 300, 50);
        windSlider.setMajorTickSpacing(10);
        windSlider.setMinorTickSpacing(2);
        windSlider.setPaintLabels(true);
        windSlider.setPaintTicks(true);
        windSlider.setPaintTrack(true);
        windSlider.addChangeListener(e -> {
            wind.set(Motion.createDisplacement(((JSlider) e.getSource()).getValue(), 0));
            windValue.setText(String.valueOf(wind.getMagnitude()));
        });
        add(windSlider);

        setLayout(null);
    }

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    public void join() {
        try {
            thread.join();
        } catch (InterruptedException igore) {
            Thread.currentThread().interrupt();
        }
    }

    public void preprocess() {
        prepare();

        setVisible(true);
        world.start();
        logger.trace("started!");
    }

    public void process() {
        //
    }

    public void postprocess() {
        logger.trace("exit!");
    }

    @Override
    public void run() {
        preprocess();

        while (!Thread.interrupted()) {
            try {
                process();
                Thread.sleep(dt);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        postprocess();
    }

    public static void main(String[] args) {
        CannonGame game = new CannonGame();

        game.start();

        game.join();
    }
}
