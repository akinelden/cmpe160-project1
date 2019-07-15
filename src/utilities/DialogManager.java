package utilities;

import java.awt.Component;
import javax.swing.JOptionPane;

public class DialogManager{

    public static final int ERROR_MSG = 0;
    public static final int INFO_MSG = 1;
    public static final int WARN_MSG = 2;
    public static final int QUES_MSG = 3;

    private static final String[] messageTitles = {"Error", "Info", "Warning", "Question"};
    

    /**
     * Shows a message to the user
     * 
     * @param message Message to be showed
     * @param messageType Type of the message
     * @param parent Parent component of the window
     */
    public static void showMessage(String message, int messageType, Component parent){
        if(messageType<0 || messageType > 3){
            messageType = 0;
        }
        JOptionPane.showMessageDialog(parent, message, messageTitles[messageType], messageType);
    }

    /**
     * Shows an error message to the user.
     * @param message Message to be showed
     * @param parent Parent component of the window
     */
    public static void informException(String message, Component parent){
        showMessage(message, ERROR_MSG, parent);
    }

    /**
     * Creates a dialog window with YES/NO options and shows the message to the user.
     * It is used to confirm an action by the user.
     * @param message Message to be showed
     * @param parent Parent component of the window
     * @return Returns <code>true</code> if user presses YES, <code>false</code> otherwise.
     */
    public static boolean askForAction(String message, Component parent){
        return JOptionPane.showConfirmDialog(parent, message, "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION ? true : false;
    }

    /**
     * Creates a dialog window with YES/NO/CANCEL options and shows the message to the user.
     * @param message Message to be showed
     * @param parent Parent component of the window
     * @return Returns <code>0</code> if user presses YES, 
     * <code>1</code> if user presses NO, 
     * <code>2</code> if user presses CANCEL.
     */
    public static int showConfirmDialog(String message, Component parent){
        return JOptionPane.showConfirmDialog(parent, message, "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
    }
}