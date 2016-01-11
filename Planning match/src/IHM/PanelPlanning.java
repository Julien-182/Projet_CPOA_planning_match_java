package IHM;

import bdd.ConnectionMySQL;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import planning.match.match.Court;
import planning.match.match.Creneau;
import planning.match.match.Date_Match;
import planning.match.match.Match;
import planning.match.match.Tour;
import planning.match.participants.*;

/**
 *
 * @author PONTONNIER la best <3
 */
public class PanelPlanning extends JPanel{
    
    private JTable planning;
    PlanningModel planningModel;
    private JPanel buttons;
    private JButton ajouterButton, editerButton, supprimerButton;
    
    private List<Match> data;
    private List<Joueur> joueur_collection;
    private List<Equipe> equipe_collection;
    private List<Arbitre> arbitre_collection;
    private List<EquipeRamasseurs> ramasseurs_collection;
    
    private Connection co;
    
    public PanelPlanning(Connection p_co){
        this.setPreferredSize(new Dimension(1000,650));
        this.setLayout(new BorderLayout());
        this.co = p_co;     
        initComponent();
        initCollections();
    }
    
    public void initComponent(){
        //Tableau planning
        String[] columnName = {"Date", "Créneau", "Catégorie", "Tour","Participant","Court"};
        data = getMatchInfos();
        planningModel = new PlanningModel(data,columnName);
        planning = new JTable(planningModel);   
        
        //Boutons d'actions
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(11,1,0,25));
        buttons.setSize(300, this.getHeight());
        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new AjouterActionListener());
        editerButton = new JButton("Editer");
        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new SupprimerActionListener());
        
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
        equipe_collection = new ArrayList<>();
        arbitre_collection = new ArrayList<>();
        ramasseurs_collection = new ArrayList<>();
        try { 
            Statement stmt = co.createStatement();
            
            // JOUEUR INIT
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
            
            // EQUIPE INIT
            query = "SELECT * FROM EQUIPE";
            rs = stmt.executeQuery(query);
            while(rs.next()){
                equipe_collection.add(new Equipe(
                                        co,
                                        rs.getInt("id_equipe"),
                                        rs.getInt("id_joueur1"),
                                        rs.getInt("id_joueur2")
                                    ));
            }
            rs.close();
            
            //ARBITRE INIT
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
            
            //EQUIPE RAMASSEURS INIT
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
            String date_string;
            Date date_match = null;
            String creneau, categorie, tour, court;      
            List<Joueur> joueurs_match = new ArrayList<>();
            List<Equipe> equipes_match = new ArrayList<>();
            
            date_string = choixDate();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date date = df.parse(date_string);
                date_match = new java.sql.Date(date.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            creneau = choixCreneau(date_match);
            categorie = choixCategorie();
            tour = choixTour();
            court = choixCourt();     
            if(categorie.equals("Simple Homme") ||categorie.equals("Simple Femme")){
                joueurs_match = choixJoueur(date_match, creneau, tour); //gérer Date !!!
            }
            else{
                equipes_match = choixEquipe(date_match, creneau, tour);
            }
            System.out.println(date_match);
            System.out.println(creneau);
            System.out.println(categorie);
            System.out.println(tour);
            System.out.println(court);
        }    
        
        public String choixDate()     {
            String date_match = null;
            
            //Choix de la date
            Date_Match date_match2 = (Date_Match) JOptionPane.showInputDialog(null, "Choisissez une date", "Ajout d'un match", JOptionPane.PLAIN_MESSAGE,null, Date_Match.values(), Date_Match.jour1);
            date_match = date_match2.toString();
            
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
            creneau = JOptionPane.showInputDialog(null,"Choix du créneau","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, Creneau.values(), Creneau.am_8).toString();
            //Vérification de la date et du créneau
           
            try {
                Statement stmt = co.createStatement();
                String query  = "SELECT COUNT( id_match ) AS nb_match FROM  `MATCH`" +
                                                                " WHERE date_match =  " + date_match  +
                                                                " AND creneau_match = " + creneau + ";";                
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    if(rs.getInt("nb_match") == 4) 
                        choixCreneau(date_match);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }              
            return creneau;
        }    
        
        public String choixCategorie(){
            String categorie = null;
            String[] categories = {"Simple Homme", "Simple Femme", "Double Homme", "Double Femme"};
            
            categorie = (String) JOptionPane.showInputDialog(null,"Choix de la categorie","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, categories, categories[0]);
            return categorie;
        }
        
        public String choixTour(){
            String tour = null;
            tour = JOptionPane.showInputDialog(null,"Choix du créneau","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, Tour.values(), Tour.Qualification).toString();
            return tour;
        }
        
        public String choixCourt(){
            String court = null;
            String[] liste_courts = {"Grand Court de Gerlan", "Court de Saint-Andre","Moyen Court", "Golden Court"};
            court = (String)JOptionPane.showInputDialog(null,"Choix du court","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, liste_courts, liste_courts[0]);
            return court;
        }
        
        public List<Joueur> choixJoueur(Date date_match, String creneau, String tour){
            List<Joueur> joueurs_assignes = new ArrayList<>();
            List<Joueur> joueurs_dispo = new ArrayList<>();
            
            for(Joueur j : joueur_collection){
                    //vérifier disponibilité
                    if(j.estDisponible(date_match, creneau) && j.estQualifie(tour)){
                        joueurs_dispo.add(j);
                    }
            }
            Joueur j1 = (Joueur) JOptionPane.showInputDialog(null,"Choix du Joueur 1","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, joueurs_dispo.toArray(), null);
            Joueur j2 = (Joueur) JOptionPane.showInputDialog(null,"Choix du Joueur 2","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, joueurs_dispo.toArray(), null);
            joueurs_assignes.add(j1);
            joueurs_assignes.add(j2);
            return joueurs_assignes;
        }
        
        public List<Equipe> choixEquipe(Date date , String creneau, String tour){
            List<Equipe> equipes_dispo = new ArrayList<>();
            List<Equipe> equipes_assignes = new ArrayList<>();
            
            for(Equipe equipe : equipe_collection){
                    if(equipe.estDisponible(date, creneau) || equipe.estQualifie(tour)){
                        equipes_dispo.add(equipe);
                    }
            }       
            
            Equipe eq1 = (Equipe) JOptionPane.showInputDialog(null,"Choix du Equipe 1","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, equipes_dispo.toArray(), equipes_dispo.get(0));
            Equipe eq2 = (Equipe) JOptionPane.showInputDialog(null,"Choix du Equipe 2","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, equipes_dispo.toArray(), equipes_dispo.get(0));
            equipes_assignes.add(eq1);
            equipes_assignes.add(eq2);
            return equipes_assignes;
        }  
        
        public void assignerArbitres(int id_match){
            
        }
        
        public void assignerRamasseurs(int id_match){
            
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
            try {
                Match match = planningModel.getRowAt(planning.getSelectedRow());
                int id = match.getId_match();
                System.out.println(id);
                
                Statement stmt = co.createStatement();
                //Suppression assignement arbitres
                String query = "DELETE FROM ASSIGNEMENT_ARBITRE WHERE id_match =" + id;
                stmt.execute(query);
                //Suppression assignement court
                query = "DELETE FROM ASSIGNEMENT_COURT WHERE id_match = " + id;
                stmt.execute(query);
                //Suppression equipe ramasseurs
                query = "DELETE FROM ASSIGNEMENT_EQUIPE_RAMASSEUR WHERE id_match = " + id;
                stmt.execute(query);
                
                //Suppression joueur ou equipe
                if(match.isMatchSimple()){
                    query = "DELETE FROM ASSIGNEMENT_EQUIPE WHERE id_match = " + id;
                }
                else{
                    query = "DELETE FROM ASSIGNEMENT_JOUEUR WHERE id_match = " + id;
                }
                stmt.execute(query);
                
                query = "DELETE FROM MATCH WHERE id_match = " + id;
                stmt.execute(query);
                
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }
}
