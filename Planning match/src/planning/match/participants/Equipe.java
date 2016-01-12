package planning.match.participants;

import bdd.ConfigConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import planning.match.match.Match;

public class Equipe extends Participant{
    
    private int id_equipe;
    private int id_j1;
    private int id_j2;
    private Joueur joueur1;
    private Joueur joueur2;
    private Connection co;
    
    public Equipe(Connection co,int id_equipe,int id_j1, int id_j2){
        this.co = co;
        this.id_equipe = id_equipe;
        this.id_j1 = id_j1;
        this.id_j2 = id_j2;
        findJoueurs();
    }

    public void assignerAMatch(int id_match){
        
    }
    
    private void findJoueurs(){
        try {
            Statement stmt = co.createStatement();
            String query = "SELECT * FROM JOUEUR WHERE id_joueur = " + id_j1 + ";";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            this.joueur1 = new Joueur(co,rs.getInt("id_joueur"),rs.getString("nom_joueur"), rs.getString("prenom_joueur"), rs.getString("nationalite"), rs.getString("sexe"));
            
            query = "SELECT * FROM JOUEUR WHERE id_joueur = " + id_j2 + ";";
            rs = stmt.executeQuery(query);
            rs.next();
            this.joueur2 = new Joueur(co,id_j1,rs.getString("nom_joueur"), rs.getString("prenom_joueur"), rs.getString("nationalite"), rs.getString("sexe"));
        } catch (SQLException ex) {
            Logger.getLogger(Equipe.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    public int getId_equipe() {
        return id_equipe;
    }


    public List<Joueur> getJoueurs(){
        List<Joueur> joueurs = new ArrayList<>();
        joueurs.add(this.joueur1);
        joueurs.add(this.joueur2);
        return joueurs;
    }
    
    public void setId_equipe(int id_equipe) {
        this.id_equipe = id_equipe;
    }


    @Override
    public String toString() {
        return "Equipe {" + "\nid_equipe = " + id_equipe + " }";
    }
    
    public boolean estDisponible(Date date, String creneau) {
        return joueur1.estDisponible(date, creneau) && joueur2.estDisponible(date, creneau);
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
    
    public boolean estQualifie(String tour){
        boolean qualifie = false;
        String tour_precedent = getTourPrecedent(tour);
        //Si on est au premier tour, le joueur est qualifie direct
        if(tour_precedent.equals("Rien")) return true;
        try {
            Statement stmt = co.createStatement();
            String query = "SELECT id_equipe FROM GAGNANT_DOUBLE WHERE id_match IN(SELECT id_match FROM MATCH WHERE tour_match = " + tour_precedent + ");";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                if(rs.getInt("id_equipe") == this.id_equipe){
                    qualifie = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qualifie;
    }
}
