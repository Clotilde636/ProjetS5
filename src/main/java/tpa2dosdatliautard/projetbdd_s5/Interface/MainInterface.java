
package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author francois
 */
public class MainInterface extends Application {

    @Override
    public void start(Stage stage) {
        Scene sc = new Scene(new SceneLogin());  //Heritage des propriétés de la classe SceneDefaut
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setScene(sc);
        stage.setTitle("Login");
        stage.show();
        
        
    }

    public static void main(String[] args) {
        launch();
    }

}