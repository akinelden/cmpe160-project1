package runnable;

import java.sql.*;
import java.util.ArrayList;

public class DBManager{
    static final String JDBC_DRIVER = "org.sqlite.JDBC";  
    static final String DB_URL_PREFIX = "jdbc:sqlite:";
    private String dbURL;
    private String dbName;
    private Connection con;
    private boolean isConnected = true;

    public DBManager(String db){
        dbName = db;
        dbURL = DB_URL_PREFIX+db;
        String tableName = "Highscores";
        try{
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(dbURL);
            DatabaseMetaData dbMeta = con.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, tableName, new String[]{"TABLE"});
            boolean tableExists = true;
            if(tables.first()){
                ResultSet column1 = dbMeta.getColumns(null, null, tableName, "ID");
                ResultSet column2 = dbMeta.getColumns(null, null, tableName, "Player"); 
                ResultSet column3 = dbMeta.getColumns(null, null, tableName, "Score");
                if(!column1.first() || !column2.first() || column3.first()){
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
                        " (ID INT PRIMARY KEY AUTOINCREMENT, "+
                        "Player TEXT NOT NULL, "+
                        "Score INT NOT NULL)");
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


    public ArrayList<String[]> readScores(String dbName){
        ArrayList<String[]> scores = new ArrayList<>();
        if(!isConnected){
            return scores;
        }
        String[] results = new String[2];
        String selectScores = "SELECT Player, Score FROM Highscores ORDER BY Score DESC LIMIT 10";
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

    public void writeScores(ArrayList<String[]> scores){
    }

}