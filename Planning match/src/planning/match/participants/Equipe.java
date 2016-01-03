package planning.match.participants;

import bdd.ConfigConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Equipe extends Participant{
    
    private int id_equipe;
    private String tour;
    private Joueur joueur1;
    private Joueur joueur2;
    
    public Equipe(int id_equipe, String tour, Joueur j1, Joueur j2){
        this.id_equipe = id_equipe;
        this.tour = tour;
        this.joueur1 = j1;
        this.joueur2 = j2;
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
        Class.forName("oracle.jdbc.OracleDriver");
            // Connexion à la base
        Connection conn = new ConfigConnection().getConnection ("connect.properties");
        conn.setAutoCommit(false);

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("UPDATE JOUEUR SET qualification = " + tour + " WHERE id_joueur = " + this.id_equipe);
        rset.close();
        conn.close();
    }

    @Override
    public String toString() {
        return "Equipe {" + "\nid_equipe = " + id_equipe + "\ntour = " + tour + " }";
    }
    
}
