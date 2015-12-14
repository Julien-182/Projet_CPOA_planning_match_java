package planning.match.match;

public class Arbitre {
    private int id_arbitre;
    private String nom;
    private String prenom;
    private String categorie_arbitre;
    private String rank_arbitre;
    private String nationalite;
    
    public Arbitre(int id_arbitre, String nom, String prenom, String categorie_arbitre, String rank_arbitre, String nationalite){
        this.id_arbitre = id_arbitre;
        this.nom = nom;
        this.prenom = prenom;
        this.categorie_arbitre = categorie_arbitre;
        this.rank_arbitre = rank_arbitre;
        this.nationalite = nationalite;
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

    public String getCategorie_arbitre() {
        return categorie_arbitre;
    }

    public String getRank_arbitre() {
        return rank_arbitre;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setId_arbitre(int id_arbitre) {
        this.id_arbitre = id_arbitre;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCategorie_arbitre(String categorie_arbitre) {
        this.categorie_arbitre = categorie_arbitre;
    }

    public void setRank_arbitre(String rank_arbitre) {
        this.rank_arbitre = rank_arbitre;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    @Override
    public String toString() {
        return "Arbitre {" + "\nid_arbitre = " + id_arbitre + "\nnom = " + nom + "\nprenom = " + prenom + 
                "\ncategorie_arbitre = " + categorie_arbitre + "\nrank_arbitre = " + rank_arbitre 
                + "\nnationalite = " + nationalite + " }";
    }
     
    
    
}
