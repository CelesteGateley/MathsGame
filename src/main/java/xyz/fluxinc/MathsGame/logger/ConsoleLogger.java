package xyz.fluxinc.MathsGame.logger;

import com.diogonunes.jcolor.Attribute;
import xyz.fluxinc.MathsGame.MathsGame;

import java.util.Date;

import static com.diogonunes.jcolor.Ansi.colorize;
import static xyz.fluxinc.MathsGame.logger.LogLevel.compareLevel;

public class ConsoleLogger extends Logger {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");

    @Override
    public void debug(String message) {
        if (!compareLevel(LogLevel.DEBUG, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [DEBUG] " + message, Attribute.TEXT_COLOR(181, 186, 191)));
    }

    @Override
    public void info(String message) {
        if (!compareLevel(LogLevel.INFO, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [INFO] " + message, Attribute.TEXT_COLOR(255, 255, 255)));
    }

    @Override
    public void warn(String message) {
        if (!compareLevel(LogLevel.WARN, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [WARN] " + message, Attribute.TEXT_COLOR(251,255,143)));
    }

    @Override
    public void error(String message) {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [ERROR] " + message, Attribute.TEXT_COLOR(255,136,136)));
    }

    @Override
    public void fatal(String message) {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [FATAL] " + message, Attribute.TEXT_COLOR(255,0,0)));
    }

    @Override
    public void error(Exception e) {
        if (!compareLevel(LogLevel.ERROR, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [ERROR] An error has occurred, details are below", Attribute.TEXT_COLOR(255,136,136)));
        e.printStackTrace();
    }

    @Override
    public void fatal(Exception e) {
        if (!compareLevel(LogLevel.FATAL, MathsGame.LOG_LEVEL)) return;
        System.out.println(dateFormat.format(new Date()) + colorize(" [FATAL] A fatal error has occurred, details below", Attribute.TEXT_COLOR(255,0,0)));
        e.printStackTrace();
    }
}
