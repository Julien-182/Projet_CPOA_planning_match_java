package planning.match.match;

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

    public void setId_ramasseur(int id_ramasseur) {
        this.id_ramasseur = id_ramasseur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    
}
