package IHM;

import bdd.ConnectionMySQL;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import planning.match.match.Match;

public class Fenetre extends JFrame{
    
    private PanelAjout panelAjout;
    private PanelPlanning panelPlanning;
    private Connection co;
    
    public Fenetre(){
        setTitle("MATCH PLANNIFICATOR");
        setSize(new Dimension(1000,700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Voulez-vous quitter l'application ?\n(La connection sera fermée)") == JOptionPane.OK_OPTION){
                    try {
                        System.out.println("Closing connection...");
                        co.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setVisible(false);
                    dispose();
                }
            }
        });
        
        Image icone = Toolkit.getDefaultToolkit().getImage("src/icon.png"); 
        this.setIconImage(icone);
        setResizable(false);
        initConnection();
        initComponent();
    }
    
    private void initConnection(){
        ConnectionMySQL coMySQL = new ConnectionMySQL();
        this.co = coMySQL.getConnection();   
        if(this.co == null){
            this.dispose();
        }
    }
    
    private void initComponent(){
        panelAjout = new PanelAjout();
        panelPlanning = new PanelPlanning(this.co);
        
        add(panelPlanning);
        add(panelAjout);
        add(panelPlanning);
        this.setContentPane(panelPlanning);
    }
}
