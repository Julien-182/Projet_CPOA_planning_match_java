package planning.match;

import bdd.ConnectionMySQL;
import java.sql.*;
import java.util.ArrayList;
import planning.match.participants.*;
        
/**
 *
 * @author PONTONNIER
 */
public class Test_JDBC2 {
    
    
    public static void main(String[] args) throws SQLException {
        System.out.println("test");
         
        ConnectionMySQL coSQL = new ConnectionMySQL();
        Connection co = coSQL.getConnection();
        
        Statement stmt = co.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM JOUEUR");
        System.out.println("test");
                
        ArrayList<Joueur> joueurs =  new ArrayList<>();
            
        while(rs.next()){
            int id_joueur = rs.getInt("id_joueur");
            String nom_joueur = rs.getString("nom_joueur");
            String prenom_joueur = rs.getString("prenom_joueur");
            String qualification = rs.getString("qualification");
            String nationalite = rs.getString("nationalite");
            String sexe = rs.getString("sexe");
            joueurs.add(new Joueur(id_joueur,nom_joueur,prenom_joueur,qualification,nationalite,sexe));
            System.out.println(id_joueur+nom_joueur+prenom_joueur+qualification+nationalite+sexe);
            System.out.println("test");
        }
        
        /*for(joueur in joueurs){
            System.out.println(joueur);
        }
        System.out.println(joueurs);*/

        rs.close();
        stmt.close();
        co.close();
    }
    
}
