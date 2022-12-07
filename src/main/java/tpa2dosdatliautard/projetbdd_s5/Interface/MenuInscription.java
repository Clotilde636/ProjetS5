package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuInscription { //extends Application{

    public void start(Stage stage) {
        Scene sc = new Scene(new SceneInscription());  //appelle le constructeur de la classe SceneMenu
        stage.setWidth(300);
        stage.setHeight(650);
        stage.setScene(sc);
        stage.setTitle("Inscription");
        stage.show();
        
        
    }

    public static void main(String[] args) {
        //launch();
    }

}