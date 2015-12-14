package planning.match.match;

public class Joueur {
    private int id_joueur;
    private String nom;
    private String prenom;
    private String tour;
    
    public Joueur(int id_joueur, String nom, String prenom, String tour){
        this.id_joueur = id_joueur;
        this.nom = nom;
        this.prenom = prenom;
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "Joueur {" + "\nid_joueur = " + id_joueur + "\nnom = " + nom + "\nprenom = " + prenom + "\ntour = " + tour + " }";
    }

    public int getId_joueur() {
        return id_joueur;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTour() {
        return tour;
    }

    public void setId_joueur(int id_joueur) {
        this.id_joueur = id_joueur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }
}
