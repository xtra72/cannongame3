package com.nhnacademy;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    static final long DEFAULT_DELTA_TIME = 10;
    static final int ANGLE_MAX = 90;
    static final int ANGLE_MIN = 0;
    static final int DEFAULT_ANGLE = 45;
    static final int POWER_MAX = 100;
    static final int POWER_MIN = 0;
    static final int DEFAULT_POWER = 30;
    static final int WIND_MAX = 20;
    static final int WIND_MIN = -20;
    static final int DEFAULT_WIND = 0;
    static final int PADDING = 10;
    static final int REFLECTIVITY_MAX = 100;
    static final int REFLECTIVITY_MIN = 0;

    Thread thread;
    Logger logger;
    MovableWorld world;
    long dt = DEFAULT_DELTA_TIME;
    Point initialLocation;
    JSlider windSlider;
    JTextField angleValue;
    JSlider angleSlider;
    JTextField powerValue;
    JSlider powerSlider;
    JSlider reflectivitySlider;
    JTextField scoreValue;
    Motion wind;
    Motion gravity;
    int power = DEFAULT_POWER;
    int theta = DEFAULT_ANGLE;
    int reflectivity = 50;
    int totalScore = 0;
    Random random;
    Regionable target;
    Cannon cannon;
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
        world.setBounds(ratedX(0), ratedY(10), ratedWidth(90), ratedHeight(70));
        world.setDT(dt);
        world.addEffect(wind);
        world.addEffect(gravity);
        add(world);

        world.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                logger.error(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == 38) && (ANGLE_MIN < power && power < ANGLE_MAX)) {
                    theta++;
                    angleValue.setText(theta + "");
                    angleSlider.setValue(theta);
                } else if ((e.getKeyCode() == 40) && (ANGLE_MIN < power && power < ANGLE_MAX)) {
                    theta--;
                    angleValue.setText(theta + "");
                    angleSlider.setValue(theta);
                } else if ((e.getKeyCode() == 39) && (POWER_MIN < power && power < POWER_MAX)) {
                    power++;
                    powerValue.setText(power + "");
                    powerSlider.setValue(power);
                } else if ((e.getKeyCode() == 37) && (POWER_MIN < power && power < POWER_MAX)) {
                    power--;
                    powerValue.setText(power + "");
                    powerSlider.setValue(power);
                } else if (e.getKeyCode() == 10) {
                    fire();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                logger.error(e);
            }

        });

        initialLocation = new Point(ratedX(7), ratedY(65));
        cannon = (Cannon) world.add(new Cannon(new Point(ratedX(2), ratedY(65)), ratedWidth(5)));
        target = world
                .add(new BoundedBox(new Point(ratedX(70), ratedY(65)), ratedWidth(5), ratedHeight(5), Color.GREEN,
                        Regionable.Type.TARGET));
        ((BoundedBox) target).setMotion(Motion.createPosition(0, 10));
        ((BoundedBox) target).enableRegion(false);
        ((BoundedBox) target).enableEffect(false);

        world.add(new Box(new Point(world.getWidth() / 2, world.getHeight() + 50),
                world.getWidth() + 200, 120, Color.GRAY, Regionable.Type.WETLAND));
        world.add(new Box(new Point(world.getWidth() / 2, world.getHeight() - 50), 300, 100, Color.BLACK));
        world.add(new Box(new Point(world.getWidth() / 2 + 100, world.getHeight() / 2), 100, 300, Color.BLACK));

        JButton fireButton = new JButton("Fire!!!");
        fireButton.setBounds(ratedX(1), ratedY(82), ratedWidth(8), ratedHeight(5));
        fireButton.setFocusable(false);
        fireButton.addActionListener(e -> fire());

        add(fireButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFocusable(false);
        clearButton.setBounds(ratedX(11), ratedY(82), ratedWidth(8), ratedHeight(5));
        clearButton.addActionListener(e -> world.clear());
        add(clearButton);

        JLabel scoreLabel = new JLabel("점수", SwingConstants.RIGHT);
        scoreLabel.setBounds(ratedX(44), ratedY(5), ratedWidth(6), ratedHeight(4));
        add(scoreLabel);

        scoreValue = new JTextField(totalScore + "");
        scoreValue.setFocusable(false);
        scoreValue.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreValue.setEditable(false);
        scoreValue.setBounds(ratedX(50), ratedY(5), ratedWidth(6), ratedHeight(4));
        add(scoreValue);

        JLabel angleLabel = new JLabel("각도", SwingConstants.RIGHT);
        angleLabel.setBounds(ratedX(84), ratedY(81), ratedWidth(6), ratedHeight(4));
        add(angleLabel);

        angleValue = new JTextField(theta + "");
        angleValue.setFocusable(false);
        angleValue.setHorizontalAlignment(SwingConstants.RIGHT);
        angleValue.setBounds(ratedX(90), ratedY(81), ratedWidth(6), ratedHeight(4));
        angleValue.addActionListener(event -> {
            try {
                int value = Integer.parseInt(((JTextField) event.getSource()).getText());
                if (value < ANGLE_MIN || ANGLE_MAX < value) {
                    logger.warn("Angle out of range : {} ", value);
                    throw new NumberFormatException();
                }

                theta = value;
                cannon.setAngle(theta);
                angleSlider.setValue(value);
            } catch (NumberFormatException exception) {
                logger.warn(exception.getMessage());
            }
        });

        add(angleValue);

        angleSlider = new JSlider(SwingConstants.VERTICAL, ANGLE_MIN, ANGLE_MAX, theta);
        angleSlider.setBounds(ratedX(90), ratedY(0), ratedWidth(10), ratedHeight(80));
        angleSlider.setMajorTickSpacing(10);
        angleSlider.setMinorTickSpacing(2);
        angleSlider.setPaintLabels(true);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintTrack(true);
        angleSlider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            System.out.print("( " + power + ", " + theta + " )");
            theta = value;
            cannon.setAngle(theta);

            System.out.println("-> ( " + power + ", " + theta + " )");
            angleValue.setText(String.valueOf(value));
        });
        add(angleSlider);

        JLabel powerLabel = new JLabel("세기", SwingConstants.RIGHT);
        powerLabel.setBounds(ratedX(24), ratedY(81), ratedWidth(6), ratedHeight(4));
        add(powerLabel);

        powerValue = new JTextField(power + "");
        powerValue.setFocusable(false);
        powerValue.setHorizontalAlignment(SwingConstants.RIGHT);
        powerValue.setBounds(ratedX(30), ratedY(81), ratedWidth(6), ratedHeight(4));
        powerValue.addActionListener(e -> {
            try {
                int value = Integer.parseInt(((JTextField) e.getSource()).getText());
                if (value < POWER_MIN || POWER_MAX < value) {
                    logger.warn("power out of range : {} ", value);
                    throw new NumberFormatException();
                }

                power = value;
                powerSlider.setValue(value);
            } catch (NumberFormatException exception) {
                logger.warn(exception.getMessage());

            }
        });
        add(powerValue);

        powerSlider = new JSlider(SwingConstants.HORIZONTAL, POWER_MIN, POWER_MAX, power);
        powerSlider.setBounds(ratedX(21), ratedY(85), ratedWidth(18), ratedHeight(6));
        powerSlider.setMajorTickSpacing(20);
        powerSlider.setMinorTickSpacing(5);
        powerSlider.setPaintLabels(true);
        powerSlider.setPaintTicks(true);
        powerSlider.setPaintTrack(true);
        powerSlider.setFocusable(false);
        powerSlider.addChangeListener(e -> {
            power = ((JSlider) e.getSource()).getValue();
            powerValue.setText(String.valueOf(power));
        });
        add(powerSlider);

        JLabel windLabel = new JLabel("바람", SwingConstants.RIGHT);
        windLabel.setBounds(ratedX(44), ratedY(81), ratedWidth(6), ratedHeight(4));
        add(windLabel);

        JTextField windValue = new JTextField(wind.getDX() + "");
        windValue.setFocusable(false);
        windValue.setHorizontalAlignment(SwingConstants.RIGHT);
        windValue.setBounds(ratedX(50), ratedY(81), ratedWidth(6), ratedHeight(4));
        windValue.addActionListener(e -> {
            try {
                int value = Integer.parseInt(((JTextField) e.getSource()).getText());
                wind.set(Motion.createPosition(value, 0));
                windSlider.setValue(wind.getMagnitude());
            } catch (NumberFormatException ignore) {
                logger.warn("invalid input : " + ((JTextField) e.getSource()).getText());
            }
        });

        add(windValue);

        windSlider = new JSlider(SwingConstants.HORIZONTAL, WIND_MIN, WIND_MAX, wind.getDX());
        windSlider.setBounds(ratedX(41), ratedY(85), ratedWidth(18), ratedHeight(6));
        windSlider.setMajorTickSpacing(10);
        windSlider.setMinorTickSpacing(2);
        windSlider.setPaintLabels(true);
        windSlider.setPaintTicks(true);
        windSlider.setPaintTrack(true);
        windSlider.setFocusable(false);
        windSlider.addChangeListener(e -> {
            wind.set(Motion.createPosition(((JSlider) e.getSource()).getValue(), 0));
            windValue.setText(String.valueOf(wind.getDX()));
        });
        add(windSlider);

        JLabel reflectivityLabel = new JLabel("반사율", SwingConstants.RIGHT);
        reflectivityLabel.setBounds(ratedX(64), ratedY(81), ratedWidth(6), ratedHeight(4));
        add(reflectivityLabel);

        JTextField reflectivityValue = new JTextField(reflectivity + "");
        reflectivityValue.setFocusable(false);
        reflectivityValue.setHorizontalAlignment(SwingConstants.RIGHT);
        reflectivityValue.setBounds(ratedX(70), ratedY(81), ratedWidth(6), ratedHeight(4));
        reflectivityValue.addActionListener(e -> {
            try {
                int value = Integer.parseInt(((JTextField) e.getSource()).getText());
                reflectivity = value;
                reflectivitySlider.setValue(reflectivity);
            } catch (NumberFormatException ignore) {
                logger.warn("invalid input : " + ((JTextField) e.getSource()).getText());
            }
        });

        add(reflectivityValue);

        reflectivitySlider = new JSlider(SwingConstants.HORIZONTAL, REFLECTIVITY_MIN, REFLECTIVITY_MAX,
                REFLECTIVITY_MAX);
        reflectivitySlider.setBounds(ratedX(61), ratedY(85), ratedWidth(18), ratedHeight(6));
        reflectivitySlider.setMajorTickSpacing(20);
        reflectivitySlider.setMinorTickSpacing(5);
        reflectivitySlider.setPaintLabels(true);
        reflectivitySlider.setPaintTicks(true);
        reflectivitySlider.setPaintTrack(true);
        reflectivitySlider.setFocusable(false);
        reflectivitySlider.addChangeListener(e -> {
            reflectivity = ((JSlider) e.getSource()).getValue();
            reflectivityValue.setText(String.valueOf(reflectivity));
        });
        add(reflectivitySlider);

        world.requestFocus();
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
        world.stop();
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

    public int ratedX(float rate) {
        return (int) (getWidth() * rate / 100);
    }

    public int ratedY(float rate) {
        return (int) (getHeight() * rate / 100);
    }

    public int ratedWidth(float rate) {
        return (int) (getWidth() * rate / 100);
    }

    public int ratedHeight(float rate) {
        return (int) (getHeight() * rate / 100);
    }

    public void addScore(int score) {
        totalScore += score;
        scoreValue.setText(totalScore + "");
    }

    public void fire() {
        int colorIndex = random.nextInt(colors.length);
        BoundedBall ball = new BoundedBall(initialLocation, 10, colors[colorIndex]);
        ball.setMotion(Motion.createDisplacement(power, -theta));
        System.out.println("( " + ball.getMotion().getDX() + ", " + ball.getMotion().getDY() + " )");
        ball.addCollisionEventListener(e1 -> {
            Regionable destination = (Regionable) e1.getDestination();
            if (destination == target) {
                addScore(1);
            } else if ((e1.getSource() instanceof Bounded)
                    && (e1.getSource() instanceof Movable)
                    && (destination.getType() == Regionable.Type.WETLAND)) {
                ((Movable) e1.getSource()).getMotion().multiply(reflectivity / 100d);
                if (((Movable) e1.getSource()).getMotion().getMagnitude() < 1) {
                    world.remove((Regionable) e1.getSource());
                }
            }
        });

        world.add(ball);
    }

    public static void main(String[] args) {
        CannonGame game = new CannonGame();

        game.start();

        game.join();

        System.exit(0);
    }

}
