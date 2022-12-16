package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tpa2dosdatliautard.projetbdd_s5.Encheres;
import tpa2dosdatliautard.projetbdd_s5.Main;


public class SceneEnchere extends GridPane{
//Attributs
    private Button btPublier;
    private Button btAnnuler;
    private ComboBox cbArticle;
    private TextField txtf_nomArticle;
    private TextField txtf_prix;
    private TextField txtf_datefin;
    private Label label_article;
    private Label label_prix;
    private Label label_datefin;
    private Spinner sp_prix;
    private int prixmax;
    private DatePicker dtp_datefin;
    
    //Constructeur
    public SceneEnchere(String mail) {
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
        this.btPublier = new Button("Publier l'enchère");
        btPublier.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.btAnnuler = new Button("Annuler");
        btAnnuler.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.cbArticle = new ComboBox();
//        cbCategorie.getItems().add("categorie 1");
//        cbCategorie.getItems().add("categorie 2");
//        cbCategorie.getItems().add("categorie 3");
        this.txtf_prix = new TextField();
        this.txtf_datefin = new TextField();
        this.txtf_nomArticle = new TextField();
        this.label_article = new Label("Article à mettre en vente");
        this.label_prix = new Label("Prix");
        this.label_datefin = new Label("Date de fin de l'enchère");
        this.sp_prix = new Spinner<Integer>();
        this.prixmax=99999;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, prixmax, 1);//min,max,valeurinitiale
        sp_prix.setValueFactory(valueFactory);
        sp_prix.setEditable(true);



        //Localisation des composants sur le GridPane 
        this.add(label_article,1,1);
        this.add(txtf_nomArticle,1,2);
        this.add(label_prix,1,4);
        this.add(sp_prix,1,5);
        this.add(label_datefin,1,7);
        this.add(txtf_datefin,1,8);
        this.add(btPublier,1,10);
        this.add(btAnnuler,1,12);

        
        //Gestion des événements
        //this.btPublier.setOnAction((event) -> {this.CreerEnchere(mail);}); 
        this.btAnnuler.setOnAction((event) -> {this.fermerStage(mail);});
        this.btPublier.setOnAction((event) -> {this.CreerEnchere(mail);});
    }
    
    public void fermerStage(String mail){
        //reouverture menu
        Stage StageMenu = new Stage();
        MenuPrincipal menu = new MenuPrincipal();
        menu.start(StageMenu,mail);
        StageMenu.show();
        //fermeture fenetre nouvel article
        Stage stage = (Stage) btAnnuler.getScene().getWindow();
        stage.close();
    }
    
    //méthode appelée lorsque btInscription est cliqué 
    public void CreerEnchere(String mail){
        String nomArticle = txtf_nomArticle.getText();
        int prix = (int) sp_prix.getValue();
        String dateDebut = "2022-12-16";
        Date myDate = null; 
        String datefin = (String) txtf_datefin.getText();
        if(nomArticle.equals("")||datefin.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Toutes les données doivent être renseignées!");       
            alert.showAndWait();
        }
        else{ //Si aucun champ vide           
            try(Connection con = Main.defautConnect()){
                int idArticle = 1;
                Encheres.creerEnchere(con,mail,dateDebut,datefin,idArticle,prix);
                fermerStage(mail);
                //System.out.println("connexion à la base de données réussie");
            }
            catch(ClassNotFoundException | SQLException ex){
                System.out.println("la connexion à la base de données a échoué");
            }

        }
            
    }
    
}
