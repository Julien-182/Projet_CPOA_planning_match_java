package IHM;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Fenetre extends JFrame{
    
    public Fenetre(){
        setTitle("MATCH PLANNIFICATOR");
        setSize(new Dimension(1000,700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
    }
    
    private void initComponent(){
        PanelEdit panelEdit = new PanelEdit();
        this.setContentPane(panelEdit);
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
