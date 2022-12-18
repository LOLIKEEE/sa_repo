package sa.homework.logger.classes;

import sa.homework.logger.enums.Destination;
import sa.homework.logger.enums.Level;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static Destination logDestination = Destination.CONSOLE;

    public static void debug(String message) {
        sendLogItem(new LogItem(Level.DEBUG, message, LocalDateTime.now()));
    }


    public static void info(String message) {
        sendLogItem(new LogItem(Level.INFO, message, LocalDateTime.now()));
    }


    public static void warn(String message) {
        sendLogItem(new LogItem(Level.WARN, message, LocalDateTime.now()));
    }


    public static void error(String message) {
        sendLogItem(new LogItem(Level.ERROR, message, LocalDateTime.now()));
    }


    public static void fatal(String message) {
        sendLogItem(new LogItem(Level.FATAL, message, LocalDateTime.now()));
    }

    private static void sendLogItem(LogItem logItem) {
        switch (logDestination) {
            case FILE:
                WriteFile.writeLog(logItem);
                break;
            case CONSOLE_AND_FILE:
                WriteFile.writeLog(logItem);
            case CONSOLE:
                logItem.print();
        }
    }

    public static void printAllLogs() {
        for (LogItem log : ReadFile.getAllLogs()) {
            log.print();
        }
    }


    public static List<LogItem> getAllLogs(LocalDate localDate) {
        List<LogItem> logList = ReadFile.getAllLogs();
        List<LogItem> logListByDate = new ArrayList<>();
        for (LogItem log : logList) {
            if (log.getDateTime().toLocalDate().equals(localDate)) {
                logListByDate.add(log);
            }
        }
        return logListByDate;
    }

    public static List<LogItem> getAllLogs(LocalTime start, LocalTime end, LocalDate localDate) {
        List<LogItem> logListByDate = getAllLogs(localDate);
        List<LogItem> logListByDateTime = new ArrayList<>();
        for (LogItem log : logListByDate) {
            if (log.getDateTime().toLocalTime().isAfter(start) &&
                    log.getDateTime().toLocalTime().isBefore(end)) {
                logListByDateTime.add(log);
            }
        }
        return logListByDateTime;
    }

    public static List<LogItem> getAllLogs(Level level) {
        List<LogItem> logList = ReadFile.getAllLogs();
        List<LogItem> logListByLevel = new ArrayList<>();
        for (LogItem log : logList) {
            if (log.getLevel().equals(level)) {
                logListByLevel.add(log);
            }
        }
        return logListByLevel;
    }

    public static List<LogItem> getAllLogs(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getAllLogs(localDate);
    }

    public static void setDestination(Destination destination) {
        logDestination = destination;
    }
}
