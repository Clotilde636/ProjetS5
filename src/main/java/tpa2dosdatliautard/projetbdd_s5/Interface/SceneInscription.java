
package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tpa2dosdatliautard.projetbdd_s5.Main;
import tpa2dosdatliautard.projetbdd_s5.Personne;
import tpa2dosdatliautard.projetbdd_s5.Utilisateur;


public class SceneInscription extends GridPane{
    //Attributs
    private Button btInscription;
    private TextField txtf_prenom;
    private TextField txtf_nom;
    private TextField txtf_mail;
    private TextField txtf_ville;
    private TextField txtf_codePostal;
    private PasswordField pwf_mdp;
    private PasswordField pwf_mdpverif;
    private Label labelprenom;
    private Label labelnom;
    private Label labelmail;
    private Label labelville;
    private Label labelcodePostal;
    private Label labelmdp;
    private Label labelmdpverif;
    
    //Constructeur
    public SceneInscription() {
        //Mise en forme du GridPane
        ColumnConstraints col_gauche = new ColumnConstraints();
        col_gauche.setPercentWidth(10);
        this.getColumnConstraints().add(col_gauche);
        ColumnConstraints colonnePrincipale = new ColumnConstraints();
        colonnePrincipale.setPercentWidth(90);
        this.getColumnConstraints().add(colonnePrincipale);
        ColumnConstraints col_droite = new ColumnConstraints();
        col_droite.setPercentWidth(10);
        this.getColumnConstraints().add(col_droite);
//        for (int n=0;n<5;n++){
//            RowConstraints ligne = new RowConstraints();
//            ligne.setPercentHeight(20);
//            this.getRowConstraints().add(ligne);
//        }
        this.setVgap(10); //10 pixel de marge horizontalement entre les composants
        this.setHgap(10); //10 pixel de marge verticalement entre les composants
        
        //Declaration des composants
        this.btInscription = new Button("Je m'inscris");
        this.txtf_prenom = new TextField();
        this.txtf_nom = new TextField();
        this.txtf_mail = new TextField();
        this.txtf_ville = new TextField();
        this.txtf_codePostal = new TextField();
        this.pwf_mdp = new PasswordField();
        this.pwf_mdpverif = new PasswordField();
        this.labelprenom = new Label("Prénom");
        this.labelnom = new Label("Nom");
        this.labelmail = new Label("E-Mail");
        this.labelville = new Label("Ville");
        this.labelcodePostal = new Label("Code Postal");
        this.labelmdp = new Label("Mot de Passe");
        this.labelmdpverif = new Label("Vérifiez votre mot de passe");
        
        //Options de mise en forme des composants
        btInscription.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //Localisation des composants sur le GridPane 
        this.add(labelprenom,1,1);
        this.add(txtf_prenom,1,2);
        this.add(labelnom,1,3);
        this.add(txtf_nom,1,4);
        this.add(labelmail,1,5);
        this.add(txtf_mail,1,6);
        this.add(labelville,1,7);
        this.add(txtf_ville,1,8);
        this.add(labelcodePostal,1,9);
        this.add(txtf_codePostal,1,10);
        this.add(labelmdp,1,11);
        this.add(pwf_mdp,1,12);
        this.add(labelmdpverif,1,13);
        this.add(pwf_mdpverif,1,14);
        this.add(btInscription,1,16);
        
        //Gestion des événements
        this.btInscription.setOnAction((event) -> {this.DoInscription();});
    }


    //méthode appelée lorsque btInscription est cliqué 
    public void DoInscription(){
        String mdp1 = this.pwf_mdp.getText();
        String mdp2 = this.pwf_mdpverif.getText();
        if(mdp1.equals(mdp2)){
            String prenom = this.txtf_prenom.getText();
            String nom = this.txtf_nom.getText();
            String mail = this.txtf_mail.getText();
            String ville = this.txtf_ville.getText();
            String codePostal = this.txtf_codePostal.getText();
            try(Connection con = Main.defautConnect()){
                Personne.creerPersonne(con, mail, mdp1, nom, prenom, codePostal, ville);
                Stage stage = (Stage) btInscription.getScene().getWindow();
                stage.close();
                //System.out.println("connexion à la base de données réussie");
            }
            catch(ClassNotFoundException | SQLException ex){
                System.out.println("la connexion à la base de données a échoué");
            }
            catch(Utilisateur.EmailExisteDejaException pb_mail){
                System.out.println("cet e-mail est déjà utilisé");
            }
        }
        else{
            System.out.println("Erreur: les mots de passe sont différents");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Attention, les mots de passe saisis ne sont pas identiques!");
            alert.setContentText("Vérifiez que votre mot de passe est le même dans les 2 champs de saisie, puis réessayez de vous inscrire.");            
            alert.showAndWait();
        }
            
    }
    

    
}