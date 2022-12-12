
package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class MenuPrincipal { //extends Application{

    public void start(Stage stage, String mail) {
        Scene sc = new Scene(new SceneMenu(mail));  //appelle le constructeur de la classe SceneMenu
        stage.setWidth(700);
        stage.setHeight(500);
        stage.setScene(sc);
        stage.setTitle("Menu Principal");
        stage.show();
        
        
    }

    public static void main(String[] args) {
        //launch();
    }

}
