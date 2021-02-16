package xyz.fluxinc.MathsGame;

import org.fusesource.jansi.AnsiConsole;
import xyz.fluxinc.MathsGame.logger.ConsoleLogger;
import xyz.fluxinc.MathsGame.logger.LogLevel;
import xyz.fluxinc.MathsGame.logger.Logger;

public class MathsGame {

    public static LogLevel LOG_LEVEL = LogLevel.INFO;
    private static Logger logger = new ConsoleLogger();

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
    }

    public static Logger getLogger() { return logger; }
}
