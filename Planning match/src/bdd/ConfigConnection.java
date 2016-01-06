 package bdd;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.net.URL;
import planning.match.participants.*;


/**
 * ConfigConnection.java
 * Initialise une connexion à une base
 * en lisant un fichier de propriétés
 * @version 2 (avec getResource)
 */
public class ConfigConnection {
     /**
   * Obtenir une connexion à partir des infos qui sont
   * dans un fichier de propriétés.
   * Le fichier doit contenir les propriétés driver, url,
   * utilisateur, mdp (mot de passe).
   * @param nomFichierProp nom du fichier de propriétés. Si le nom
   * commence par "/", l'emplacement désigne un endroit relatif
   * au classpath, sinon il désigne un endroit relatif au
   * répertoire qui contient le fichier ConfigConnection.class.
   * @return une connexion à la base
   */
  public static Connection getConnection(String nomFichierProp)
      throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    Properties props = new Properties();
    URL urlFichierProp = ConfigConnection.class.getResource(nomFichierProp);
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(urlFichierProp.openStream());
      props.load(bis);
      String driver = props.getProperty("driver");
      String url = props.getProperty("url");
      String utilisateur = props.getProperty("utilisateur");
      String mdp = props.getProperty("mdp");
      Class.forName(driver);
      return DriverManager.getConnection(url, utilisateur, mdp);
    }
    finally {
      if (bis != null) {
        bis.close();
      }
    }
  }

  /**
   * Obtenir une connexion à partir des infos qui sont
   * dans un fichier de propriétés, du nom d'utilisateur
   * et du mot de passe passés en paramètre.
   * Le fichier doit contenir les propriétés driver, url.
   * Le nom et le mot de passe de l'utilisateur sont passés
   * en paramètre de la méthode.
   * @param nomFichierProp nom du fichier de propriétés. Si le nom
   * commence par "/", l'emplacement désigne un endroit relatif
   * au classpath, sinon il désigne un endroit relatif au
   * répertoire qui contient le fichier ConfigConnection.class.
   * @param utilisateur nom de l'utilisateur.
   * @param mdp mot de passe de l'utilisateur.
   * @return une connexion à la base.
   */
  public static Connection getConnection(String nomFichierProp,
                                         String utilisateur,
                                         String mdp)
      throws IOException, ClassNotFoundException, SQLException {
    Properties props = new Properties();
    URL urlFichierProp = ConfigConnection.class.getResource(nomFichierProp);
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(urlFichierProp.openStream());
      props.load(bis);
      String driver = props.getProperty("driver");
      String url = props.getProperty("url");
      Class.forName(driver);
      return DriverManager.getConnection(url, utilisateur, mdp);
    }
    finally {
      if (bis != null) {
        bis.close();
      }
    }
  }
  
  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
      Connection co = ConfigConnection.getConnection("connect.properties");
  }
 
}
