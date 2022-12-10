
package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tpa2dosdatliautard.projetbdd_s5.Main;
import tpa2dosdatliautard.projetbdd_s5.Utilisateur;


public class SceneMenu extends TabPane{ //extends TabPane
    String clrEdit = "#b0c4de"; //reminder: couleur blanche = #fffff
    String clrBase = "#f3f3f3"; //reminder: couleur gris de fenetre = #f3f3f3
    
    //Attributs
    private GridPane grilleProfil;
    private GridPane grilleArticles;
    private TabPane tabpane;
    private Button btPlaceholder;
    private Button btEditPrenom;
    private Button btConfirmPrenom;
    private Button btEditNom;
    private Button btConfirmNom;
    private Button btEditMdp;
    private Button btConfirmMdp;
    private Button btEditVille;
    private Button btConfirmVille;
    private Button btEditCodePostal;
    private Button btConfirmCodePostal;
    private Button btDeconnexion;
    private Label labelmail;
    private Label labeldescriptionmail;
    private Label labelprenom;
    private Label labelnom;
    private Label labelville;
    private Label labelcodePostal;
    private Label labelmdp;
    private TextField txtf_mail;
    private TextField txtf_prenom;
    private TextField txtf_nom;
    private TextField txtf_ville;
    private TextField txtf_codePostal;
    private TextField txtf_mdp;
    
    //Constructeur
    public SceneMenu(String mail) {       
        //Instanciation et Mise en forme des GridPane
        this.grilleProfil = new GridPane();
        ColumnConstraints bord_gauche = new ColumnConstraints();
        bord_gauche.setPercentWidth(10);
        grilleProfil.getColumnConstraints().add(bord_gauche);
        ColumnConstraints col_credentials = new ColumnConstraints();
        col_credentials.setPercentWidth(50);
        grilleProfil.getColumnConstraints().add(col_credentials);
        ColumnConstraints col_boutons1 = new ColumnConstraints();
        col_boutons1.setPercentWidth(15);
        grilleProfil.getColumnConstraints().add(col_boutons1);
        ColumnConstraints col_boutons2 = new ColumnConstraints();
        col_boutons2.setPercentWidth(15);
        grilleProfil.getColumnConstraints().add(col_boutons2);
        ColumnConstraints bord_droit = new ColumnConstraints();
        bord_droit.setPercentWidth(10);
        grilleProfil.getColumnConstraints().add(bord_droit);
        grilleProfil.setVgap(10);   //10 pixels de marge verticale entre composants
        grilleProfil.setHgap(10);   //10 pixels de marge horizontale entre composants
        
        this.grilleArticles = new GridPane();
        
        //Instanciation Textfields et Labels
        labelprenom = new Label("Prenom");
        labelnom = new Label("Nom");
        labelmail = new Label("Adresse Mail");
        labeldescriptionmail = new Label("Ne peut pas être changée");
        labelmdp = new Label("Mot de Passe");
        labelville = new Label("Ville");
        labelcodePostal = new Label("Code postal");
        txtf_mail = new TextField(mail);
        txtf_mail.setEditable(false);
        txtf_mail.setStyle("-fx-control-inner-background:"+clrBase);
        //Recuperation du contenu des TextFields depuis la BDD
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            System.out.println(Utilisateur.ProfilUtilisateur(con, mail).get_prenom());
            this.txtf_prenom = new TextField(Utilisateur.ProfilUtilisateur(con, mail).get_prenom());
            txtf_prenom.setEditable(false);
            txtf_prenom.setStyle("-fx-control-inner-background:"+clrBase);
            this.txtf_nom = new TextField(Utilisateur.ProfilUtilisateur(con, mail).get_nom());
            System.out.println("Utilisateur.ProfilUtilisateur(con, mail).get_nom() -> "+Utilisateur.ProfilUtilisateur(con, mail).get_nom());
            txtf_nom.setEditable(false);
            txtf_nom.setStyle("-fx-control-inner-background:"+clrBase);
            this.txtf_mdp = new TextField(Utilisateur.ProfilUtilisateur(con, mail).get_pass());
            System.out.println("Utilisateur.ProfilUtilisateur(con, mail).get_pass() -> "+Utilisateur.ProfilUtilisateur(con, mail).get_pass());
            txtf_mdp.setEditable(false);
            txtf_mdp.setStyle("-fx-control-inner-background:"+clrBase);
            this.txtf_ville = new TextField(Utilisateur.ProfilUtilisateur(con, mail).get_ville());
            System.out.println("Utilisateur.ProfilUtilisateur(con, mail).get_ville() -> "+Utilisateur.ProfilUtilisateur(con, mail).get_ville());
            txtf_ville.setEditable(false);
            txtf_ville.setStyle("-fx-control-inner-background:"+clrBase);
            this.txtf_codePostal = new TextField(Utilisateur.ProfilUtilisateur(con, mail).get_codePostal());
            System.out.println("Utilisateur.ProfilUtilisateur(con, mail).get_codePostal() -> "+Utilisateur.ProfilUtilisateur(con, mail).get_codePostal());
            txtf_codePostal.setEditable(false);
            txtf_codePostal.setStyle("-fx-control-inner-background:"+clrBase);
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            } 
               
        //Instanciation et Mise en Forme des Buttons
        this.btPlaceholder = new Button("placeholder");
        btPlaceholder.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.btEditPrenom = new Button("Modifier");
        this.btConfirmPrenom = new Button("Confirmer");
        this.btEditNom = new Button("Modifier");
        this.btConfirmNom = new Button("Confirmer");
        this.btEditMdp = new Button("Modifier");
        this.btConfirmMdp = new Button("Confirmer");
        this.btEditVille = new Button("Modifier");
        this.btConfirmVille = new Button("Confirmer");
        this.btEditCodePostal = new Button("Modifier");
        this.btConfirmCodePostal = new Button("Confirmer");
        this.btDeconnexion = new Button("Déconnexion");
        this.btDeconnexion.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        //Instanciation et Mise en forme des Tab
        Tab tabProfil = new Tab("Mon Compte",grilleProfil);      
        Tab tabArticles = new Tab("Mes Articles",grilleArticles);  
        this.getTabs().add(0,tabArticles);
        this.getTabs().add(1, tabProfil);


        //Localisation des boutons sur un GridPane, lui meme contenu dans une Tab
        grilleProfil.add(labelmail,1,0);
        grilleProfil.add(txtf_mail, 1, 1);
        grilleProfil.add(labeldescriptionmail,2,1,2,1); //sur 2 cases horizontales et 1 case verticale
        grilleProfil.add(labelprenom,1,2);
        grilleProfil.add(txtf_prenom, 1, 3);
        grilleProfil.add(btEditPrenom,2,3);
        grilleProfil.add(btConfirmPrenom,3,3);
        grilleProfil.add(labelnom,1,4);
        grilleProfil.add(txtf_nom, 1, 5);
        grilleProfil.add(btEditNom,2,5);
        grilleProfil.add(btConfirmNom,3,5);
        grilleProfil.add(labelmdp,1,6);
        grilleProfil.add(txtf_mdp, 1, 7);
        grilleProfil.add(btEditMdp,2,7);
        grilleProfil.add(btConfirmMdp,3,7);
        grilleProfil.add(labelville,1,8);
        grilleProfil.add(txtf_ville, 1, 9);
        grilleProfil.add(btEditVille,2,9);
        grilleProfil.add(btConfirmVille,3,9);
        grilleProfil.add(labelcodePostal,1,10);
        grilleProfil.add(txtf_codePostal, 1, 11);
        grilleProfil.add(btEditCodePostal,2,11);
        grilleProfil.add(btConfirmCodePostal,3,11);
        grilleProfil.add(btDeconnexion,1,13,3,1);
 
        
        grilleArticles.add(btPlaceholder,1,1);
        
        //Gestion des événements
        this.btPlaceholder.setOnAction((event) -> {this.methodetest();});
        this.btDeconnexion.setOnAction((event) -> {this.DoDeconnexion();});
        this.btEditPrenom.setOnAction((event) -> {txtf_prenom.setEditable(true);txtf_prenom.setStyle("-fx-control-inner-background:"+clrEdit);});
        this.btEditNom.setOnAction((event) -> {txtf_nom.setEditable(true);txtf_nom.setStyle("-fx-control-inner-background:"+clrEdit);});
        this.btEditMdp.setOnAction((event) -> {txtf_mdp.setEditable(true);txtf_mdp.setStyle("-fx-control-inner-background:"+clrEdit);});
        this.btEditVille.setOnAction((event) -> {txtf_ville.setEditable(true);txtf_ville.setStyle("-fx-control-inner-background:"+clrEdit);});
        this.btEditCodePostal.setOnAction((event) -> {txtf_codePostal.setEditable(true);txtf_codePostal.setStyle("-fx-control-inner-background:"+clrEdit);});
        this.btConfirmPrenom.setOnAction((event) -> {this.changePrenom(mail);});
        this.btConfirmNom.setOnAction((event) -> {this.changeNom(mail);});
        this.btConfirmMdp.setOnAction((event) -> {this.changeMdp(mail);});
        this.btConfirmVille.setOnAction((event) -> {this.changeVille(mail);});
        this.btConfirmCodePostal.setOnAction((event) -> {this.changeCodePostal(mail);});
        
    }
    
    //méthode appelée lorque le bouton "btPlaceholder" est cliqué
    public void methodetest() {
        System.out.println("bouton cliqué");
    }   
    
    //méthode appelée lorque btDeconnexion est cliqué
    public void DoDeconnexion() {
        System.out.println("Attempting Deconnexion...");       
        //Fermeture menu principal
        Stage stage = (Stage) btDeconnexion.getScene().getWindow();
        stage.close();
        //Ouverture fenetre login
        Stage stageLogin = new Stage();
        MainInterface menuLogin = new MainInterface();
        menuLogin.start(stageLogin);
        stageLogin.show();
    }
    
    //méthode appelée lorque le bouton "btConfirmPrenom" est cliqué
    public void changePrenom(String mail) {
        txtf_prenom.setEditable(false);
        txtf_prenom.setStyle("-fx-control-inner-background:"+clrBase);
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            String newPrenom = txtf_prenom.getText();
            Utilisateur.UpdatePrenomUtilisateur(con, mail, newPrenom);         
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            } 
    }  
    
    //méthode appelée lorque le bouton "btConfirmNom" est cliqué
    public void changeNom(String mail) {
        txtf_nom.setEditable(false);
        txtf_nom.setStyle("-fx-control-inner-background:"+clrBase);
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            String newnom = txtf_nom.getText();
            Utilisateur.UpdateNomUtilisateur(con, mail, newnom);         
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            } 
    }
    
    //méthode appelée lorque btConfirmMdp est cliqué
    public void changeMdp(String mail) {
        txtf_mdp.setEditable(false);
        txtf_mdp.setStyle("-fx-control-inner-background:"+clrBase);
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            String newmdp = txtf_mdp.getText();
            Utilisateur.UpdatePasswordUtilisateur(con, mail, newmdp);         
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            } 
    }
    
    //méthode appelée lorque le bouton "btConfirmVille" est cliqué
    public void changeVille(String mail) {
        txtf_ville.setEditable(false);
        txtf_ville.setStyle("-fx-control-inner-background:"+clrBase);
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            String newville = txtf_ville.getText();
            Utilisateur.UpdateVilleUtilisateur(con, mail, newville);         
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            } 
    }
    
    //méthode appelée lorque le bouton "btConfirmCodePostal" est cliqué
    public void changeCodePostal(String mail) {
        txtf_codePostal.setEditable(false);
        txtf_codePostal.setStyle("-fx-control-inner-background:"+clrBase);
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            String newcodepostal = txtf_codePostal.getText();
            Utilisateur.UpdateCodePostalUtilisateur(con, mail, newcodepostal);         
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            } 
    }  
}