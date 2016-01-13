package IHM;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

public class Authentification extends JFrame{

    private String login_string = "admin";
    private String password_string = "admin";
    private boolean admin_mode;
    
    JLabel login,mdp;
    JTextField login1;
    JPasswordField mdp1;
    JButton valider,annuler;
    
    JPanel panel;
    
    public Authentification(boolean mode){
        super();
        this.admin_mode = mode;
        this.setTitle("Authentification Administrateur");
        this.setSize(new Dimension(400,200));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
         
        initComponent();
    }
    
    public void initComponent(){
        login = new JLabel("Login");
        login1 = new JTextField();
         
        mdp = new JLabel("Mot de Passe");
        mdp1 = new JPasswordField();
         
        valider = new JButton("Valider ");
        annuler = new JButton(" Annuler");
        
        Container contenu = this.getContentPane();
        contenu.setLayout(null);
         
        contenu.add(login);
        login.setBounds(20, 20, 100, 20);
         
        contenu.add(login1);
        login1.setBounds(150, 20, 150, 20);
         
        contenu.add(mdp);
        mdp.setBounds(22, 55, 100, 20);
         
        contenu.add(mdp1);
        mdp1.setBounds(150, 55, 150, 20);
         
        contenu.add(valider);
        valider.setBounds(125,100 ,77 ,20 );
         
        contenu.add(annuler);
        annuler.setBounds(225, 100, 82, 20);
         
        valider.addActionListener(new ValiderListener());  
        annuler.addActionListener(new AnnulerListener());
         
        this.setVisible(true);
    }

    private boolean isCorrect(char[] input){
        boolean isCorrect = true;
        char[] correctPassword = password_string.toCharArray();
        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }

        //Zero out the password.
        Arrays.fill(correctPassword,'0');

        return isCorrect;
    }
    
    private class ValiderListener implements ActionListener{
         
        @Override
        public void actionPerformed(ActionEvent e) {
            if(login1.getText().equals(login_string)){
                if(isCorrect(mdp1.getPassword())){
                    //si auth validée, on ferme la page d'identification et on ouvre une nouvelle fenêtre de l'appli en mode admin
                    admin_mode = true;
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Fenetre f = new Fenetre(admin_mode);
                            f.setVisible(true);
                        }
                    });
                }
                else{
                    JOptionPane.showConfirmDialog(null, "Mauvais mot de passe", "Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                }
            }
            else{
                JOptionPane.showConfirmDialog(null, "Mauvais identifiants", "Confirmation", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
        }         
    }

    private class AnnulerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            dispose();
        }
        
    }
}
