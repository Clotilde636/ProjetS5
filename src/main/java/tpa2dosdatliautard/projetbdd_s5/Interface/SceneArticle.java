
package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tpa2dosdatliautard.projetbdd_s5.Article;
import tpa2dosdatliautard.projetbdd_s5.Main;
import tpa2dosdatliautard.projetbdd_s5.Utilisateur;


public class SceneArticle extends GridPane{
//Attributs
    private Button btAjouter;
    private Button btAnnuler;
    private ComboBox cbCategorie;
    private TextField txtf_nom;
    private TextArea txta_description;
    private Label label_categorie;
    private Label label_nom;
    private Label label_description;
    
    //Constructeur
    public SceneArticle(String mail) {
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
        this.btAjouter = new Button("Ajouter à mes articles");
        btAjouter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.btAnnuler = new Button("Annuler");
        btAnnuler.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.cbCategorie = new ComboBox();
        cbCategorie.getItems().add("categorie 1");
        cbCategorie.getItems().add("categorie 2");
        cbCategorie.getItems().add("categorie 3");
        this.txtf_nom = new TextField();        
        this.txta_description = new TextArea("");
        this.label_nom = new Label("Nom de l'article");
        this.label_categorie = new Label("catégorie");
        this.label_description = new Label("Description");

        //Localisation des composants sur le GridPane 
        this.add(label_nom,1,1);
        this.add(txtf_nom,1,2);
        this.add(label_categorie,1,4);
        this.add(cbCategorie,1,5);
        this.add(label_description,1,7);
        this.add(txta_description,1,8,1,4);
        this.add(btAjouter,1,13);
        this.add(btAnnuler,1,15);

        
        //Gestion des événements
        this.btAjouter.setOnAction((event) -> {this.CreerArticle(mail);}); 
        this.btAnnuler.setOnAction((event) -> {this.fermerStage();});
    }
    
    public void fermerStage(){
        Stage stage = (Stage) btAnnuler.getScene().getWindow();
        stage.close();
    }
    
    //méthode appelée lorsque btInscription est cliqué 
    public void CreerArticle(String mail){
        String nom = this.txtf_nom.getText();
        String description = this.txta_description.getText();
        String combobox_value = (String) cbCategorie.getValue();
        int idcategorie = -1; //valeur par défaut
        if(nom.equals("")||combobox_value==null||description.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Toutes les données doivent être renseignées!");       
            alert.showAndWait();
        }
        else{ //Si aucun champ vide
            if(combobox_value.equals("categorie 1")){
                idcategorie = 1;
            }
            else if(combobox_value.equals("categorie 2")){
                idcategorie = 2;
            }
            else if(combobox_value.equals("categorie 3")){
                idcategorie = 3;
            }
            else{
                System.out.println("erreur lecture ComboBox cbCategorie");
            }
            try(Connection con = Main.defautConnect()){               
                Article.creerArticle(con, mail, nom, description, idcategorie);
                Stage stage = (Stage) btAjouter.getScene().getWindow();
                stage.close();
                //System.out.println("connexion à la base de données réussie");
            }
            catch(ClassNotFoundException | SQLException ex){
                System.out.println("la connexion à la base de données a échoué");
            }

        }
            
    }
    
}
