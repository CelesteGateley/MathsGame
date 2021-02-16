package xyz.fluxinc.MathsGame.logger;

import org.fusesource.jansi.Ansi;
import xyz.fluxinc.MathsGame.MathsGame;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;
import static xyz.fluxinc.MathsGame.logger.LogLevel.compareLevel;

public class ConsoleLogger extends Logger {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");

    @Override
    public void debug(String message) {
        if (!compareLevel(LogLevel.DEBUG, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(DEFAULT).a(" [DEBUG] " + message));
    }

    @Override
    public void info(String message) {
        if (!compareLevel(LogLevel.INFO, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(WHITE).a(" [INFO] " + message));
    }

    @Override
    public void warn(String message) {
        if (!compareLevel(LogLevel.WARN, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(YELLOW).a(" [WARN] " + message));
    }

    @Override
    public void error(String message) {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(MAGENTA).a(" [ERROR] " + message));
    }

    @Override
    public void fatal(String message) {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(RED).a(" [FATAL] " + message));
    }

    @Override
    public void error(Exception e) {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(MAGENTA).a(" [ERROR] An error has occurred, details below"));
        e.printStackTrace();
    }

    @Override
    public void fatal(Exception e) {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        System.out.println(ansi().a(dateFormat.format(new Date())).fg(RED).a(" [FATAL] A fatal error has occurred, details below"));
        e.printStackTrace();
    }
}
