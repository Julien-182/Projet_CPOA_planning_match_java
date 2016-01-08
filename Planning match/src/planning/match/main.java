package planning.match;
import IHM.Fenetre;
import java.text.ParseException;
import javax.swing.SwingUtilities;
import planning.match.match.Match;
import planning.match.participants.Equipe;
import planning.match.participants.Joueur;
import planning.match.participants.Ramasseur;
import planning.match.participants.Arbitre;

public class main {

    public static void main(String[] args) throws ParseException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Fenetre f = new Fenetre();
                f.setVisible(true);
            }
        });
    }
    
}

