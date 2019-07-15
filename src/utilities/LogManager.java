package utilities;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager{

    public static final int INFO_LOG = 0;
    public static final int WARN_LOG = 1;
    public static final int ERROR_LOG = 2;

    private static final String[] logTypes = {"INFO", "WARN", "ERROR"};
    
    private String logfile;

    private LogManager(){}

    /**
     * Creates a log file with given name.
     * @param fileName Name of the log file to be created
     */
    public LogManager(String fileName){
        logfile = fileName;
        String line = (new File(logfile)).exists() ? "------------------------------------------------" : "TYPE\tMESSAGE";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));){
            writer.write(line+"\n");
            writer.write("INFO\tAPPLICATION STARTED\n");
        }catch(IOException e){
            DialogManager.informException("Log file couldn't open.\nNo logs will be recorded.", null);
        }
    }

    /**
     * Writes a log message with given type.
     * @param message Message to be writed on logfile
     * @param type Type of the message
     * @return <code>true</code> if operation is successful, <code>false</code> otherwise
     */
    public boolean writeLogMessage(String message, int type){
        if(type<0 || type >= logTypes.length){
            type = 0;
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));){
            writer.write(logTypes[type]+"\t"+message+"\n");
            return true;
        }catch(IOException e){
            return false;
        }
    }

    /**
     * Writes the message of an exception to the logfile
     * @param e Exception to be logged
     */
    public void writeExceptionLog(Exception e){
        writeLogMessage(e.getMessage(), ERROR_LOG);
    }
}