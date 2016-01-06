package IHM;

import bdd.ConnectionMySQL;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author PONTONNIER
 */
public class PanelPlanning extends JPanel{
    
    public PanelPlanning(){
        this.setPreferredSize(new Dimension(100,700));
    }
    
    public void initComponent(){
        String[] titres = {"Date", "Créneau", "Catégorie", "Tour"};
        Object[][] data = {};
        
        JTable table = new JTable(data, titres);
    }
    
    /**
     * 
     * @return Les données de la BDD des matchs sous formes d'un tableau d'Object
     * Chaque ligne du tableau correspond à [Date,Créneau,Categorie,Tour,Participants,Court]
     */
    private Object[][] getMatchInfos(){
        Object[][] data = null;
        int i = 0;
        
        ConnectionMySQL coSQL = new ConnectionMySQL();
        Connection co = coSQL.getConnection();
        
        Statement stmt;
        try {
            stmt = co.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `MATCH`");
            
            while(rs.next()){
                String date = rs.getDate("date_match").toString();
                String creneau = rs.getString("8am");
                String categorie = rs.getString("categorie_match");
                String tour = rs.getString("tour_match");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
}
