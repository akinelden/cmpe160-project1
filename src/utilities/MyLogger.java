package utilities;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger{

    public static final int INFO_LOG = 0;
    public static final int WARN_LOG = 1;
    public static final int ERROR_LOG = 2;

    private static final String[] logTypes = {"INFO", "WARN", "ERROR"};
    
    private String logfile;

    private MyLogger(){}

    /**
     * Creates a log file with given name.
     * @param fileName Name of the log file to be created
     */
    public MyLogger(String fileName){
        logfile = fileName;
        String line = (new File(logfile)).exists() ? "------------------------------------------------" : "DateTime\tType\tMessage";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));){
            writer.write(line+"\n");
            writer.write(getCurrentDateTime()+"\tINFO\tAPPLICATION STARTED\n");
        }catch(IOException e){
            DialogManager.informException("Log file couldn't open.\nNo logs will be recorded.", null);
        }
    }

    private String getCurrentDateTime(){
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(d);
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
            writer.write(getCurrentDateTime() + "\t" + logTypes[type]+"\t"+message+"\n");
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