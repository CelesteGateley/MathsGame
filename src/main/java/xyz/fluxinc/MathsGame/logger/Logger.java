package xyz.fluxinc.MathsGame.logger;

import xyz.fluxinc.MathsGame.errors.LoggingFailedException;

import java.text.SimpleDateFormat;

public abstract class Logger {

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");

    public abstract void debug(String message) throws LoggingFailedException;
    public abstract void info(String message) throws LoggingFailedException;
    public abstract void warn(String message) throws LoggingFailedException;
    public abstract void error(String message) throws LoggingFailedException;
    public abstract void fatal(String message) throws LoggingFailedException;
    public abstract void error(Exception e) throws LoggingFailedException;
    public abstract void fatal(Exception e) throws LoggingFailedException;
}
