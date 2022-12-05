
package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuPrincipal { //extends Application{

    public void start(Stage stage) {
        Scene sc = new Scene(new SceneMenu());  //appelle le constructeur de la classe SceneMenu
        stage.setWidth(600);
        stage.setHeight(100);
        stage.setScene(sc);
        stage.setTitle("Menu Principal");
        stage.show();
        
        
    }

    public static void main(String[] args) {
        //launch();
    }

}
