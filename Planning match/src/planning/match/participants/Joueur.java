package planning.match.participants;

import bdd.ConfigConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Joueur extends Participant{
    
    private int id_joueur;
    private String nom_joueur;
    private String prenom_joueur;
    private String qualification;
    private String nationalite;
    private String sexe;
    
    public Joueur(int id_joueur, String nom_joueur, String prenom_joueur, String qualification, String nationalite, String sexe){
        this.id_joueur = id_joueur;
        this.nom_joueur = nom_joueur;
        this.prenom_joueur = prenom_joueur;
        this.qualification = qualification;
        this.nationalite = nationalite;
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Joueur {" + "\nid_joueur = " + id_joueur + "\nnom_joueur = " + nom_joueur + "\nprenom_joueur = " + prenom_joueur + "\nqualification = " + qualification + "\nnationalite = " + nationalite +"\nsexe = "+ sexe + " }";
    }

    public int getId_joueur() {
        return id_joueur;
    }

    public String getNom_joueur() {
        return nom_joueur;
    }

    public String getPrenom_joueur() {
        return prenom_joueur;
    }

    public String getQualification() {
        return qualification;
    }
    
    public String getNationalite(){
        return nationalite;
    }

    public String getSexe() {
        return sexe;
    }

    public void setQualification(String qualification) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException {
        this.qualification = qualification;
        Class.forName("oracle.jdbc.OracleDriver");
            // Connexion à la base
        Connection conn = new ConfigConnection().getConnection ("connect.properties");
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("UPDATE JOUEUR SET qualification = " + qualification + " WHERE id_joueur = " + this.id_joueur);
        rset.close();
        conn.close();
    }
    
    //A FAIRE
    public boolean estDisponible(Date date, String creneau) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
         /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'arbitre est assigné au match
                SI OUI --> return false (pas dispo)
                SI NON --> return true (dispo)
        */
        Boolean dispo;
        Class.forName("oracle.jdbc.OracleDriver");
            // Connexion à la base
        Connection conn = new ConfigConnection().getConnection ("connect.properties");
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT id_joueur "
                                         + "FROM ASSIGNMENT_JOUEUR "
                                         + "WHERE id_match IN("
                                                               + "SELECT id_match"
                                                               + " FROM MATCHS "
                                                               + "WHERE date_match = date AND creneau_match = creneau"
                                        + ")");
        if(rset.next()) dispo =  false;
        else dispo = true;
        
        rset.close();
        conn.close();
        return dispo;
    }
}
