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
    private int id_court;
    
    private Arbitre arbitreChaise, arbitreFilet;
    private List<Arbitre> arbitresLigne = new ArrayList<>();
    private List<Ramasseur> ramasseursDroite = new ArrayList<>();
    private List<Ramasseur> ramasseursGauche = new ArrayList<>();
    private Participant participant1;
    private Participant participant2;
    
    
    public Match(int id_match, Date date, String creneau, String categorie, String tour, int id_court){
        this.date = date;   
        this.creneau = creneau;
        this.categorie_match = categorie;
        this.tour = tour;
        this.id_court = id_court;
    }

    @Override
    public String toString() {
        return "Match{" + "id_match=" + id_match + ", date=" + date + ", creneau=" + creneau + ", categorie_match=" 
                + categorie_match + ", tour=" + tour + ", court=" + id_court + ", arbitreChaise=" + arbitreChaise 
                + ", arbitreFilet=" + arbitreFilet + ", arbitresLigne=" + arbitresLigne + ", ramasseursDroite=" 
                + ramasseursDroite + ", ramasseursGauche=" + ramasseursGauche + ", participant1=" + participant1 
                + ", participant2=" + participant2 + '}';
    }
    
    public int getId_match() {
        return id_match;
    }
    
    public String getDateString(){
  
        String jour_s,mois_s,annee_s;
        String date_s = date.toString();
        //System.out.println(date_s);
        //2016-01-05
        annee_s = date_s.substring(0, 4);
        mois_s = date_s.substring(5, 7);
        jour_s = date_s.substring(8,10);
        /*
        System.out.println("jour " + jour_s);
        System.out.println("mois " + mois_s);
        System.out.println("annee " + annee_s);
        */
        switch(mois_s){
            case "01": mois_s = "Janvier";
                    break;
            case "02" : mois_s = "Février";
                    break;
            case "03": mois_s = "Mars";
                    break;
            case "04" : mois_s = "Avril";
                    break;
            case "05": mois_s = "Mai";
                    break;
            case "06" : mois_s = "Juin";
                    break;
            case "07": mois_s = "Juillet";
                    break;
            case "08" : mois_s = "Août";
                    break;   
            case "09": mois_s = "Septembre";
                    break;
            case "10" : mois_s = "Octobre";
                    break;
            case "11": mois_s = "Novembre";
                    break;
            case "12" : mois_s = "Décembre";
                    break;
            default : mois_s = "Problème :/";
                    break;
        }        
        return jour_s + " " + mois_s + " " + annee_s;
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
