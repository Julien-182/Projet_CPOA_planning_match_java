package planning.match.match;

import java.util.List;
import planning.match.participants.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Match{
    
    private int id_match;
    private String date;
    private String creneau;
    private String categorie_match;
    private String tour;
    private String court;
    
    private Arbitre arbitreChaise, arbitreFilet;
    private List<Arbitre> arbitresLigne = new ArrayList<>();
    private List<Ramasseur> ramasseursDroite = new ArrayList<>();
    private List<Ramasseur> ramasseursGauche = new ArrayList<>();
    private Participant participant1;
    private Participant participant2;
    
    
    public Match(int id_match, String date, String creneau, String categorie, String tour){
        this.id_match = id_match;
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy" );
        Date currentTime_1 = new Date();
        String dateString = formatter.format(currentTime_1);
        this.date = dateString;  
        this.creneau = creneau;
        this.categorie_match = categorie;
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "Match {" + "\nid_match = " + id_match + "\ndate = " + date + " }";
    }
    
    public int getId_match() {
        return id_match;
    }

    public String getDate() {
        return date;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
