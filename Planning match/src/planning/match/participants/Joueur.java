package planning.match.participants;

import bdd.ConfigConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import planning.match.match.Match;

public class Joueur extends Participant{
    
    private Connection co;
    private int id_joueur;
    private String nom_joueur;
    private String prenom_joueur;
    private String nationalite;
    private String sexe;
    
    public Joueur(Connection co,int id_joueur, String nom_joueur, String prenom_joueur, String nationalite, String sexe){
        this.co = co;
        this.id_joueur = id_joueur;
        this.nom_joueur = nom_joueur;
        this.prenom_joueur = prenom_joueur;
        this.nationalite = nationalite;
        this.sexe = sexe;
    }

    public void assignerAMatch(Match match){
        try {
            Statement stmt = co.createStatement();
            String query = "INSERT INTO ASSIGNEMENT_JOUEUR VALUES (" + match.getId_match() + "," + this.id_joueur + ");";
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Override
    public String toString() {
        return "Joueur {" + "\nid_joueur = " + id_joueur + "\nnom_joueur = " + nom_joueur + "\nprenom_joueur = " + prenom_joueur + "\nnationalite = " + nationalite +"\nsexe = "+ sexe + " }";
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
    
    public String getNationalite(){
        return nationalite;
    }

    public String getSexe() {
        return sexe;
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

        Statement stmt = this.co.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT id_joueur "
                                         + "FROM ASSIGNMENT_JOUEUR "
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
