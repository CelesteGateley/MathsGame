package xyz.fluxinc.MathsGame.logger;

import xyz.fluxinc.MathsGame.MathsGame;
import xyz.fluxinc.MathsGame.errors.LoggingFailedException;

import java.io.*;
import java.util.Date;

import static xyz.fluxinc.MathsGame.logger.LogLevel.compareLevel;

public class FileLogger extends Logger{

    private final File logFile;

    public FileLogger(File logFile) {
        this.logFile = logFile;
        try { logFile.createNewFile(); }
        catch (IOException e) { e.printStackTrace();}
    }

    @Override
    public void debug(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.DEBUG, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [DEBUG] " + message);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }

    @Override
    public void info(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.INFO, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [INFO] " + message);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }

    @Override
    public void warn(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.WARN, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [WARN] " + message);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }

    @Override
    public void error(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [ERROR] " + message);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }

    @Override
    public void fatal(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [FATAL] " + message);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }

    @Override
    public void error(Exception e) throws LoggingFailedException {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [ERROR] An error has occurred, details are below");
            e.printStackTrace(printStream);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }

    @Override
    public void fatal(Exception e) throws LoggingFailedException {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(logFile, true));
            printStream.println(dateFormat.format(new Date()) + " [FATAL] A fatal error has occurred, details are below");
            e.printStackTrace(printStream);
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException err) { throw new LoggingFailedException(err); }
    }
}
