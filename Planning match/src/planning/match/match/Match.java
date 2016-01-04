package planning.match.match;

import java.util.List;
import planning.match.participants.*;
import java.util.ArrayList;
import java.sql.Date;


public class Match{
    
    private int id_match;
    private Date date;
    private String creneau;
    private String categorie_match;
    private String tour;
    private Court court;
    
    private Arbitre arbitreChaise, arbitreFilet;
    private List<Arbitre> arbitresLigne = new ArrayList<>();
    private List<Ramasseur> ramasseursDroite = new ArrayList<>();
    private List<Ramasseur> ramasseursGauche = new ArrayList<>();
    private Participant participant1;
    private Participant participant2;
    
    
    public Match(int id_match, Date date, String creneau, String categorie, String tour){
        this.date = date;   
        this.creneau = creneau;
        this.categorie_match = categorie;
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "Match{" + "id_match=" + id_match + ", date=" + date + ", creneau=" + creneau + ", categorie_match=" 
                + categorie_match + ", tour=" + tour + ", court=" + court + ", arbitreChaise=" + arbitreChaise 
                + ", arbitreFilet=" + arbitreFilet + ", arbitresLigne=" + arbitresLigne + ", ramasseursDroite=" 
                + ramasseursDroite + ", ramasseursGauche=" + ramasseursGauche + ", participant1=" + participant1 
                + ", participant2=" + participant2 + '}';
    }
    
    public int getId_match() {
        return id_match;
    }

    public Date getDate() {
        return date;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public void setDate(Date date){
        this.date = date;
    }
    
}
