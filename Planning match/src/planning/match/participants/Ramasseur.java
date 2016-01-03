package planning.match.participants;

import java.util.Date;

public class Ramasseur {
    
    private int id_ramasseur;
    private String nom;
    private String prenom;
    
    public Ramasseur(int id_ramasseur,String nom,String prenom){
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
    
    //A FAIRE
    public boolean estDisponible(Date date, String creneau){
         /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'arbitre est assigné au match
                SI OUI --> return false (pas dispo)
                SI NON --> return true (dispo)
        */
        
        return true;
    }
  
}
