package xyz.fluxinc.MathsGame.logger;

public abstract class Logger {

    public abstract void debug(String message);
    public abstract void info(String message);
    public abstract void warn(String message);
    public abstract void error(String message);
    public abstract void fatal(String message);
    public abstract void error(Exception e);
    public abstract void fatal(Exception e);
}
