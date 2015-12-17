package planning.match.match;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Match {
    private int id_match;
    private String date;
    
    public Match(int id_match, String date){
        this.id_match = id_match;
        SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy" );
        Date currentTime_1 = new Date();
        String dateString = formatter.format(currentTime_1);
        this.date = dateString;
    }

    @Override
    public String toString() {
        return "Match {" + "\nid_match = " + id_match + "\ndate = " + date + " }";
    }
    
    public int getId_match() {
        return id_match;
    }

    public String getDate() {
        return date;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
