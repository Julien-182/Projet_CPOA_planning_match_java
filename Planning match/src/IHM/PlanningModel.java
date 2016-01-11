package IHM;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import planning.match.match.Match;

public class PlanningModel extends AbstractTableModel{

    private List<Match> data;
    private String[] titres;
    
    public PlanningModel(List<Match> data, String[] titres){
        this.data = data;
        this.titres = titres;
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return titres.length;
    }
    
    @Override
    public String getColumnName(int index){
        return titres[index];
    }
    
    public Match getRowAt(int index){
        return data.get(index);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Match match = data.get(rowIndex);
        switch(columnIndex){
            case(0): return match.getDateString();
            case(1): return match.getCreneau();
            case(2): return match.getCategorie();
            case(3): return match.getTour();
            case(4): return match.getJoueursToString();
            case(5): return match.getCourt();
            default : return "";
        }
    }   
}
