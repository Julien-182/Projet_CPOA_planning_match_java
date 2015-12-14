package planning.match.match;

public class Equipe {
    private int id_equipe;
    private String tour;
    
    public Equipe(int id_equipe, String tour){
        this.id_equipe = id_equipe;
        this.tour = tour;
    }

    public int getId_equipe() {
        return id_equipe;
    }

    public String getTour() {
        return tour;
    }

    public void setId_equipe(int id_equipe) {
        this.id_equipe = id_equipe;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    @Override
    public String toString() {
        return "Equipe {" + "\nid_equipe = " + id_equipe + "\ntour = " + tour + " }";
    }
    
}
