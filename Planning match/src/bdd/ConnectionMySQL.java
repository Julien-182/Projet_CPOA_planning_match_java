package bdd;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionMySQL {
    
    //JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/cpoa";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    
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
        }finally{
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }
        }      
        return conn;
    }
}
