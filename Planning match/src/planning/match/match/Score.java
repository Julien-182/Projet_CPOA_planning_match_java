package planning.match.match;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bobbybel
 */
public class Score{
    private int id_match;
    private int id_joueur1;
    private int id_joueur2;
    private String score_joueur1;
    private String score_joueur2;
    private Connection co;
    
    public Score(Connection co,int id_joueur1, int id_joueur2, String score_joueur1, String score_joueur2){
        this.co = co;
        this.id_joueur1 = id_joueur1;
        this.id_joueur1 = id_joueur2;
        this.score_joueur1 = score_joueur1;
        this.score_joueur2 = score_joueur2;
    }

    public String getScorejoueur1() {
        return score_joueur1;
    }

    public String getScorejoueur2() {
        return score_joueur2;
    }

    public int getId_joueur1() {
        return id_joueur1;
    }

    public int getId_joueur2() {
        return id_joueur2;
    }

    public int getId_match() {
        return id_match;
    }

    public void setScorejoueur1(String score_joueur1) {
        this.score_joueur1 = score_joueur1;
    }

    public void setScorejoueur2(String score_joueur2) {
        this.score_joueur2 = score_joueur2;
    }

    public void setId_joueur1(int id_joueur1) {
        this.id_joueur1 = id_joueur1;
    }

    public void setId_joueur2(int id_joueur2) {
        this.id_joueur2 = id_joueur2;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }
    
    public void addToDataBase(){
        try {
            Statement stmt = co.createStatement();
            String query = "INSERT INTO SCORE(id_match,id_joueur1,id_joueur2,score_joueur1,score_joueur2) "
                            + "VALUES (" + this.id_match + "," + this.id_joueur1 + "," + this.id_joueur2 + "," + this.score_joueur1 +","+ this.score_joueur2 + ");";
        } catch (SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
