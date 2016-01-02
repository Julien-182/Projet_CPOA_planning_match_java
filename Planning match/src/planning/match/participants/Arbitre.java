package planning.match.participants;

public class Arbitre {
    
    private int id_arbitre;
    private String nom;
    private String prenom;
    protected String rank_arbitre;
    private String nationalite;
    private int nbSimple;
    private int nbDouble;
    
    public Arbitre(int id_arbitre, String nom, String prenom, String rank_arbitre, String nationalite, 
                    int nbSimple, int nbDouble){
        this.id_arbitre = id_arbitre;
        this.nom = nom;
        this.prenom = prenom;
        this.rank_arbitre = rank_arbitre;
        this.nationalite = nationalite;
        this.nbSimple = nbSimple;
        this.nbDouble = nbDouble;
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

    public void setId_arbitre(int id_arbitre) {
        this.id_arbitre = id_arbitre;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setRank_arbitre(String rank_arbitre) {
        this.rank_arbitre = rank_arbitre;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
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
        return this.getNbSimple() < 2;
    }
    
    /**
     * 
     * @return vrai si l'arbitre peut arbitrer un match double
     */
    public boolean canArbitreDouble(){
        return this.getNbDouble() < 2;
    }
}
