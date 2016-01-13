package IHM;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import planning.match.match.Creneau;
import planning.match.match.Date_Match;

public class Reservation extends JFrame{

    private Connection co;
    
    private JPanel panel;
    private JComboBox liste_date, liste_creneau, liste_court;
    private JButton reserverButton;
    
    public Reservation(Connection co){
        super();
        this.setTitle("Mode Réservation");
        this.setSize(new Dimension(400,200));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
            }
        });
        
        this.setResizable(false);
         
        initComponent();
        this.co = co;
    }

    private void initComponent() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel labelDate = new JLabel("Choix date");
        liste_date = new JComboBox(Date_Match.values());
        
        JLabel labelCreneau = new JLabel("Choix creneau");
        liste_creneau = new JComboBox(Creneau.values());
        
        JLabel labelCourt = new JLabel("Choix du Court");
        String[] courts = {"Grand Court de Gerlan","Court de Saint-Andre","Moyen Court", "Golden Court"};
        liste_court = new JComboBox(courts);
        
        reserverButton = new JButton("Reserver");
        reserverButton.addActionListener(new ValidReservationActionListener());
        
        panel.add(labelDate);
        panel.add(liste_date);
        panel.add(labelCreneau);
        panel.add(liste_creneau);
        panel.add(labelCourt);
        panel.add(liste_court);
        panel.add(reserverButton);
        this.setContentPane(panel);
    }
    
    private class ValidReservationActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Date_Match date_reservation = (Date_Match) liste_date.getSelectedItem();
            String creneau_reservation = liste_creneau.getSelectedItem().toString();
            String court_reservation = (String) liste_court.getSelectedItem();
            
            if(date_reservation != null && creneau_reservation != null && court_reservation != null){
                String nom = JOptionPane.showInputDialog("Entrer votre nom");
                String prenom = JOptionPane.showInputDialog("Entrer votre prénom");
                
                try {
                    Statement stmt = co.createStatement();
                    String query = "SELECT id_joueur FROM JOUEUR WHERE nom_joueur = '" + nom + "' AND prenom_joueur = '" + prenom + "';";
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    int id_joueur = rs.getInt(1);
                    
                    query = "SELECT id_court FROM COURT WHERE nom_court = '" + court_reservation + "';";
                    rs = stmt.executeQuery(query);
                    rs.next();
                    int id_court = rs.getInt(1);
                    rs.close();
                    
                    query = "INSERT INTO RESERVATION VALUES( STR_TO_DATE('" + date_reservation.toString() + "', '%Y-%m-%d'), '" 
                                                                            + creneau_reservation + "', "
                                                                            + id_court + ","
                                                                            + id_joueur + ");";
                    stmt.executeUpdate(query);
                    stmt.close();
                    JOptionPane.showConfirmDialog(null, "Réservation ajoutée !", "Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(Reservation.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
    }
}
