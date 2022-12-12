package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuArticle { //extends Application{

    public void start(Stage stage, String mail) {
        Scene sc = new Scene(new SceneArticle(mail));  //appelle le constructeur de la classe SceneArticle
        stage.setWidth(300);
        stage.setHeight(550);
        stage.setScene(sc);
        stage.setTitle("Nouvel Article");
        stage.show();
        
        
    }

    public static void main(String[] args) {
        //launch();
    }

}