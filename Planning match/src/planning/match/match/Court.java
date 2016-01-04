package planning.match.match;

/**
 *
 * @author Bobbybel
 */
public class Court{
    private int id_court;
    private int nbPlaces;
    private String nomCourt;
    

    public Court(int id_court, int nbPlaces, String nomCourt){
        this.id_court = id_court;
        this.nbPlaces = nbPlaces;
        this.nomCourt = nomCourt;
    }

    public int getId_court() {
        return id_court;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public void setId_court(int id_court) {
        this.id_court = id_court;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }
}
