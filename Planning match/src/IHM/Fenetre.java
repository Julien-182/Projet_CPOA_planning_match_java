package IHM;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Fenetre extends JFrame{
    
    private PanelAjout panelAjout;
    private PanelPlanning panelPlanning;
    
    public Fenetre(){
        setTitle("MATCH PLANNIFICATOR");
        setSize(new Dimension(1000,700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
    }
    
    private void initComponent(){
        panelAjout = new PanelAjout();
        panelPlanning = new PanelPlanning();
        
        add(panelPlanning);
        add(panelAjout);
        add(panelPlanning);
        this.setContentPane(panelPlanning);
    }

    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Fenetre f = new Fenetre();
                f.setVisible(true);
            }
        });
    }
}
