package planning.match;
import java.text.ParseException;
import planning.match.match.Match;
import planning.match.participants.Equipe;
import planning.match.participants.Joueur;
import planning.match.participants.Ramasseur;
import planning.match.participants.Arbitre;

public class main {

    public static void main(String[] args) throws ParseException {
        Arbitre arbitre = new Arbitre(1,"Dugelet","Aubin","JAT2","Française",0,0);
        System.out.println(arbitre);
        System.out.println();
        System.out.println();
        //Equipe equipe = new Equipe(1,"3ème");
        //System.out.println(equipe);
        //System.out.println();
        //System.out.println();
        Joueur joueur = new Joueur(1,"Ducrovic","Juvak","3ème tour", "Français");
        System.out.println(joueur);
        System.out.println();
        System.out.println();
        Ramasseur ramasseur = new Ramasseur(1,"Merle","Jérémy");
        System.out.println(ramasseur);
        System.out.println();
        System.out.println();
        //Match match = new Match(1,17/12/2015,"19h-20h","simple","finale");
        //System.out.println(match);
    }
    
}

