package planning.match.participants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import planning.match.match.Match;

public class EquipeRamasseurs {

    private List<Ramasseur> liste_ramasseurs;
    private int id_equipe_ramasseurs;
    private String nom_equipe_ramasseur;
    
    private Connection co;
    
    public EquipeRamasseurs(Connection co,int id_equipe, String nom) throws SQLException{
        this.co = co;
        this.id_equipe_ramasseurs = id_equipe;
        this.nom_equipe_ramasseur = nom;
        this.liste_ramasseurs = new ArrayList<>();
        findRamasseurs();
    }
    
    private void findRamasseurs() throws SQLException{
        String query = "SELECT * FROM RAMASSEUR WHERE id_ramasseur IN ("
                                                                    + "SELECT id_ramasseur"
                                                                    + " FROM ASSIGNEMENT_RAMASSEUR"
                                                                    + " WHERE id_equipe_ramasseurs = " + id_equipe_ramasseurs 
                                                                    + ");";
        Statement stmt = co.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while(rs.next()){
            int id = rs.getInt("id_ramasseur");
            String nom = rs.getString("nom_ramasseur");
            String prenom = rs.getString("prenom_ramasseur");
            
            this.liste_ramasseurs.add(new Ramasseur(
                                        co,
                                        id,
                                        nom,
                                        prenom
                                     ));
        }
    }
    
    public void assignerAMatch(int id_match){
        try {
            Statement stmt = co.createStatement();
            String query = "INSERT INTO ASSIGNEMENT_EQUIPE_RAMASSEURS VALUES (" + id_match + "," + this.id_equipe_ramasseurs + ");";
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(EquipeRamasseurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Ramasseur> getRamasseurs(){
        return this.liste_ramasseurs;
    }
    
    public boolean estDisponible(Date date, String creneau){
        try {
            /*
            Recuperer tous les matchs joués pendant la date et le créneau
            Regarder dans les matchs si l'équipe de ramasseurs est assignée au match
            SI OUI --> return false (pas dispo)
            SI NON --> return true (dispo)
            */
            boolean dispo;
            Statement stmt = this.co.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT id_equipe_ramasseurs "
                    + "FROM ASSIGNEMENT_EQUIPE_RAMASSEURS "
                    + "WHERE id_match IN("
                    + "SELECT id_match"
                    + " FROM `MATCH` "
                    + "WHERE date_match = STR_TO_DATE('" + date.toString() + "' , '%Y-%M-%d') " + "AND creneau_match = '" + creneau
                    + "')");
            if(rset.next()) dispo =  false;
            else dispo = true;
            
            stmt.close();
            rset.close();
            return dispo;
        } catch (SQLException ex) {
            Logger.getLogger(EquipeRamasseurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
