package xyz.fluxinc.MathsGame.logger;

public enum LogLevel implements Comparable<LogLevel> {

    DEBUG(0),
    INFO(1),
    WARN(2),
    ERROR(3),
    FATAL(4);

    private final int level;

    LogLevel(int value) {
        this.level = value;
    }

    public static boolean compareLevel(LogLevel expectedLevel, LogLevel actualLevel) {
        return expectedLevel.level >= actualLevel.level;
    }
}
