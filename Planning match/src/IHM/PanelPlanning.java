package IHM;

import bdd.ConnectionMySQL;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
    private PlanningModel planningModel;
    private JScrollPane jsp;
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
        planning.setRowHeight(30);
        planning.setGridColor(Color.BLUE);
        planning.getColumn("Date").setPreferredWidth(25);
        planning.getColumn("Créneau").setPreferredWidth(5);
        planning.getColumn("Participant").setPreferredWidth(150);
        planning.setFont(new Font("Calibri Light", Font.PLAIN, 12));
        //Boutons d'actions
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(11,1,0,25));
        buttons.setSize(300, this.getHeight());
        ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new AjouterActionListener());
        editerButton = new JButton("Editer");
        editerButton.addActionListener(new EditerActionListener());
        supprimerButton = new JButton("Supprimer");
        supprimerButton.addActionListener(new SupprimerActionListener());
        
        jsp = new JScrollPane(planning);
        this.add(jsp, BorderLayout.CENTER);
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
            String date_string; Date date_match = null;
            String creneau = null, categorie = null , tour = null, court = null;      
            
            List<Joueur> joueurs_match = new ArrayList<>();
            List<Equipe> equipes_match = new ArrayList<>();
            
            date_string = choixDate();   
            //Les conditions permettent d'arrêter la procédure lorsque l'on annule le choix où que l'on rentre une valeur vide/null
            if(date_string != null){
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    java.util.Date date = df.parse(date_string);
                    date_match = new java.sql.Date(date.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
                }
                creneau = choixCreneau(date_match);
                if(creneau != null){
                    categorie = choixCategorie();
                    if(categorie != null){
                        tour = choixTour();
                        if(tour != null){
                            court = choixCourt(date_match, creneau); 
                            if(court != null){
                                if(categorie.equals("Simple Homme") ||categorie.equals("Simple Femme")){
                                    joueurs_match = choixJoueur(date_match, creneau, tour,categorie);
                                }
                                else{
                                    equipes_match = choixEquipe(date_match, creneau, tour);
                                }
                                //Vérification que la séléction des joueurs/equipes a bien eu lieu
                                if( (categorie.equals("Simple Homme") ||categorie.equals("Simple Femme") && joueurs_match.size() != 0) || 
                                        (categorie.equals("Double Homme") ||categorie.equals("Double Femme") && equipes_match.size() != 0)){
                                    
                                    int id_match = getNewIdMatch();
                                    //On insère dans la table MATCH le nouveau match
                                    insererMatch(id_match, date_match, creneau, categorie, tour);

                                    //On assigne le court au match dans les tables SQL
                                    assignerCourt(court, id_match);

                                    //On assigne les joueurs/équipes au match dans les tables SQL
                                    if(categorie.equals("Simple Homme") || categorie.equals("Simple Femme")){
                                        for(Joueur j : joueurs_match){
                                            j.assignerAMatch(id_match);
                                        }
                                    }
                                    else{
                                        for(Equipe eq : equipes_match){
                                            eq.assignerAMatch(id_match);
                                        }
                                    }
                                    
                                    assignerArbitres(id_match, date_match, creneau);
                                    
                                    assignerRamasseurs(id_match,date_match,creneau);
                                    
                                    Match new_match = new Match(co, id_match, date_match, creneau, categorie, tour);
                                    data.add(new_match);
                                    planningModel.fireTableDataChanged();
                                    JOptionPane.showConfirmDialog(null, "Match ajouté !", "Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }     
        }    
        
        public String choixDate()     {
            String date_match = null;
            
            //Choix de la date
            Date_Match date_match2 = (Date_Match) JOptionPane.showInputDialog(null, "Choisissez une date", "Ajout d'un match", JOptionPane.PLAIN_MESSAGE,null, Date_Match.values(), null);
            if(date_match2 == null) return null;
            
            date_match = date_match2.toString();
            
            try {
                Statement stmt = co.createStatement();
                String query  = "SELECT COUNT( id_match ) FROM  `MATCH`" +
                                                                " WHERE date_match = STR_TO_DATE( " + date_match + ",  '%Y-%m-%d' )";               
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                //24 = 4 courts * 6 creneaux = 24 matchs/jour possibles
                if(rs.getInt(1) == Creneau.values().length * 4) choixDate();
                rs.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
            return date_match;
        }    
        
        public String choixCreneau(Date date_match){
            String creneau = null;
            //Choix du créneau
            creneau = JOptionPane.showInputDialog(null,"Choix du créneau","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, Creneau.values(), null).toString();
            
            if(creneau == null) return null;
            //Vérification de la date et du créneau
           String date_string = date_match.toString();
            try {
                Statement stmt = co.createStatement();
                String query  = "SELECT COUNT(id_match) FROM  `MATCH`" +
                                                                " WHERE date_match =  STR_TO_DATE( " + date_string + ",  '%Y-%m-%d' )" + 
                                                                " AND creneau_match = '" + creneau + "';";                
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    if(rs.getInt(1) == 4) 
                        choixCreneau(date_match);
                }
                rs.close();
                stmt.close();
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
            tour = JOptionPane.showInputDialog(null,"Choix du créneau","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, Tour.values(), null).toString();
            return tour;
        }
        
        public String choixCourt(Date date, String creneau){
            String court = null;
            String[] liste_courts = {"Grand Court de Gerlan", "Court de Saint-Andre","Moyen Court", "Golden Court"};
            court = (String)JOptionPane.showInputDialog(null,"Choix du court","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, liste_courts, null);
            if(court != null){
                try {
                    Statement stmt = co.createStatement();
                    String query = "SELECT id_match FROM `MATCH` WHERE date_match = STR_TO_DATE('" + date.toString() +"', '%Y-%m-%d')\n" +
"									AND creneau_match = '" + creneau +"' \n" +
"									AND id_match IN (SELECT id_match \n" +
                                    "                                                     FROM ASSIGNEMENT_COURT \n" +
                                    "                                                     WHERE id_court IN (SELECT id_court FROM COURT\n" +
                                    "                                                                        WHERE nom_court = '" + court + "'));";
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) choixCourt(date,creneau);
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return court;
        }
        
        public List<Joueur> choixJoueur(Date date_match, String creneau, String tour, String categorie){
            List<Joueur> joueurs_assignes = null;
            List<Joueur> joueurs_dispo = new ArrayList<>();
      
            
            for(Joueur j : joueur_collection){
                    //vérifier disponibilité
                    if(j.estDisponible(date_match, creneau)){
                        //System.out.println(j.getNom_joueur() + " dispo");
                        if(j.estQualifie(tour)){
                            //System.out.println(j.getNom_joueur() + " est qualifié");
                            if(categorie.equals("Simple Homme") || categorie.equals("Double Homme")){
                                if(j.isHomme())
                                    joueurs_dispo.add(j);
                            }
                            else{
                                if(!j.isHomme())
                                    joueurs_dispo.add(j);
                            }
                        }
                    }
            }
            
            Joueur j1 = (Joueur) JOptionPane.showInputDialog(null,"Choix du Joueur 1","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, joueurs_dispo.toArray(), null);
            if(j1 != null){
                joueurs_assignes =  new ArrayList<>();
                joueurs_dispo.remove(j1);
                Joueur j2 = (Joueur) JOptionPane.showInputDialog(null,"Choix du Joueur 2","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, joueurs_dispo.toArray(), null);
                if(j2 != null){
                    joueurs_assignes.add(j1);
                    joueurs_assignes.add(j2);
                }
            }
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
            equipes_dispo.remove(eq1);
            Equipe eq2 = (Equipe) JOptionPane.showInputDialog(null,"Choix du Equipe 2","Ajout d'un match",JOptionPane.PLAIN_MESSAGE,null, equipes_dispo.toArray(), equipes_dispo.get(0));
            equipes_assignes.add(eq1);
            equipes_assignes.add(eq2);
            return equipes_assignes;
        }  
        
        public int getNewIdMatch(){
            int new_id = 0;
            try {
                Statement stmt = co.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(id_match) FROM `MATCH`");
                rs.next();
                new_id = rs.getInt(1) + 1;
                
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new_id;
        }
        
        public void insererMatch(int id_match, Date date, String creneau, String categorie, String tour){
             //Inserer dans la table match
            try {
                Statement stmt = co.createStatement();
                String query = "INSERT INTO `MATCH` VALUES (" + id_match + ", STR_TO_DATE('" + date.toString() + "', '%Y-%m-%d') ,'" + creneau + "','" + categorie + "','" + tour + "');";
                stmt.executeUpdate(query);
                
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void assignerCourt(String court, int id_match){
            try {
                Statement stmt = co.createStatement();
                String query = "SELECT id_court FROM COURT WHERE nom_court = '" + court + "';";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                int id_court = rs.getInt(1);
                rs.close();
                
                query = "INSERT INTO ASSIGNEMENT_COURT VALUES (" + id_match + "," + id_court + ");";
                stmt.execute(query);
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void assignerArbitres(int id_match, Date date, String creneau){
            List<Arbitre> arbitres_dispo = new ArrayList<>();
            boolean arbitre_choisi = false; int i = 0;
            
            for(Arbitre a : arbitre_collection){
                if(a.estDisponible(date, creneau)){
                    arbitres_dispo.add(a);
                }
            }
            if(arbitres_dispo.size() < 10) System.out.println("Pas assez d'arbitres dispo pour le match " + id_match);
            
            //Assignement arbitre chaise    1 seul
            while(!arbitre_choisi){
                Arbitre ac = arbitres_dispo.get(i);
                if(ac.isArbitreChaise() && ac.canArbitrerChaise(id_match)){
                    ac.assignerAMatch(id_match, "Chaise");
                    arbitres_dispo.remove(ac);
                    arbitre_choisi = true;
                }
                i++;
            }
            arbitre_choisi = false;
            i = 0;
            
            //Assignement arbitre filet
            while(!arbitre_choisi){
                Arbitre af = arbitres_dispo.get(i);
                if(af.isArbitreFiletLigne()){
                    af.assignerAMatch(id_match, "Filet");
                    arbitres_dispo.remove(af);
                    arbitre_choisi = true;
                }
                i++;
            }
            i = 0;
            arbitre_choisi = false;
            
            //Assignement arbitres lignes
            for(i = 0 ; i < 8 ; i++){
                Arbitre al = arbitres_dispo.get(i);
                if(al.isArbitreFiletLigne()){
                    al.assignerAMatch(id_match, "Ligne");
                }
            }
        }
        
        public void assignerRamasseurs(int id_match,Date date, String creneau){
            List<EquipeRamasseurs> ramasseurs_dispo = new ArrayList();
            for(EquipeRamasseurs eq : ramasseurs_collection){
                if(eq.estDisponible(date, creneau)){
                    ramasseurs_dispo.add(eq);
                }
            }
            for(int i = 0 ; i < 2 ; i++){
                EquipeRamasseurs eq = ramasseurs_dispo.get(i);
                eq.assignerAMatch(id_match);
            }
        }
    }
    
    private class EditerActionListener implements ActionListener{

        AjouterActionListener al = new AjouterActionListener();
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Match match = planningModel.getRowAt(planning.getSelectedRow());
            int id_match = match.getId_match();
            
            String[] liste_choix = {"Date" , "Creneau", "Court", "Joueurs/Equipes", "Arbitres", "Ramasseurs"};
            String choix = (String) JOptionPane.showInputDialog(null,"Choix de modification","Edition d'un match",JOptionPane.PLAIN_MESSAGE,null, liste_choix, null);
            
            switch(choix){
                case "Date" :
                    editDate(match);
                    break;
                case "Creneau" :
                    editCreneau(match);
                    break;
                case "Court" :
                    editCourt(match);
                    break;
                case "Joueurs/Equipes" :
                    editJoueurs(match);
                    break;
                case "Arbitres" :
                    editArbitres(match);
                    break;
                case "Ramasseurs" :
                    editRamasseurs(match);
                   break;
                default :
                    break;
            }
        } 
        
        public void editDate(Match match){
            String date_string = al.choixDate();
            System.out.println(date_string);
            Date date_match = null;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date date = df.parse(date_string);
                date_match = new java.sql.Date(date.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Statement stmt = co.createStatement();
                String query = "UPDATE `MATCH` "
                            + "SET date_match = STR_TO_DATE('" + date_match.toString() + "', '%Y-%m-%d') "
                            + "WHERE id_match = " + match.getId_match();
                stmt.executeUpdate(query);
                stmt.close();
                match.setDate(date_match);
                planningModel.fireTableDataChanged();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void editCreneau(Match match){
            String creneau = al.choixCreneau(match.getDate());
            try {
                Statement stmt = co.createStatement();
                String query = "UPDATE `MATCH` "
                            + "SET creneau_match = '" + creneau + "' "
                            + "WHERE id_match = " + match.getId_match();
                stmt.executeUpdate(query);
                stmt.close();
                match.setCreneau(creneau);
                planningModel.fireTableDataChanged();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void editCourt(Match match){
            String court = al.choixCourt(match.getDate(), match.getCreneau());
            try {
                Statement stmt = co.createStatement();
                String query = "UPDATE ASSIGNEMENT_COURT "
                                + "SET id_court = (SELECT id_court FROM COURT WHERE nom_court = '" + court + "') "
                                + "WHERE id_match = " + match.getId_match() + ";";
                stmt.executeUpdate(query);
                stmt.close();
                
                match.setCourt(court);
                planningModel.fireTableDataChanged();
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void editJoueurs(Match match){
            String query;
            int[] old_id = new int[2];
            
            if(match.isMatchSimple()){
                //On récupère les id des anciens joueurs
                try {
                    Statement stmt = co.createStatement();
                    query = "SELECT id_joueur FROM ASSIGNEMENT_JOUEUR WHERE id_match = " + match.getId_match();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    old_id[0] = rs.getInt(1);
                    rs.next();
                    old_id[0] = rs.getInt(1);
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //on choisit les nouvelles équipes
                List<Joueur> joueurs_assignes = new ArrayList();
                joueurs_assignes = al.choixJoueur(match.getDate(), match.getCreneau(), match.getTour(), match.getCategorie());
                
                //On remplace les anciennes équipes par les nouvelles
                try {                
                    Statement stmt = co.createStatement();
                    query = "UPDATE ASSIGNEMENT_JOUEUR "
                        + "SET id_joueur = " + joueurs_assignes.get(0).getId_joueur() 
                        + " WHERE id_match = " + match.getId_match() + " AND id_joueur = " + old_id[0] + ";";

                    stmt.executeUpdate(query);
                    
                    query = "UPDATE ASSIGNEMENT_JOUEUR "
                        + "SET id_joueur = " + joueurs_assignes.get(1).getId_joueur() 
                        + " WHERE id_match = " + match.getId_match() + " AND id_joueur = " + old_id[1] + ";";
                    
                    stmt.executeUpdate(query);
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //on remplace les joueurs dans l'objet match puis on actualise le tableau
                match.setJoueurs(joueurs_assignes);
                planningModel.fireTableDataChanged();
            }
            
            else{
                try {
                    Statement stmt = co.createStatement();
                    query = "SELECT id_equipe FROM ASSIGNEMENT_EQUIPE WHERE id_match = " + match.getId_match();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    old_id[0] = rs.getInt(1);
                    rs.next();
                    old_id[0] = rs.getInt(1);
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                List<Equipe> equipes_assignees = new ArrayList();
                equipes_assignees = al.choixEquipe(match.getDate(), match.getCreneau(), match.getTour());
                
                try {
                    Statement stmt = co.createStatement();
                    query = "UPDATE ASSIGNEMENT_EQUIPE "
                        + "SET id_equipe = " + equipes_assignees.get(0).getId_equipe()
                        + " WHERE id_match = " + match.getId_match() + " AND id_equipe = " + old_id[0] + ";";

                    stmt.executeUpdate(query);
                    
                    query = "UPDATE ASSIGNEMENT_EQUIPE "
                        + "SET id_equipe = " + equipes_assignees.get(1).getId_equipe() 
                        + " WHERE id_match = " + match.getId_match() + " AND id_equipe = " + old_id[1] + ";";
                    
                    stmt.executeUpdate(query);
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                match.setEquipes(equipes_assignees);
                planningModel.fireTableDataChanged();
            }
        }
        
        public void editArbitres(Match match){
            
        }
        
        public void editRamasseurs(Match match){
            
        }
    }
    
    private class SupprimerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Match match = planningModel.getRowAt(planning.getSelectedRow());
                int id = match.getId_match();
                
                int reponse = JOptionPane.showConfirmDialog (null, "Voulez-vous vraiment supprimer ce match ? (id n°" + id + ")" ,"Suppression d'un match",JOptionPane.YES_NO_OPTION);
                if(reponse == JOptionPane.YES_OPTION){

                    Statement stmt = co.createStatement();
                    //Suppression assignement arbitres
                    String query = "DELETE FROM ASSIGNEMENT_ARBITRE WHERE id_match =" + id;
                    stmt.executeUpdate(query);
                    //Suppression assignement court
                    query = "DELETE FROM ASSIGNEMENT_COURT WHERE id_match = " + id;
                    stmt.executeUpdate(query);
                    //Suppression equipe ramasseurs
                    query = "DELETE FROM ASSIGNEMENT_EQUIPE_RAMASSEURS WHERE id_match = " + id;
                    stmt.executeUpdate(query);

                    //Suppression joueur ou equipe
                    if(match.isMatchSimple()){
                        query = "DELETE FROM ASSIGNEMENT_JOUEUR WHERE id_match = " + id;
                    }
                    else{
                        query = "DELETE FROM ASSIGNEMENT_EQUIPE WHERE id_match = " + id;
                    }
                    stmt.executeUpdate(query);

                    query = "DELETE FROM `MATCH` WHERE id_match = " + id;
                    stmt.executeUpdate(query);
                    stmt.close();
                    data.remove(match);
                    planningModel.fireTableDataChanged();
                    JOptionPane.showConfirmDialog(null, "Match supprimé !", "Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PanelPlanning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
    }
}
