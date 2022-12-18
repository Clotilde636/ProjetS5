package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuEnchere { //extends Application{

    public void start(Stage stage, String mail) {
        Scene sc = new Scene(new SceneEnchere(mail));  //appelle le constructeur de la classe SceneEnchere
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setScene(sc);
        stage.setTitle("Nouvelle Enchère");
        stage.show();
        
        
    }

    public static void main(String[] args) {
        //launch();
    }

}