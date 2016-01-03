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
    private String nom;
    private String prenom;
    private String tour;
    private String nationalite;
    
    public Joueur(int id_joueur, String nom, String prenom, String tour, String nationalite){
        this.id_joueur = id_joueur;
        this.nom = nom;
        this.prenom = prenom;
        this.tour = tour;
        this.nationalite = nationalite;
    }

    @Override
    public String toString() {
        return "Joueur {" + "\nid_joueur = " + id_joueur + "\nnom = " + nom + "\nprenom = " + prenom + "\ntour = " + tour + "\nnationalite = " + nationalite +" }";
    }

    public int getId_joueur() {
        return id_joueur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTour() {
        return tour;
    }
    
    public String getNationalite(){
        return nationalite;
    }

    public void setTour(String tour) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException {
        this.tour = tour;
        Class.forName("oracle.jdbc.OracleDriver");
            // Connexion à la base
        Connection conn = new ConfigConnection().getConnection ("connect.properties");
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("UPDATE JOUEUR SET qualification = " + tour + " WHERE id_joueur = " + this.id_joueur);
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
