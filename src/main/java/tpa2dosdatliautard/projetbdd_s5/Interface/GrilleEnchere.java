
package tpa2dosdatliautard.projetbdd_s5.Interface;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


public class GrilleEnchere extends GridPane{
    
    private String mail;
    
    public GrilleEnchere(String mail){
        //Mise en forme du GridPane     
        //colonnes
        ColumnConstraints marge_gauche = new ColumnConstraints();
        marge_gauche.setPercentWidth(10);
        this.getColumnConstraints().add(marge_gauche);
        ColumnConstraints col_principale1 = new ColumnConstraints();
        col_principale1.setPercentWidth(45);
        this.getColumnConstraints().add(col_principale1);
        ColumnConstraints col_principale2 = new ColumnConstraints();
        col_principale2.setPercentWidth(45);
        this.getColumnConstraints().add(col_principale2);
        ColumnConstraints marge_droite = new ColumnConstraints();
        marge_droite.setPercentWidth(10);
        this.getColumnConstraints().add(marge_droite);
        //lignes
        RowConstraints bord_sup = new RowConstraints();
        bord_sup.setPercentHeight(5);
        this.getRowConstraints().add(bord_sup);
        RowConstraints row_table = new RowConstraints();
        row_table.setPercentHeight(70);
        this.getRowConstraints().add(row_table);
        RowConstraints row_AddOrDeleteArticle = new RowConstraints();
        row_AddOrDeleteArticle.setPercentHeight(10);
        this.getRowConstraints().add(row_AddOrDeleteArticle);
        RowConstraints row_deleteall = new RowConstraints();
        row_deleteall.setPercentHeight(10);
        this.getRowConstraints().add(row_deleteall);
        RowConstraints bord_inf = new RowConstraints();
        bord_inf.setPercentHeight(5);
        this.getRowConstraints().add(bord_inf);
        //marges
        this.setVgap(10);
        this.setHgap(10);
        //Instanciation Divers
        Button btCreerEnchere = new Button("Mettre un article en enchère");
        btCreerEnchere.setMaxHeight(Double.MAX_VALUE);
        btCreerEnchere.setMaxWidth(Double.MAX_VALUE);
        Button btDeleteEnchere = new Button("Supprimer l'enchère sélectionnée");
        btDeleteEnchere.setMaxHeight(Double.MAX_VALUE);
        btDeleteEnchere.setMaxWidth(Double.MAX_VALUE);
        //Localisation des composants sur grilleArticles
        this.add(btCreerEnchere,1,2);
        this.add(btDeleteEnchere,2,2);
        //Gestion des Evénements
        btCreerEnchere.setOnAction((event) -> {askDataEnchere(mail,btCreerEnchere);});
        btDeleteEnchere.setOnAction((event) -> {deleteSelectedEnchere(mail,btDeleteEnchere);});
        
    }
    
    //méthode appelée lorque btCreerEnchere est cliqué
    public void askDataEnchere(String mail,Button bt) {
        Stage StageNewEnchere = new Stage();
        MenuEnchere menuenchere = new MenuEnchere();
        menuenchere.start(StageNewEnchere, mail);
        StageNewEnchere.show();
        Stage stage = (Stage) bt.getScene().getWindow();
        stage.close();
    } 
    
    //méthode appelée lorque btDeleteEnchere est cliqué
    public void deleteSelectedEnchere(String mail,Button bt) {

    } 
    
}
