package planning.match.participants;

import java.util.Date;
import java.sql.*;

public class Ramasseur {
    
    private int id_ramasseur;
    private String nom;
    private String prenom;
    private Connection co;
    
    public Ramasseur(Connection co,int id_ramasseur,String nom,String prenom){
        this.co = co;
        this.id_ramasseur = id_ramasseur;
        this.nom = nom;
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Ramasseur {" + "\nid_ramasseur = " + id_ramasseur + "\nnom = " + nom + "\nprenom=" + prenom + " }";
    }
            
    public int getId_ramasseur() {
        return id_ramasseur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    
    //A FAIRE
    public boolean estDisponible(Date date, String creneau) throws SQLException{
         /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'arbitre est assigné au match
                SI OUI --> return false (pas dispo)
                SI NON --> return true (dispo)
        */
        Boolean dispo;

        Statement stmt = this.co.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT id_ramasseur "
                                         + "FROM ASSIGNMENT_RAMASSEUR "
                                         + "WHERE id_match IN("
                                                               + "SELECT id_match"
                                                               + " FROM MATCHS "
                                                               + "WHERE date_match = " + date + "AND creneau_match = " + creneau
                                        + ")");
        if(rset.next()) dispo =  false;
        else dispo = true;
        
        stmt.close();
        rset.close();
        return dispo;
    }
  
}
