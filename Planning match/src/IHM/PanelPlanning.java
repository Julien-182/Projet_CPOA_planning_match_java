package IHM;

import bdd.ConnectionMySQL;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import planning.match.match.Match;

/**
 *
 * @author PONTONNIER la best <3
 */
public class PanelPlanning extends JPanel{
    
    private JTable planning;
    private JPanel buttons;
    private JButton ajouterButton, editerButton, supprimerButton;
    
    public PanelPlanning(){
        this.setPreferredSize(new Dimension(100,650));
        this.setLayout(new BorderLayout());
        initComponent();
    }
    
    public void initComponent(){
        //Tableau planning
        String[] columnName = {"Date", "Créneau", "Catégorie", "Tour","Participant","Court"};
        List<Match> data = getMatchInfos();
        PlanningModel planningModel = new PlanningModel(data,columnName);
        planning = new JTable(planningModel);   
        
        //Boutons d'actions
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(11,1,0,25));
        buttons.setSize(300, this.getHeight());
        ajouterButton = new JButton("Ajouter");
        editerButton = new JButton("Editer");
        supprimerButton = new JButton("Supprimer");
        
        this.add(new JScrollPane(planning), BorderLayout.CENTER);
        buttons.add(new Label());buttons.add(new Label());buttons.add(new Label());
        buttons.add(ajouterButton);
        buttons.add(new Label());
        buttons.add(editerButton);
        buttons.add(new Label());
        buttons.add(supprimerButton);
        this.add(buttons, BorderLayout.EAST);
    }
    
    /**
     * 
     * @return Les données de la BDD des matchs sous formes d'un tableau d'Object
     * Chaque ligne du tableau correspond à [Date,Créneau,Categorie,Tour,Participants,Court]
     */
    private List<Match> getMatchInfos(){
        try {
            List<Match> liste_matchs = new ArrayList<>();
            ConnectionMySQL coMySQL = new ConnectionMySQL();
            Connection co = coMySQL.getConnection();
            
            Statement stmt = co.createStatement();
            String query = "SELECT * FROM `MATCH`;";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                liste_matchs.add(new Match(
                        co,
                        rs.getInt("id_match"),
                        rs.getDate("date_match"),
                        rs.getString("creneau_match"),
                        rs.getString("categorie_match"),
                        rs.getString("tour_match"),
                        rs.getInt("id_court")
                ));
            }
            
            return liste_matchs;
        } catch (SQLException ex) {
            Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private class AjouterActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }    
    }
    
    private class EditerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } 
    }
    
    private class SupprimerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
