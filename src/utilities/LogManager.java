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

    public LogManager(){
        logfile = "unnamed.log";
        initializeLogfile();
    }

    public LogManager(String fileName){
        logfile = fileName;
        initializeLogfile();
    }

    private boolean isLogfileExists(){
        File f = new File(logfile);
        return f.exists();
    }

    private void initializeLogfile(){
        String line = isLogfileExists() ? "------------------------------------------------" : "TYPE\tMESSAGE";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logfile, true));){
            writer.write(line+"\n");
            writeLogMessage("APPLICATION STARTED", INFO_LOG);
        }catch(IOException e){
            DialogManager.informException("Log file couldn't open.\nNo logs will be recorded.", null);
        }
    }

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

    public void logException(Exception e){
        writeLogMessage(e.getMessage(), ERROR_LOG);
        writeLogMessage(e.getStackTrace().toString(), ERROR_LOG);
    }

    

}