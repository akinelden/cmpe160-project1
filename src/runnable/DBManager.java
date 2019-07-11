package runnable;

import java.sql.*;
import java.util.ArrayList;

public class DBManager{
    static final String JDBC_DRIVER = "org.sqlite.JDBC";  
    static final String DB_URL_PREFIX = "jdbc:sqlite:";
    private String dbURL;
    private String dbName;
    private String tableName = "Highscores";
    private Connection con;
    private boolean isConnected = true;

    public DBManager(String db){
        dbName = db;
        dbURL = DB_URL_PREFIX+dbName;
        try{
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(dbURL);
            DatabaseMetaData dbMeta = con.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, tableName, new String[]{"TABLE"});
            boolean tableExists = true;
            if(tables.next()){
                ResultSet column1 = dbMeta.getColumns(null, null, tableName, "ID");
                ResultSet column2 = dbMeta.getColumns(null, null, tableName, "Player"); 
                ResultSet column3 = dbMeta.getColumns(null, null, tableName, "Score");
                if(!column1.next() || !column2.next() || column3.next()){
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("DROP TABLE "+tableName);
                    tableExists = false;
                }
            }
            else{
                tableExists = false;
            }
            if(!tableExists){
                Statement stmt = con.createStatement();
                stmt.executeUpdate("CREATE TABLE "+tableName+
                        " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        "Player TEXT NOT NULL, "+
                        "Score INTEGER NOT NULL)");
                stmt.executeUpdate("CREATE INDEX score_index ON "+tableName+" (Score)");
                stmt.close();
            }
        }catch(SQLException e1){
            e1.printStackTrace();
            isConnected = false;
        }catch(Exception e2){
            e2.printStackTrace();
            isConnected = false;
        }
        
    }

    public ArrayList<String[]> readScores(){
        ArrayList<String[]> scores = new ArrayList<>();
        if(!isConnected){
            return scores;
        }
        String[] results = new String[2];
        String selectScores = "SELECT Player, Score FROM "+tableName+" ORDER BY Score DESC LIMIT 10";
        try(PreparedStatement stmt = con.prepareStatement(selectScores);
            ResultSet rs = stmt.executeQuery();){
                while(rs.next()){
                    results[0] = rs.getString("Player");
                    results[1] = Integer.toString(rs.getInt("Score"));
                    scores.add(results);
                }

        }catch(SQLException e){
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

}