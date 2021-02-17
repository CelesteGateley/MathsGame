package xyz.fluxinc.MathsGame.errors;

import javax.swing.*;

public class LoggingFailedException extends Exception {
    public LoggingFailedException(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message, "An error has occurred during logging", JOptionPane.ERROR_MESSAGE);
    }

    public LoggingFailedException(Exception exception) {
        super(exception);
        JOptionPane.showMessageDialog(null, exception.getMessage(), "An error has occurred during logging", JOptionPane.ERROR_MESSAGE);
    }
}
