package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tpa2dosdatliautard.projetbdd_s5.Article;
import static tpa2dosdatliautard.projetbdd_s5.Article.getIDarticle;
import tpa2dosdatliautard.projetbdd_s5.Encheres;
import tpa2dosdatliautard.projetbdd_s5.Main;


public class SceneEnchere extends GridPane{
    
//Attributs
    private Button btPublier;
    private Button btAnnuler;
    private ComboBox cbArticle;
    private ChoiceBox chbArticles;
    private TextField txtf_nomArticle;
    private TextField txtf_prix;
    private TextField txtf_datefin;
    private Label label_article;
    private Label label_prix;
    private Label label_datefin;
    private Spinner sp_prix;
    private double prixmax;
    private DatePicker dtp_datefin;
    private Label labelvide;
    
    //Constructeur
    public SceneEnchere(String mail) {
        //Mise en forme du GridPane
        ColumnConstraints col_gauche = new ColumnConstraints();
        col_gauche.setPercentWidth(10);
        this.getColumnConstraints().add(col_gauche);
        ColumnConstraints colonnePrincipaleGauche = new ColumnConstraints();
        colonnePrincipaleGauche.setPercentWidth(45);
        this.getColumnConstraints().add(colonnePrincipaleGauche);
        ColumnConstraints colonnePrincipaleDroite = new ColumnConstraints();
        colonnePrincipaleDroite.setPercentWidth(45);
        this.getColumnConstraints().add(colonnePrincipaleDroite);
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
        this.dtp_datefin = new DatePicker();
        dtp_datefin.setValue(LocalDate.of(2022, 1, 1));
        this.chbArticles = new ChoiceBox();
        //recuperation des noms des articles
        try(Connection con = Main.defautConnect()){
            ArrayList<String> listeNomArticles = Article.GetAllNomArticlesUtilisateur (con,mail);
            for(int i =0;i<listeNomArticles.size();i++){
                //System.out.println(i+" : nom : "+listeNomArticles.get(i));
                chbArticles.getItems().add(listeNomArticles.get(i));
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println(ex);
        }
        catch(SQLException ex){
           System.out.println(ex);
        }
        
//        cbCategorie.getItems().add("categorie 1");
//        cbCategorie.getItems().add("categorie 2");
//        cbCategorie.getItems().add("categorie 3");
        this.txtf_prix = new TextField();
        this.txtf_datefin = new TextField();
        this.txtf_nomArticle = new TextField();
        this.label_article = new Label("Article");
        this.label_prix = new Label("Prix (Euro)");
        this.label_datefin = new Label("Date de fin de l'enchère");
        this.labelvide = new Label(" ");
        this.sp_prix = new Spinner<Double>();
        this.prixmax=99999.99;
        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, prixmax, 1);//min,max,valeurinitiale
        sp_prix.setValueFactory(valueFactory);
        sp_prix.setEditable(true);



        //Localisation des composants sur le GridPane 
        this.add(label_article,1,1);
        this.add(label_prix,2,1);
        GridPane.setHalignment(label_article, HPos.CENTER);
        GridPane.setHalignment(label_prix, HPos.CENTER);
        this.add(chbArticles,1,2);        
        this.add(sp_prix,2,2);
        this.add(label_datefin,1,4,2,1);
        this.add(dtp_datefin,1,5,2,1);
        this.add(labelvide,1,6);
        this.add(btPublier,1,7,2,1);
        this.add(btAnnuler,1,8,2,1);

        
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
        //String nomArticle = txtf_nomArticle.getText();
        String nomArticle = (chbArticles.getValue()).toString();
        double d_prix = (double) sp_prix.getValue();
        float f_prix = (float) d_prix;
        String dateDebut = "2022-12-18";
        //String datefin = (String) txtf_datefin.getText();
//        LocalDate ld_datefin = dtp_datefin.getValue();
//        String str_datefin = ld_datefin.toString();
        String datefin = dtp_datefin.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("datefin retrieved = "+datefin);
        if(nomArticle.equals("")||datefin.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Toutes les données doivent être renseignées!");       
            alert.showAndWait();
        }
        else{ //Si aucun champ vide           
            try(Connection con = Main.defautConnect()){
                int idArticle = getIDarticle(con,mail,nomArticle);
                System.out.println("idArticle retrieved = "+idArticle);
                Encheres.creerEnchere(con,mail,dateDebut,datefin,idArticle,f_prix);
                fermerStage(mail);
                //System.out.println("connexion à la base de données réussie");
            }
            catch(ClassNotFoundException ex){
                System.out.println("ClassNotFoundException dans Enchere.CreerEnchere");
            }
            catch(SQLException ex){
                System.out.println("SQLException dans Enchere.CreerEnchere");
                System.out.println(ex);
            }

        }
            
    }
    
}
