package planning.match.match;

import java.sql.Connection;
import java.util.List;
import planning.match.participants.*;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Match{
    
    //Valeurs attribuées via le constructeur
    private Connection co;
    private int id_match;
    private Date date;
    private String creneau;
    private String categorie_match;
    private String tour;
    private String court;
    
    //Valeurs à trouver grâce aux méthodes
    private Arbitre arbitreChaise = null, arbitreFilet = null;
    private List<Arbitre> arbitresLigne = new ArrayList<>();
    private EquipeRamasseurs equipe_ramasseurs;
    private List<Joueur> participant1 = new ArrayList<>();
    private List<Joueur> participant2 = new ArrayList<>();
    
    public Match(Connection co,int id_match, Date date, String creneau, String categorie, String tour) throws SQLException{
        this.co = co;
        this.id_match = id_match;
        this.date = date;   
        this.creneau = creneau;
        this.categorie_match = categorie;
        this.tour = tour;
        if(isMatchSimple())
            findJoueurs();
        else
            findEquipe();
        findArbitres();
        findEquipeRamasseurs();
        findCourt();
    }

    public void findJoueurs() throws SQLException{
        String query;
        Statement stmt = co.createStatement();
        int i = 0;
        //cas avec 2 joueurs (simple)
        query = "SELECT * FROM JOUEUR WHERE id_joueur IN (SELECT id_joueur\n" +
                                                         "FROM ASSIGNEMENT_JOUEUR\n" +
                                                         "WHERE id_match = " + id_match + ");";
        ResultSet rs = stmt.executeQuery(query);
        
        //On parcours les 2 joueurs du match
        while(rs.next()){
            int id_joueur = rs.getInt("id_joueur");
            String nom_joueur = rs.getString("nom_joueur");
            String prenom_joueur = rs.getString("prenom_joueur");
            String qualification = rs.getString("qualification");
            String nationalite = rs.getString("nationalite");
            String sexe = rs.getString("sexe");
            if(i ==0){
                participant1.add(new Joueur(co,id_joueur,nom_joueur,prenom_joueur,qualification, nationalite, sexe));
                i++;
            }
            else{
                participant2.add(new Joueur(co,id_joueur,nom_joueur,prenom_joueur,qualification, nationalite, sexe));
            }
        }
        rs.close();
        stmt.close();
    }
    
    public void findEquipe() throws SQLException{
        int id_equipe = 0, i = 0;
        String query;
        Statement stmt = co.createStatement();
        Statement stmt2 = co.createStatement();
        
        query = "SELECT id_equipe FROM ASSIGNEMENT_EQUIPE WHERE id_match = " + id_match + ";";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            //Parcours chaque equipe
            id_equipe = rs.getInt("id_equipe");
            
            query = "SELECT * FROM JOUEUR WHERE id_joueur IN (SELECT id_joueur FROM EQUIPE WHERE id_equipe = " + id_equipe + ");";
            ResultSet rs2 = stmt2.executeQuery(query);
            //On parcours les joueurs de l'équipe. Si on est dans l'équipe 1
            if(i == 0){
                while(rs2.next()){
                    participant1.add(new Joueur(co,
                                        rs.getInt("id_joueur"), 
                                        rs.getString("nom_joueur"), 
                                        rs.getString("prenom_joueur"), 
                                        rs.getString("qualification"), 
                                        rs.getString("nationalite"), 
                                        rs.getString("sexe")));
                }
            }
            //Si on est dfans l'équipe 2
            else{
                while(rs2.next()){
                    participant2.add(new Joueur(co,
                                        rs.getInt("id_joueur"), 
                                        rs.getString("nom_joueur"), 
                                        rs.getString("prenom_joueur"), 
                                        rs.getString("qualification"), 
                                        rs.getString("nationalite"), 
                                        rs.getString("sexe")));
                }
            }
            i++; //permet de préciser que l'on change d'équpe.
        }   
        rs.close();
        stmt.close();
        stmt2.close();
    }
    
    public void findArbitres() throws SQLException{
        findArbitreChaise(); //categorie_arbitre = Chaise
        findArbitreFilet(); //categorie_arbitre = Filet
        findArbitresLigne(); // categoriearbitre = Ligne
    }
    
    public void findArbitreChaise() throws SQLException{
        String query = "SELECT * FROM ARBITRE WHERE id_arbitre IN (SELECT id_arbitre FROM ASSIGNEMENT_ARBITRE WHERE categorie_arbitre = 'Chaise' AND id_match = " + id_match + ");";
        Statement stmt = co.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if(rs.next()){
            arbitreChaise = new Arbitre(
                                        co,
                                        rs.getInt("id_arbitre"),
                                        rs.getString("nom_arbitre"),
                                        rs.getString("prenom_arbitre"),
                                        rs.getString("rank_arbitre"),
                                        rs.getString("nationalite")
                                        );
        }
        rs.close();
        stmt.close();
    }
    
    public void findArbitreFilet() throws SQLException{
        String query = "SELECT * FROM ARBITRE WHERE id_arbitre IN (SELECT id_arbitre FROM ASSIGNEMENT_ARBITRE WHERE categorie_arbitre = 'Filet' AND id_match = " + id_match + ");";
        Statement stmt = co.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if(rs.next()){
            arbitreChaise = new Arbitre(
                                        co,
                                        rs.getInt("id_arbitre"),
                                        rs.getString("nom_arbitre"),
                                        rs.getString("prenom_arbitre"),
                                        rs.getString("rank_arbitre"),
                                        rs.getString("nationalite")
                                        );
        }
        rs.close();
        stmt.close();
    }
    
    public void findArbitresLigne() throws SQLException{
        String query = "SELECT * FROM ARBITRE WHERE id_arbitre IN (SELECT id_arbitre FROM ASSIGNEMENT_ARBITRE WHERE categorie_arbitre = 'Ligne' AND id_match = " + id_match + ");";
        Statement stmt = co.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()){
            arbitresLigne.add(new Arbitre(
                                        co,
                                        rs.getInt("id_arbitre"),
                                        rs.getString("nom_arbitre"),
                                        rs.getString("prenom_arbitre"),
                                        rs.getString("rank_arbitre"),
                                        rs.getString("nationalite")
                                        ));
        }
        rs.close();
        stmt.close();
    }
    
    public void findEquipeRamasseurs() throws SQLException{
        //String query = "SELECT * FROM RAMASSEUR WHERE id_ramasseur IN (SELECT id_ramasseur FROM ASSIGNEMENT_RAMASSEUR WHERE id_match = " + id_match + ");";
        String query = "SELECT * FROM EQUIPE_RAMASSEURS WHERE id_equipe_ramasseurs IN ("
                                                                                    + "SELECT id_equipe_ramasseurs"
                                                                                    + " FROM ASSIGNEMENT_EQUIPE_RAMASSEURS"
                                                                                    + " WHERE id_match = " + id_match + ");";
        Statement stmt = co.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if(rs.next()){
            equipe_ramasseurs = new EquipeRamasseurs(
                                            co,
                                            rs.getInt("id_equipe_ramasseurs"),
                                            rs.getString("nom_equipe")
                                    );
        }
        rs.close();
        stmt.close();
    }
    
    public void findCourt() throws SQLException{
        String query = "SELECT nom_court FROM COURT WHERE id_court IN (SELECT id_court FROM ASSIGNEMENT_COURT WHERE id_match = " + id_match + ");";
        Statement stmt = co.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        if(rs.next())
            this.court = rs.getString("nom_court");
        
        rs.close();
        stmt.close();
    }
    
    @Override
    public String toString() {
        return "Match{" + "id_match=" + id_match + ", date=" + date + ", creneau=" + creneau + ", categorie_match=" 
                + categorie_match + ", tour=" + tour + ", court=" + court + ", arbitreChaise=" + arbitreChaise 
                + ", arbitreFilet=" + arbitreFilet + ", arbitresLigne=" + arbitresLigne + ", ramasseurs=" 
                + equipe_ramasseurs + ", participant1=" + participant1 
                + ", participant2=" + participant2 + '}';
    }
    
    public int getId_match() {
        return id_match;
    }
    
    public String getCreneau(){
        return this.creneau;
    }
    
    public String getTour(){
        return this.tour;
    }
    
    public String getCategorie(){
        return this.categorie_match;
    }
    
    public String getCourt(){
        return this.court;
    }
    
    public String getJoueursToString(){
        String joueurs = "";
        
        for(Joueur joueur : participant1){
            joueurs += joueur.getNom_joueur() + " ";
        }
        joueurs += "\nVS \n";
        for(Joueur joueur : participant2){
            joueurs += joueur.getNom_joueur() + " ";
        }
        return joueurs;
    }
    
    public boolean isMatchSimple(){
        return this.categorie_match.equals("Simple Homme") || this.categorie_match.equals("Simple Femme");
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

    public void addToDataBase(){
        try {
            Statement stmt = co.createStatement();
            String query = "INSERT INTO `MATCH`(date_match,creneau_match,categorie_match,tour_match,id_court) "
                            + "VALUES (" + this.date + "," + this.creneau + "," + this.categorie_match + "," + this.tour + ");";
        } catch (SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
