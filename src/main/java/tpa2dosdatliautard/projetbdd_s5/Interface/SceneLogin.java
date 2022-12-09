
package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
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

/**
 *
 * @author Elève
 */
public class SceneLogin extends GridPane{
    //Attributs
    private Button btConnexion;
    private Button btInscription;
    private TextField txtf_id;
    private PasswordField pwf_mdp;
    private Label labelid;
    private Label labelmdp;
    private Label labelInscription;
    
    //Constructeur
    public SceneLogin() {
        //Connexion à la Bdd
//        public static void main(String[] args) {
//        try ( Connection con = defautConnect()) {
//            System.out.println("connecté !!!");
//            System.out.println("");
//            //menuSimple(con);
//            menuComplet(con);
//        } catch (Exception ex) {
//            throw new Error(ex);
//        }
//    }
        
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
        this.btConnexion = new Button("Connexion");
        this.btInscription = new Button("Inscription");
        this.txtf_id = new TextField();
        this.pwf_mdp = new PasswordField();
        this.labelid = new Label("Identifiant");
        this.labelmdp = new Label("mot de passe");
        this.labelInscription = new Label("Pas encore de compte ? Inscrivez-vous!");
        
        //Options de mise en forme des composants
        btConnexion.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btInscription.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //Localisation des composants sur le GridPane 
        this.add(labelid,1,1);
        this.add(txtf_id,1,2);
        this.add(labelmdp,1,3);
        this.add(pwf_mdp,1,4);
        this.add(btConnexion,1,6);
        this.add(labelInscription,1,8);
        this.add(btInscription,1,9);
        
        //Gestion des événements
        this.btConnexion.setOnAction((event) -> {this.checkID();});
        this.btInscription.setOnAction((event) -> {this.askDataInscription();});
    }
    
    //méthode appelée lorque btConnexion est cliqué
    public void checkID() {
        String id = this.txtf_id.getText();
        String mdp = this.pwf_mdp.getText();
        try(Connection con = Main.defautConnect()){
                String result=Personne.login(con, id, mdp);
                if(result.equals("erreur")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Mot de passe ou Identifiant incorrect");            
                    alert.showAndWait();
                }
                else{
                    Stage StageMenu = new Stage();
                    MenuPrincipal menu = new MenuPrincipal();
                    menu.start(StageMenu);
                    StageMenu.setWidth(500);
                    StageMenu.setHeight(500);
                    StageMenu.show();
                    //Fermeture de la fenêtre login
                    Stage stage = (Stage) btConnexion.getScene().getWindow();
                    stage.close();
                }
                //System.out.println("connexion à la base de données réussie");
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            }                 
    }
    
//    public void checkID() {
//        String id = this.txtf_id.getText();
//        String mdp = this.pwf_mdp.getText();
//        if (id.equals("Emilien") & mdp.equals("pass")){
//            System.out.println("succès");
//            Stage StageMenu = new Stage();
//            MenuPrincipal menu = new MenuPrincipal();
//            menu.start(StageMenu);
//            StageMenu.setWidth(500);
//            StageMenu.setHeight(500);
//            StageMenu.show();
//            
//        }
//        else{
//            System.out.println("échec");
//        }                  
//    }
    
    //méthode appelée lorque le bouton btInscription est cliqué
    public void askDataInscription() {
        Stage StageInscription = new Stage();
        MenuInscription inscription = new MenuInscription();
        inscription.start(StageInscription);
        StageInscription.show();                
    }
    
    //Gestion des événements
        
//        EventHandler<ActionEvent> connexion = new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e){
//                id=txtf_id.getText();
//                mdp=txtf_mdp.getText();
//                System.out.println(id+mdp);
// 
//            }
//        }; 
    
}