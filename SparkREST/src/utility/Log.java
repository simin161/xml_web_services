package utility;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log {
    private static Logger mainLog = null;
    private static Logger infoLog = null;
    private static Logger warnLog = null;
    private static Logger errorLog = null;
    private FileHandler fh = null;
    private FileHandler fh1 = null;
    private FileHandler fh2 = null;
    private FileHandler fh3 = null;
    private static Log instance = null;
    public static Log get(){
        if(instance != null){
            instance = new Log();
        }
        return instance;
    }
    private Log() {
        try {
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "[%1$tF %1$tT] [%4$-7s] %5$s %n");
            mainLog = Logger.getLogger(Log.class.getName());
            fh = new FileHandler("./main.log");
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String retVal = "[" + LocalDateTime.now().toString() + "]";
                    retVal += "  [" + record.getLevel() + "]  " + record.getMessage();
                    return retVal;
                }
            });
            fh1 = new FileHandler("./info.log");
            fh1.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String retVal = "[" + LocalDateTime.now().toString() + "]";
                    retVal += "  [" + record.getLevel() + "]  " + record.getMessage();
                    return retVal;
                }
            });
            fh2 = new FileHandler("./warn.log");
            fh2.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String retVal = "[" + LocalDateTime.now().toString() + "]";
                    retVal += "  [" + record.getLevel() + "]  " + record.getMessage();
                    return retVal;
                }
            });
            fh3 = new FileHandler("./error.log");
            fh3.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String retVal = "[" + LocalDateTime.now().toString() + "]";
                    retVal += "  [" + record.getLevel() + "]  " + record.getMessage();
                    return retVal;
                }
            });
            mainLog.addHandler(fh);
            infoLog.addHandler(fh1);
            warnLog.addHandler(fh2);
            errorLog.addHandler(fh2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Logger getMainLog() {
        return mainLog;
    }

    public static Logger getInfoLog() {
        return infoLog;
    }

    public static Logger getWarnLog() {
        return warnLog;
    }

    public static Logger getErrorLog() {
        return errorLog;
    }
}
