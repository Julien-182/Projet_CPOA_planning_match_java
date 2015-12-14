package planning.match.participants;
import java.util.Date;

public class Match {
    private int id_match;
    private Date date;
    
    public Match(int id_match, Date date){
        this.id_match = id_match;
        this.date = new Date(date);
    }

    @Override
    public String toString() {
        return "Match {" + "\nid_match = " + id_match + "\ndate = " + date + " }";
    }
    
    public int getId_match() {
        return id_match;
    }

    public Date getDate() {
        return date;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
