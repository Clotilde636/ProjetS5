
package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class SceneMenu extends GridPane{
    //Attributs
    private Button btPlaceholder;

    
    //Constructeur
    public SceneMenu() {
        //Mise en forme du GridPane
 
        this.setVgap(10); //10 pixel de marge horizontalement entre les composants
        this.setHgap(10); //10 pixel de marge verticalement entre les composants
        
        //Declaration des composants
        this.btPlaceholder = new Button("Placeholder");

        
        //Options de mise en forme des composants
        btPlaceholder.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        //Localisation des boutons sur le GridPane 
        this.add(btPlaceholder,1,1);
        
        //Gestion des événements
        this.btPlaceholder.setOnAction((event) -> {this.methodetest();});
    }
    
    //méthode appelée lorque le bouton "btPlaceholder" est cliqué
    public void methodetest() {
        System.out.println("bouton cliqué");
    }   
    
}