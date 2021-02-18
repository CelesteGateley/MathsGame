package xyz.fluxinc.MathsGame.logger;

import xyz.fluxinc.MathsGame.MathsGame;
import xyz.fluxinc.MathsGame.errors.LoggingFailedException;

import java.io.*;
import java.util.Date;

import static xyz.fluxinc.MathsGame.logger.LogLevel.compareLevel;

public class FileLogger extends Logger{

    private final PrintStream printStream;

    public FileLogger(File logFile) throws LoggingFailedException {
        try {
            logFile.createNewFile();
            printStream = new PrintStream(new FileOutputStream(logFile, true));
        } catch (IOException e) {
            throw new LoggingFailedException("Failed to initialise output stream");
        }
    }

    @Override
    public void debug(String message) {
        if (!compareLevel(LogLevel.DEBUG, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [DEBUG] " + message);
        printStream.flush();
    }

    @Override
    public void info(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.INFO, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [INFO] " + message);
        printStream.flush();
    }

    @Override
    public void warn(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.WARN, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [WARN] " + message);
        printStream.flush();
    }

    @Override
    public void error(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [ERROR] " + message);
        printStream.flush();
    }

    @Override
    public void fatal(String message) throws LoggingFailedException {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [FATAL] " + message);
        printStream.flush();
    }

    @Override
    public void error(Exception e) throws LoggingFailedException {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [ERROR] An error has occurred, details are below");
        printStream.println(new String(new char[120]).replace("\0", "-"));
        e.printStackTrace(printStream);
        printStream.println(new String(new char[120]).replace("\0", "-"));
        printStream.flush();
    }

    @Override
    public void fatal(Exception e) throws LoggingFailedException {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        printStream.println(dateFormat.format(new Date()) + " [FATAL] A fatal error has occurred, details are below");
        printStream.println(new String(new char[120]).replace("\0", "="));
        e.printStackTrace(printStream);
        printStream.println(new String(new char[120]).replace("\0", "="));
        printStream.flush();
    }
}
