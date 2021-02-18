package xyz.fluxinc.MathsGame;

import com.sun.istack.internal.Nullable;
import xyz.fluxinc.MathsGame.errors.LoggingFailedException;
import xyz.fluxinc.MathsGame.errors.StorageFailedException;
import xyz.fluxinc.MathsGame.logger.FileLogger;
import xyz.fluxinc.MathsGame.logger.LogLevel;
import xyz.fluxinc.MathsGame.logger.Logger;

import java.io.File;

public class MathsGame {

    public static LogLevel LOG_LEVEL = LogLevel.DEBUG;
    private static Logger logger;
    private static Session currentSession;
    public static int MAX_QUESTIONS = 10;

    public static void main(String[] args) throws LoggingFailedException {
        logger = new FileLogger(new File("latest.log"));
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is a error message");
        logger.fatal("This is a fatal message");
        logger.error(new StorageFailedException("This is a non-fatal exception"));
        logger.fatal(new StorageFailedException("This is a fatal exception"));
    }

    public static Logger getLogger() { return logger; }

    @Nullable
    public static Session getSession() { return currentSession; }
}
