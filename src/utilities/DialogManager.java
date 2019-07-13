package utilities;

import java.awt.Component;
import javax.swing.JOptionPane;

public class DialogManager{

    public static final int ERROR_MSG = 0;
    public static final int INFO_MSG = 1;
    public static final int WARN_MSG = 2;
    public static final int QUES_MSG = 3;

    private static final String[] messageTitles = {"Error", "Info", "Warning", "Question"};
    

    public static void showMessage(String message, int messageType, Component parent){
        if(messageType<0 || messageType > 3){
            messageType = 0;
        }
        JOptionPane.showMessageDialog(parent, message, messageTitles[messageType], messageType);
    }

    public static void informException(String message, Component parent){
        showMessage(message, ERROR_MSG, parent);
    }

    public static boolean askForAction(Exception e, String message, Component parent){
        return JOptionPane.showConfirmDialog(parent, message, "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ? true : false;
    }

    public static int showConfirmDialog(String message, Component parent){
        return JOptionPane.showConfirmDialog(parent, message, "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
    }
}