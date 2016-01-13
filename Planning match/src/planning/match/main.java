package planning.match;
import IHM.Authentification;
import IHM.Fenetre;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import planning.match.match.Match;
import planning.match.participants.Equipe;
import planning.match.participants.Joueur;
import planning.match.participants.Ramasseur;
import planning.match.participants.Arbitre;

public class main {

    private static boolean admin_mode = false;
    
    public static void main(String[] args) throws ParseException {
        
        String mode = choixMode();
        
        if(mode.equals("Admin")){
            Authentification auth = new Authentification(admin_mode);        
        }
        else{
            admin_mode = false;
            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Fenetre f = new Fenetre(admin_mode);
                f.setVisible(true);
            }
        });
        }  
    }
    
    private static String choixMode(){
        String[] modes = {"Admin", "Joueur"};
        String mode = (String)JOptionPane.showInputDialog(null, "Choisissez votre mode d'authentification", "Authentification" ,JOptionPane.PLAIN_MESSAGE,null, modes, modes[0]);
        
        if(mode == null) choixMode();
        return mode;
    }
    
}

