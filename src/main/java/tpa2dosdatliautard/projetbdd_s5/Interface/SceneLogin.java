
package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Elève
 */
public class SceneLogin extends GridPane{
    //Attributs
    private Button btConnexion;
    private TextField txtf_id;
    private PasswordField pwf_mdp;
    private Label labelid;
    private Label labelmdp;
    
    //Constructeur
    public SceneLogin() {
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
        this.txtf_id = new TextField();
        this.pwf_mdp = new PasswordField();
        this.labelid = new Label("Identifiant");
        this.labelmdp = new Label("mot de passe");
        
        //Options de mise en forme des composants
        btConnexion.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //Localisation des boutons sur le GridPane 
        this.add(labelid,1,1);
        this.add(txtf_id,1,2);
        this.add(labelmdp,1,3);
        this.add(pwf_mdp,1,4);
        this.add(btConnexion,1,6);
        
        //Gestion des événements
        this.btConnexion.setOnAction((event) -> {this.checkID();});
    }
    
    //méthode appelée lorque le bouton "Connexion" est cliqué
    public void checkID() {
        String id = this.txtf_id.getText();
        String mdp = this.pwf_mdp.getText();
        if (id.equals("Emilien") & mdp.equals("pass")){
            System.out.println("succès");
            Stage StageMenu = new Stage();
            MenuPrincipal menu = new MenuPrincipal();
            menu.start(StageMenu);
            StageMenu.setWidth(500);
            StageMenu.setHeight(500);
            StageMenu.show();
            
        }
        else{
            System.out.println("échec");
        }                  
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