package planning.match;
import planning.match.match.*;
import planning.match.participants.*;

public class main {

    public static void main(String[] args) {
        Arbitre arbitre = new Arbitre(1,"Dugelet","Aubin","Touche","JAT2","Française");
        System.out.println(arbitre);
        System.out.println();
        System.out.println();
        Equipe equipe = new Equipe(1,"3ème");
        System.out.println(equipe);
        System.out.println();
        System.out.println();
        Joueur joueur = new Joueur(1,"Ducrovic","Juvak","3ème tour");
        System.out.println(joueur);
        System.out.println();
        System.out.println();
        Ramasseur ramasseur = new Ramasseur(1,"Merle","Jérémy");
        System.out.println(ramasseur);
        System.out.println();
        System.out.println();
        Match match = new Match(1,(2013,0,31è));
        System.out.println(match);
    }
    
}

