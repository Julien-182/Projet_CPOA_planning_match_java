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
    
    public boolean estQualifie(String tour){
        boolean qualifie = false;
        String tour_precedent = getTourPrecedent(tour);
        //Si on est au premier tour, le joueur est qualifie direct
        if(tour_precedent.equals("Rien")) return true;
        try {
            Statement stmt = co.createStatement();
            String query = "SELECT id_joueur FROM GAGNANT_SIMPLE WHERE id_match IN(SELECT id_match FROM MATCH WHERE tour_match = " + tour_precedent + ");";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                if(rs.getInt("id_joueur") == this.id_joueur){
                    qualifie = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qualifie;
    }
    
    private String getTourPrecedent(String tour){
        String tour_precedent = "";
        switch(tour){
            case "Qualification" : tour_precedent =  "Rien";
            case "Quart de finale" : tour_precedent = "Qualification";
            case "Demi-finale" : tour_precedent =  "Quart de finale";
            case "Finale" : tour_precedent =  "Demi-finale";
        }
        return tour_precedent;
    }
    //A FAIRE
    public boolean estDisponible(Date date, String creneau){
        try {
            /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'arbitre est assigné au match
            SI OUI --> return false (pas dispo)
            SI NON --> return true (dispo)
            */
            Boolean dispo;
            
            Statement stmt = this.co.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT id_joueur "
                    + "FROM ASSIGNEMENT_JOUEUR "
                    + "WHERE id_match IN("
                    + "SELECT id_match"
                    + " FROM `MATCH` "
                    + "WHERE date_match = '" + date + "' AND creneau_match = " + creneau
                    + ");");
            if(rset.next()) dispo =  false;
            else dispo = true;
            
            stmt.close();
            rset.close();
            return dispo;
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
