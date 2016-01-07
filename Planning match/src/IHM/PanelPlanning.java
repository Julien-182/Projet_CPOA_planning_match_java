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
        String[][] datas = {
            {"8 Janvier", "8am", "Simple Homme", "Demi-finale", "dedede","dede"},
            {"9 Janvier", "11am", "Double Femme", "Qualifications","dedede","deded"}
        };
        planning = new JTable(datas,columnName);   
        
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
    private void getMatchInfos(){}
    
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
