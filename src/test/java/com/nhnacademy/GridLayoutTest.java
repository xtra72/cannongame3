package com.nhnacademy;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GridLayoutTest {
    static int state = 0;
    static String[] operand = { "", "" };
    static String operator = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setSize(300, 300);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        frame.setLayout(new GridBagLayout());
        JButton button;

        JTextField textField = new JTextField("0");
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        constraints.gridy = 0;
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setText("0");
        frame.add(textField, constraints);

        button = new JButton("7");
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        frame.add(button, constraints);

        button = new JButton("8");
        constraints.gridx = 1;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("9");
        constraints.gridx = 2;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("+");
        constraints.gridx = 3;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            if (state == 0) {
                operator = "+";
                state = 1;
                textField.setText("+");
            }
        });

        button = new JButton("4");
        constraints.gridx = 0;
        constraints.gridy = 2;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("5");
        constraints.gridx = 1;
        constraints.gridy = 2;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("6");
        constraints.gridx = 2;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("-");
        constraints.gridx = 3;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            if (state == 0) {
                operator = "-";
                state = 1;
                textField.setText("-");
            }
        });

        button = new JButton("1");
        constraints.gridx = 0;
        constraints.gridy = 3;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("2");
        constraints.gridx = 1;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("3");
        constraints.gridx = 2;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("*");
        constraints.gridx = 3;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            if (state == 0) {
                operator = "*";
                state = 1;
                textField.setText("*");
            }
        });

        button = new JButton("0");
        constraints.gridx = 0;
        constraints.gridy = 4;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[state] = operand[state] + ((JButton) e.getSource()).getText();
            textField.setText(operand[state]);
        });

        button = new JButton("/");
        constraints.gridx = 3;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            if (state == 0) {
                operator = "/";
                state = 1;
                textField.setText("/");
            }
        });

        button = new JButton("=");
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.gridy = 5;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            if (state == 1) {
                int value1 = Integer.parseInt(operand[0]);
                int value2 = Integer.parseInt(operand[1]);
                if (operator.equals("+")) {
                    int result = value1 + value2;
                    operand[0] = String.valueOf(result);
                    textField.setText(operand[0]);
                    operand[1] = "";
                    state = 0;
                } else if (operator.equals("-")) {
                    int result = value1 - value2;
                    operand[0] = String.valueOf(result);
                    textField.setText(operand[0]);
                    operand[1] = "";
                    state = 0;
                } else if (operator.equals("*")) {
                    int result = value1 * value2;
                    operand[0] = String.valueOf(result);
                    textField.setText(operand[0]);
                    operand[1] = "";
                    state = 0;
                } else if (operator.equals("/")) {
                    int result = value1 / value2;
                    operand[0] = String.valueOf(result);
                    textField.setText(operand[0]);
                    operand[1] = "";
                    state = 0;
                }
            }
        });

        button = new JButton("AC");
        constraints.gridx = 3;
        constraints.gridwidth = 1;
        frame.add(button, constraints);
        button.addActionListener(e -> {
            operand[0] = "";
            operator = "";
            operand[1] = "";
            state = 0;
            textField.setText(operand[0]);
        });

        frame.setVisible(true);
    }
}
