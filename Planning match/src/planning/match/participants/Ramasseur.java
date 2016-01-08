package planning.match.participants;

import java.util.Date;
import java.sql.*;

public class Ramasseur {
    
    private int id_ramasseur;
    private String nom;
    private String prenom;
    private Connection co;
    
    public Ramasseur(Connection co,int id_ramasseur,String nom,String prenom){
        this.co = co;
        this.id_ramasseur = id_ramasseur;
        this.nom = nom;
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Ramasseur {" + "\nid_ramasseur = " + id_ramasseur + "\nnom = " + nom + "\nprenom=" + prenom + " }";
    }
            
    public int getId_ramasseur() {
        return id_ramasseur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }  
}
