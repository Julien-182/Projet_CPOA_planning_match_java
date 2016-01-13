package participants;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import match.Match;

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

    public void assignerAMatch(int id_match){
        try {
            Statement stmt = co.createStatement();
            String query = "INSERT INTO ASSIGNEMENT_JOUEUR VALUES (" + id_match + "," + id_joueur + ");";
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Override
    public String toString() {
        return this.nom_joueur + " " + this.prenom_joueur + "   - " + this.getNationalite();
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
    
    public boolean isHomme(){
        return this.sexe.equals("Homme");
    }
    
    public boolean estQualifie(String tour){
        boolean qualifie = false;
        String tour_precedent = getTourPrecedent(tour);
        //Si on est au premier tour, le joueur est qualifie direct
        if(tour_precedent.equals("Rien")) return true;
        try {
            Statement stmt = co.createStatement();
            String query = "SELECT id_joueur FROM GAGNANT_SIMPLE WHERE id_match IN (SELECT id_match FROM `MATCH` WHERE tour_match = '" + tour_precedent + "');";
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
            case "Qualification" : tour_precedent =  "Rien"; break;
            case "Quart de finale" : tour_precedent = "Qualification"; break;
            case "Demi-finale" : tour_precedent =  "Quart de finale"; break;
            case "Finale" : tour_precedent =  "Demi-finale"; break;
        }
        //System.out.println("Tout précédent = " + tour_precedent);
        return tour_precedent;
    }
    
    public boolean estDisponible(Date date, String creneau){
        try {
            /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'arbitre est assigné au match
            SI OUI --> return false (pas dispo)
            SI NON --> return true (dispo)
            */
            boolean dispo = true;
            String date_string = date.toString();
            
            Statement stmt = this.co.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT id_joueur "
                    + "FROM ASSIGNEMENT_JOUEUR "
                    + "WHERE id_match IN("
                    + "SELECT id_match"
                    + " FROM `MATCH` "
                    + "WHERE date_match = STR_TO_DATE('" + date_string + "','yyyy-MM-dd') AND creneau_match = '" + creneau
                    + "');");
            while(rset.next()){
                if(rset.getInt("id_joueur") == this.id_joueur)
                    dispo = false;
            }
            
            stmt.close();
            rset.close();
            return dispo;
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
