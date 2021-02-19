package xyz.fluxinc.MathsGame.ui;

import xyz.fluxinc.MathsGame.calculations.Calculation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static xyz.fluxinc.MathsGame.MathsGame.*;

public class CalculationScreen extends Screen implements ActionListener {

    private final Calculation calculation;
    private final JTextField answerBox = new JTextField();


    public CalculationScreen(Calculation calculation) {
        this.calculation = calculation;
    }

    @Override
    public void display(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(4,1));	//Changes the windows layout

        answerBox.addActionListener(this);
        answerBox.setActionCommand("answer");
        JButton button = new JButton("Submit Answer");
        button.setActionCommand("answer");
        button.addActionListener(this);

        frame.add(new JLabel(calculation.toString()));
        frame.add(answerBox);
        frame.add(button);
        frame.add(new JLabel("Score: " + getSession().getScore() + "/" + MAX_QUESTIONS));
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("answer")) {
            double answer;
            try { answer = Double.parseDouble(answerBox.getText()); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(null, "You must input a valid number"); return; }

            getSession().addAnsweredQuestion(calculation, answer);
            showNextQuestion();
        }
    }

}
