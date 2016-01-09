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
import planning.match.match.Match;

public class Equipe extends Participant{
    
    private int id_equipe;
    private String tour;
    private Joueur joueur1;
    private Joueur joueur2;
    private Connection co;
    
    public Equipe(Connection co,int id_equipe, String tour, Joueur j1, Joueur j2){
        this.co = co;
        this.id_equipe = id_equipe;
        this.tour = tour;
        this.joueur1 = j1;
        this.joueur2 = j2;
    }

    public void assignerAMatch(Match match){
        
    }
    
    public int getId_equipe() {
        return id_equipe;
    }

    public String getTour() {
        return tour;
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

    public void setTour(String tour) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException {
        this.tour = tour;

        Statement stmt = this.co.createStatement();
        stmt.executeQuery("UPDATE JOUEUR SET qualification = " + tour + " WHERE id_joueur = " + this.id_equipe);
        stmt.close();
    }

    @Override
    public String toString() {
        return "Equipe {" + "\nid_equipe = " + id_equipe + "\ntour = " + tour + " }";
    }
    
    public boolean estDisponible(Date date, String creneau) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
        return joueur1.estDisponible(date, creneau) && joueur2.estDisponible(date, creneau);
    }   
}
