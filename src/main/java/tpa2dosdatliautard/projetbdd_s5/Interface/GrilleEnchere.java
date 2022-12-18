
package tpa2dosdatliautard.projetbdd_s5.Interface;

import java.sql.Connection;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import tpa2dosdatliautard.projetbdd_s5.Encheres;
import tpa2dosdatliautard.projetbdd_s5.Main;


public class GrilleEnchere extends GridPane{
    
    private String mail;
    private TableView table_encheres;
    
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
        
    //Instanciation TableView
        this.table_encheres = new TableView();
        TableColumn <Encheres,String> colonne_nom = new TableColumn("Nom (id Article)");
        TableColumn <Encheres,String> colonne_description = new TableColumn("Description");
        TableColumn <Encheres, String> colonne_prix = new TableColumn("Prix (Euro)");      
        TableColumn <Encheres,String> colonne_dateDebut = new TableColumn("Date début");
        TableColumn <Encheres,String> colonne_dateFin = new TableColumn("Date fin");
        table_encheres.getColumns().addAll(colonne_nom,colonne_prix,colonne_dateDebut,colonne_dateFin);
        colonne_nom.prefWidthProperty().bind(table_encheres.widthProperty().multiply(0.2));
        colonne_description.prefWidthProperty().bind(table_encheres.widthProperty().multiply(0.35));
        colonne_prix.prefWidthProperty().bind(table_encheres.widthProperty().multiply(0.15));
        colonne_dateDebut.prefWidthProperty().bind(table_encheres.widthProperty().multiply(0.15));
        colonne_dateFin.prefWidthProperty().bind(table_encheres.widthProperty().multiply(0.15));
        //Instructions pour remplir les cases du tableau
        colonne_nom.setCellValueFactory(new PropertyValueFactory<>("idArticle"));//Attention à bien mettre le nom exact des attributs depuis la classe Encheres
        //colonne_nom.setCellValueFactory(new PropertyValueFactory<>("nomArticle"));
        //colonne_description.setCellValueFactory(new PropertyValueFactory<>("descriptionArticle"));
        colonne_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colonne_dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colonne_dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        //Afficher les données de la liste liste_articles 
        ObservableList<Encheres> obslist_encheres = getAllEncheresUtilisateur(mail);
        table_encheres.setItems(obslist_encheres);
    //Instanciation Divers
        Button btCreerEnchere = new Button("Nouvelle enchère");
        btCreerEnchere.setMaxHeight(Double.MAX_VALUE);
        btCreerEnchere.setMaxWidth(Double.MAX_VALUE);
        Button btDeleteEnchere = new Button("Supprimer l'enchère sélectionnée");
        btDeleteEnchere.setMaxHeight(Double.MAX_VALUE);
        btDeleteEnchere.setMaxWidth(Double.MAX_VALUE);
        Button btDeleteAllEncheres = new Button("Supprimer toutes mes enchères");
        btDeleteAllEncheres.setMaxHeight(USE_PREF_SIZE);
        btDeleteAllEncheres.setMaxWidth(USE_PREF_SIZE);
        btDeleteAllEncheres.setStyle("-fx-background-color:#ff3333"); //bouton en rouge
    //Localisation des composants sur grilleArticles
        this.add(table_encheres, 1, 1,2,1);
        this.add(btCreerEnchere,1,2);
        this.add(btDeleteEnchere,2,2);
        this.add(btDeleteAllEncheres,1,3,2,1);        
    //Gestion des Evénements
        btCreerEnchere.setOnAction((event) -> {askDataEnchere(mail,btCreerEnchere);});
        btDeleteEnchere.setOnAction((event) -> {deleteSelectedEnchere(mail,btDeleteEnchere);});
        btDeleteAllEncheres.setOnAction((event) -> {this.DeleteAllEncheres(mail,btDeleteAllEncheres);});
    }
    
    //méthode appelée lorsque btCreerEnchere est cliqué
    public void askDataEnchere(String mail,Button bt) {
        Stage StageNewEnchere = new Stage();
        MenuEnchere menuenchere = new MenuEnchere();
        menuenchere.start(StageNewEnchere, mail);
        StageNewEnchere.show();
        Stage stage = (Stage) bt.getScene().getWindow();
        stage.close();
    }
    
    //Fonction qui renvoie la liste des encheres d'un utilisateur
    public ObservableList<Encheres> getAllEncheresUtilisateur(String mail) {
        ObservableList<Encheres> obslist = FXCollections.observableArrayList();
        try(Connection con = Main.defautConnect()){
            //System.out.println("connexion à la base de données réussie");
            obslist = Encheres.GetAllEncheresUtilisateur(con, mail);         
            }
            catch(ClassNotFoundException ex){
                System.out.println(ex);
            }
            catch(SQLException ex){
                System.out.println(ex);
            }
        return obslist;
    }
    
    //méthode appelée lorque btDeleteEnchere est cliqué
    public void deleteSelectedEnchere(String mail,Button bt) {
        Encheres enchere = (Encheres) table_encheres.getSelectionModel().getSelectedItem();
        if(enchere!=null){
            int idEnchere = enchere.getIdEncheres();
            //System.out.println(nomArticle);
            try(Connection con = Main.defautConnect()){
                System.out.println("idEnchere: "+idEnchere);
                Encheres.DeleteEnchere(con, mail, idEnchere);
                //System.out.println("connexion à la base de données réussie");
                Stage stage = (Stage) bt.getScene().getWindow();
                stage.close();
                Stage StageMenu = new Stage();
                MenuPrincipal menu = new MenuPrincipal();
                menu.start(StageMenu,mail);
                StageMenu.show();
                }
            catch(ClassNotFoundException | SQLException ex){
                System.out.println("erreur dans GrilleEnchere.deleteSelectedEnchere");
                System.out.println(ex);
            }
        }
        else{
            //System.out.println("aucune sélection");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune enchère n'a été sélectionnée!");       
            alert.showAndWait();
        }
    }
    
    //méthode appelée lorque le bouton "DeleteAllArticles" est cliqué
    public void DeleteAllEncheres(String mail,Button bt){
        try(Connection con = Main.defautConnect()){
            Encheres.DeleteAllEncheres(con, mail);
            //System.out.println("connexion à la base de données réussie");
            Stage stage = (Stage) bt.getScene().getWindow();
            stage.close();
            Stage StageMenu = new Stage();
            MenuPrincipal menu = new MenuPrincipal();
            menu.start(StageMenu,mail);
            StageMenu.show();
            }
            catch(ClassNotFoundException | SQLException ex){
                System.out.println("erreur dans GrilleEnchere.DeleteAllEncheres");
                System.out.println(ex);
                
            }
    }
    
}
