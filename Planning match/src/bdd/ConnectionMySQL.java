package bdd;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionMySQL {
    
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://iutdoua-webetu.univ-lyon1.fr/p1404643";

    //  Database credentials
    static final String USER = "p1404643";
    static final String PASS = "213622";
    
    public ConnectionMySQL(){}
    
    public Connection getConnection(){
        
        Connection conn = null;
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("SUCCESS");
        return conn;
    }
}
