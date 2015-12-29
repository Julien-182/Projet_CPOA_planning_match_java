package planning.match.match;

/**
 *
 * @author Bobbybel
 */
public class Court{
    private int id_court;
    private int nbPlaces;
    private boolean occupe;
    

    public Court(int id_court, int nbPlaces, boolean occupe){
        this.id_court = id_court;
        this.nbPlaces = nbPlaces;
        this.occupe = false;
    }

    public int getId_court() {
        return id_court;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setId_court(int id_court) {
        this.id_court = id_court;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }
}
