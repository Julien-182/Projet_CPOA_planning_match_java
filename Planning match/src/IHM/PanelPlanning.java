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
import planning.match.match.Court;
import planning.match.match.Creneau;
import planning.match.match.Date_Match;
import planning.match.match.Match;
import planning.match.participants.*;

/**
 *
 * @author PONTONNIER la best <3
 */
public class PanelPlanning extends JPanel{
    
    private JTable planning;
    private JPanel buttons;
    private JButton ajouterButton, editerButton, supprimerButton;
    
    private List<Match> data;
    private List<Joueur> joueur_collection;
    private List<Arbitre> arbitre_collection;
    private List<EquipeRamasseurs> ramasseurs_collection;
    
    private Connection co;
    
    public PanelPlanning(Connection p_co){
        this.setPreferredSize(new Dimension(1000,650));
        this.setLayout(new BorderLayout());
        this.co = p_co;
        initComponent();
    }
    
    public void initComponent(){
        //Tableau planning
        String[] columnName = {"Date", "Créneau", "Catégorie", "Tour","Participant","Court"};
        data = getMatchInfos();
        PlanningModel planningModel = new PlanningModel(data,columnName);
        planning = new JTable(planningModel);   
        
        //Boutons d'actions
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(11,1,0,25));
        buttons.setSize(300, this.getHeight());
        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new AjouterActionListener());
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
    
    public void initCollections(){

        joueur_collection = new ArrayList<>();
        arbitre_collection = new ArrayList<>();
        ramasseurs_collection = new ArrayList<>();
        try { 
            Statement stmt = co.createStatement();
            
            String query = "SELECT * FROM JOUEUR;";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                joueur_collection.add(new Joueur(
                                        co,
                                        rs.getInt("id_joueur"),
                                        rs.getString("nom_joueur"),
                                        rs.getString("prenom_joueur"),
                                        rs.getString("nationalite"),
                                        rs.getString("sexe")
                                    ));
            }
            rs.close();
            
            query = "SELECT * FROM ARBITRE;";
            rs = stmt.executeQuery(query);
            
            while(rs.next()){
                arbitre_collection.add(new Arbitre(
                                        co,
                                        rs.getInt("id_arbitre"),
                                        rs.getString("nom_arbitre"),
                                        rs.getString("prenom_arbitre"),
                                        rs.getString("rank_arbitre"),
                                        rs.getString("nationalite")
                                    ));
            }
            rs.close();
            
            query = "SELECT * FROM EQUIPE_RAMASSEURS;";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                ramasseurs_collection.add(new EquipeRamasseurs(
                                        co,
                                        rs.getInt("id_equipe_ramasseurs"),
                                        rs.getString("nom_equipe")
                                    ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @return Les données de la BDD des matchs sous formes d'un tableau d'Object
     * Chaque ligne du tableau correspond à [Date,Créneau,Categorie,Tour,Participants,Court]
     */
    private List<Match> getMatchInfos(){
        List<Match> liste_matchs = new ArrayList<>();
        try {
            if(co != null){
                System.out.println("...");
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
                            rs.getString("tour_match")
                    ));
                }

                rs.close();
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liste_matchs;
    }
    
    private class AjouterActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            /*
                Afficher fenêtre dialog --> choix date parmi les dates disponibles (où il n'y a pas les 6 créneau occupés)
                Afficher fenêtre dialog --> choix créneau parmi ceux disponible dans la journée
                Afficher fenêtre dialog --> choisir categorie du match 
                Afficher fenêtre dialog --> choisir tour du match
                Afficher fenêtre dialog --> choisir les deux joueurs parmi ceux qui sont disponibles et ceux qualifiés pour le tour du match
                Afficher fenêtre dialog --> choisir le court du match
            */
            List<Date> date_dispo = new ArrayList<>();
            Date date_match;
            String creneau, categorie, tour;       
            
            date_match = Date.valueOf(choixDate());
            System.out.println("COUCOU");
            creneau = choixCreneau(date_match);
            
        }    
        
        public String choixDate(){
            String date_match = null;
            
            //Choix de la date
            date_match = (String) JOptionPane.showInputDialog(null, "Choisissez une date", "Ajout d'un match", JOptionPane.PLAIN_MESSAGE,null, Date_Match.values(), Date_Match.jour1);
           
            try {
                Statement stmt = co.createStatement();
                String query  = "SELECT COUNT( id_match ) AS nb_match FROM  `MATCH`" +
                                                                " WHERE date_match = STR_TO_DATE( " + date_match + ",  '%Y-%m-%d' )";               
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                
                //24 = 4 courts * 6 creneaux = 24 matchs/jour possibles
                if(rs.getInt("nb_match") == Creneau.values().length * 4) choixDate();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }     
            
            return date_match;
        }    
        public String choixCreneau(Date date_match){
            String creneau = null;
            //Choix du créneau
            creneau = (String)JOptionPane.showInputDialog(null,"Choix du créneau","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, Creneau.values(), Creneau.am_8);
            //Vérification de la date et du créneau
           
            try {
                Statement stmt = co.createStatement();
                String query  = "SELECT COUNT( id_match ) AS nb_match FROM  `MATCH`" +
                                                                " WHERE date_match = STR_TO_DATE( " + date_match + ",  '%Y-%m-%d' )" +
                                                                " AND creneau_match = " + creneau + ";";                
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                
                if(rs.getInt("nb_match") == 4) choixCreneau(date_match);
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }     
           
            return creneau;
        }
        public String choixCategorie(){
            String categorie = null;
            
            return null;
        }
        public String choixTour(){
            String tour = null;
            
            return tour;
        }
        public String choixCourt(){
            String court = null;
            
            return court;
        }
        public Joueur choixJoueur(){
            Joueur joueur = null;
            
            return joueur;
        }
        public Equipe choixEquipe(){
            Equipe equipe = null;
            return equipe;
        }
        
    }
    
    private class EditerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        } 
    }
    
    private class SupprimerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           
        }       
    }
}
