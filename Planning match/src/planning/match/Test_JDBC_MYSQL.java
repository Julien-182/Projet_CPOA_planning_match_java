package planning.match;

import bdd.ConnectionMySQL;
import java.sql.*;
        
/**
 *
 * @author PONTONNIER
 */
public class Test_JDBC_MYSQL {
    
    
    public static void main(String[] args) throws SQLException {
         
        ConnectionMySQL coSQL = new ConnectionMySQL();
        Connection co = coSQL.getConnection();
        
        Statement stmt = co.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT nom_joueur, prenom_joueur\n" +
                                        "FROM JOUEUR\n" +
                                        "WHERE id_joueur\n" +
                                        "IN (\n" +
                                        "SELECT id_joueur1\n" +
                                        "FROM EQUIPE\n" +
                                        "WHERE id_equipe =1\n" +
                                        "UNION SELECT id_joueur2\n" +
                                        "FROM EQUIPE\n" +
                                        "WHERE id_equipe =1\n" +
                                        ")");
        
        while(rs.next()){
            String nom = rs.getString("nom_joueur");
            String prenom = rs.getString("prenom_joueur");
            
            System.out.println("Nom : " + nom + ", prenom : " + prenom);
        }
        
        rs.close();
        stmt.close();
        co.close();
    }
    
}
