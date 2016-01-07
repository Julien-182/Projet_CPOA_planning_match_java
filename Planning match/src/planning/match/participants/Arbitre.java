package planning.match.participants;

import bdd.ConfigConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Arbitre {
    
    private Connection co;
    private int id_arbitre;
    private String nom;
    private String prenom;
    protected String rank_arbitre;
    private String nationalite;
    private int nbSimple;
    private int nbDouble;
    
    public Arbitre(Connection co,int id_arbitre, String nom, String prenom, String rank_arbitre, String nationalite){
        this.co = co;
        this.id_arbitre = id_arbitre;
        this.nom = nom;
        this.prenom = prenom;
        this.rank_arbitre = rank_arbitre;
        this.nationalite = nationalite;
    }

    @Override
    public String toString() {
        return "Arbitre {" + "\nid_arbitre = " + id_arbitre + "\nnom = " + nom + "\nprenom = " + prenom + 
                "\nrank_arbitre = " + rank_arbitre + "\nnationalite = " + nationalite + " }";
    }

    public int getId_arbitre() {
        return id_arbitre;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getRank_arbitre() {
        return rank_arbitre;
    }

    public String getNationalite() {
        return nationalite;
    }

    public int getNbSimple() {
        return nbSimple;
    }

    public int getNbDouble() {
        return nbDouble;
    }

    public void setNbSimple(int nbSimple) {
        this.nbSimple = nbSimple;
    }
    
    public int arbitrerMatchSimple(int nbSimple){
        return nbSimple++;
    }

    public void setNbDouble(int nbDouble) {
        this.nbDouble = nbDouble;
    }   
    
    public int arbitrerMatchDouble(int nbDouble){
        return nbDouble++;
    }
    
    /**
     * 
     * @return vrai si l'arbitre à le rang nécéssaire pour être arbitre de chaise
     */
    public boolean isArbitreChaise(){
        return "ITT1".equals(this.getRank_arbitre());
    }
    
    /**
     * 
     * @return vrai si l'arbitre à le rang nécéssaire pour être arbitre de chaise
     */
    public boolean isArbitreFiletLigne(){
        return "JAT2".equals(this.getRank_arbitre());
    }
        
    /**
     * 
     * @return vrai si l'arbitre peut arbitrer un match simple
     */
    public boolean canArbitrerSimple(){
        if(this.isArbitreChaise()){
            return this.getNbSimple() < 2;
        }else{
            return true;
        }
        
    }
    
    /**
     * 
     * @return vrai si l'arbitre peut arbitrer un match double
     */
    public boolean canArbitreDouble(){
        if(this.isArbitreChaise()){
            return this.getNbDouble() < 2;
        }else{
            return true;
        }
    }
    
    //A FAIRE
    public boolean estDisponible(Date date, String creneau) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException{
        /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'arbitre est assigné au match
                SI OUI --> return false (pas dispo)
                SI NON --> return true (dispo)
        */
        Boolean dispo;
        
        Statement stmt = this.co.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT id_ramasseur "
                                         + "FROM ASSIGNMENT_RAMASSEUR "
                                         + "WHERE id_match IN("
                                                               + "SELECT id_match"
                                                               + " FROM MATCHS "
                                                               + "WHERE date_match = " + date + "AND creneau_match = " + creneau
                                        + ")");
        if(rset.next()) dispo =  false;
        else dispo = true;
        
        stmt.close();
        rset.close();
        return dispo;
    }
}
