package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;

public class SQLiteManager{
    private static final String JDBC_DRIVER = "org.sqlite.JDBC";  
    private static final String DB_URL_PREFIX = "jdbc:sqlite:";
    private String dbURL;
    private String dbName;
    private String tableName = "Highscores";
    private static final String noConnectionWarn = "Couldn't connect to highscores data. New scores won't be saved.";
    private static final String resetTableWarn = "An error occurred while reading the highscores!\n"+
                                                    "Do you want to delete existing records (if any) and reset the data.\n"+
                                                    "Otherwise new scores won't be saved!";
    private static final String logLabel = "DB_ERROR";                                                
    private boolean isConnected = true;
    private Connection con;
    private Logger logger;

    private SQLiteManager(){}

    public SQLiteManager(String db){
        dbName = db;
        dbURL = DB_URL_PREFIX+dbName;
        logger = Logger.getLogger(SQLiteManager.class.getName());
        try{
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(dbURL);
        }catch(SQLException e1){
            logger.log(Level.SEVERE, logLabel, e1);
            DialogManager.informException(noConnectionWarn, null);
            isConnected = false;
        }catch(Exception e2){
            logger.log(Level.SEVERE, logLabel, e2);
            DialogManager.informException("Some files are missing.\nNew scores won't be saved.", null);
            isConnected = false;
        }
    }

    private boolean checkTableExistence(){
        try{
            DatabaseMetaData dbMeta = con.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, tableName, new String[]{"TABLE"});
            if(tables.next()){
                tables.close();
                return true;
            }
            else{
                return false;
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE, logLabel, e);
            return false;
        }
    }

    private void resetTable(boolean tableExists){
        if(tableExists){
            try(Statement stmt = con.createStatement();){
                stmt.executeUpdate("DROP TABLE "+tableName);
            }catch(SQLException e){
            logger.log(Level.SEVERE, logLabel, e);
            }
        }
        try(Statement stmt = con.createStatement()){
            stmt.executeUpdate("CREATE TABLE "+tableName+
                        " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        "Player TEXT NOT NULL, "+
                        "Score INTEGER NOT NULL)");
            stmt.executeUpdate("CREATE INDEX score_index ON "+tableName+" (Score)");
        }catch(SQLException e){
            logger.log(Level.SEVERE, logLabel, e);
            DialogManager.informException(noConnectionWarn, null);
            isConnected = false;
        }
    }

    public ArrayList<String[]> readScores(){
        ArrayList<String[]> scores = new ArrayList<>();
        if(!isConnected){
            return scores;
        }
        String selectScores = "SELECT Player, Score FROM "+tableName+" INDEXED BY score_index ORDER BY Score DESC LIMIT 10";
        try(PreparedStatement stmt = con.prepareStatement(selectScores);
        ResultSet rs = stmt.executeQuery();){
            while(rs.next()){
                String[] results = new String[2];
                results[0] = rs.getString("Player");
                results[1] = Integer.toString(rs.getInt("Score"));
                scores.add(results);
            }
        }catch(SQLException e){
            boolean tableExists = checkTableExistence();
            if(tableExists){
            logger.log(Level.SEVERE, logLabel, e);
                if(DialogManager.askForAction(resetTableWarn, null)){
                    resetTable(tableExists);
                }
                else{
                    DialogManager.informException(noConnectionWarn, null);
                    isConnected = false;
                }
            }
            else{
                resetTable(tableExists);
            }
        }
        return scores;
    }

    public boolean writeNewScore(String playerName, int score){
        if(!isConnected){
            return false;
        }
        String insertSql = "INSERT INTO "+tableName+" (Player, Score) VALUES ('"+playerName+"', "+Integer.toString(score)+")";
        try(Statement stmt = con.createStatement()){
            stmt.executeUpdate(insertSql);
        }catch(SQLException e){
            boolean tableExists = checkTableExistence();
            if(tableExists){
            logger.log(Level.SEVERE, logLabel, e);
                if(DialogManager.askForAction(resetTableWarn, null)){
                    resetTable(tableExists);
                    if(writeNewScore(playerName, score)){
                        return true;
                    }
                }
                else{
                    DialogManager.informException(noConnectionWarn, null);
                    isConnected = false;
                }
            }
            else{
                resetTable(tableExists);
            }
            return false;
        }
        return true;
    }

}