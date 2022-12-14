package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static tpa2dosdatliautard.projetbdd_s5.Categorie.IdentifierCategorieArticle;

public class Article {
//Attributs
    public final int idArticle; //final=>variable non modifiable une fois initialisée
    private int categorie;
    private String description_C; //Description courte
    private String description_D; // Description détaillée
    
//Constructeur
    public Article(int id, int categorie, String D_C, String D_D){
        this.idArticle=id;
        this.categorie=categorie;
        this.description_C=D_C;
        this.description_D=D_D; 
    }
    
//Méthodes Get Set
    //pas de méthode get set pour idArticle, car public et final
    public int getCategorie(){//pour que PropertyValueFactory fonctionne, il faut que les get/set respectent strictement la syntaxe: getAttribut 
        return this.categorie;  //par exemple, get_categorie et getcategorie ne fonctionne pas ! ,mut$l^-or-'è=o)etyàietykp$ùmlm
    } 
    public String getDescription_C(){
        return this.description_C;
    } 
    public String getDescription_D(){
        return this.description_D;
    } 
    public void set_categorie(int newCategorie) {
        this.categorie = newCategorie;
    }
     public void set_description_C(String newD_C) {
        this.description_C = newD_C;
    }
     public void set_description_D(String newD_D) {
        this.description_D = newD_D;
    }
    
    public static int creerArticle (Connection con, String email, String descriptionC, String descriptionL, int idCategorie)
        throws SQLException {
        con.setAutoCommit(false);
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into articles (descriptionC, descriptionL, idCategorie, emailVendeur) values (?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, descriptionC);
                pst.setString(2, descriptionL);
                pst.setInt(3, idCategorie);
                pst.setString(4, email);
                pst.executeUpdate();
                con.commit();
                try ( ResultSet rid = pst.getGeneratedKeys()) {
                    rid.next();
                    int id = rid.getInt(1);
                    return id;
            }    
        }
    }
    
    public static void DeleteAllArticles (Connection con,String mail) throws SQLException {
        con.setAutoCommit(false);
        try{
            PreparedStatement pst = con.prepareStatement("DELETE from articles WHERE emailVendeur = ?");
            pst.setString(1,mail);
            pst.executeUpdate();
            con.commit();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }   
    }
    
    public static void DeleteArticle (Connection con,String mail, String nomArticle) throws SQLException {
        con.setAutoCommit(false);
        try{
            PreparedStatement pst = con.prepareStatement("DELETE from articles WHERE emailVendeur = ? and descriptionc = ?");
            pst.setString(1,mail);
            pst.setString(2,nomArticle);
            pst.executeUpdate();
            con.commit();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }   
    }
    
    public static List<Article> touslesArticles (Connection con) throws SQLException {
        List<Article> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idArticle, idCategorie, descriptionC, descriptionL, emailVendeur
                 from encheres
                 order by descriptionC asc
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                System.out.println("liste des utilisateurs :");
                System.out.println("------------------------");
                while (rs.next()) {
                    res.add(new Article (rs.getInt("idArticle"),rs.getInt("idCategorie"),
                        rs.getString("descriptionC"), rs.getString("DescriptionL")));
                    System.out.println(rs.getInt("idArticle")+ " : "+ IdentifierCategorieArticle(con,rs.getInt("idArticle")));
                    System.out.println("Posseceur: "+rs.getString("descriptionC"));
                    System.out.println("Nom : "+rs.getString("descriptionC"));
                    System.out.println("Description : "+rs.getString("descriptionL"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
   
    public static List<Article> ProfilArticleUtilisateur (Connection con, String email) throws SQLException {
        List<Article> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from articles where emailVendeur = ? "
        )) {
            pst.setString(1, email);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Article (rs.getInt("idArticle"),rs.getInt("idCategorie"),
                            rs.getString("descriptionC"), rs.getString("DescriptionL")));
                    System.out.println(rs.getInt("idArticle")+ " : "+IdentifierCategorieArticle (con,rs.getInt("idArticle")));
                    System.out.println("Nom : "+rs.getString("descriptionC"));
                    System.out.println("Description : "+rs.getString("descriptionL"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    public static ObservableList<Article> GetAllArticlesUtilisateur (Connection con, String email) throws SQLException {
        ObservableList<Article> obslist = FXCollections.observableArrayList();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from articles where emailVendeur = ? "
        )) {
            pst.setString(1, email);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    obslist.add(new Article(rs.getInt("idArticle"),rs.getInt("idCategorie"),
                                    rs.getString("descriptionC"), rs.getString("DescriptionL")));
                }
                return obslist;
            }
        }
    }
    
    
    public static ArrayList<String> GetAllNomArticlesUtilisateur (Connection con, String email) throws SQLException {
        ArrayList<String> listeNomArticles = new ArrayList<String>();
        try ( PreparedStatement pst = con.prepareStatement("select * from articles where emailVendeur = ? ")) {
            pst.setString(1, email);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("descriptionc");
                    listeNomArticles.add(nom);
                }               
            }
        }
        return listeNomArticles;
    }
    
    public static int getIDarticle (Connection con, String email, String nomArticle) throws SQLException {
        int id = 0;
        try ( PreparedStatement pst = con.prepareStatement("SELECT idarticle FROM articles WHERE emailVendeur = ? AND descriptionc = ? ")) {
            pst.setString(1, email);
            pst.setString(2, nomArticle);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);           
            }
        }
        return id;
    }
    
    public static Article ArticleEnchere (Connection con, int idArticle) throws SQLException {
        Article res = null;
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from articles where idArticle = ? "
        )) {
            pst.setInt(1, idArticle);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res = new Article (rs.getInt("idArticle"),rs.getInt("idCategorie"),
                            rs.getString("descriptionC"), rs.getString("DescriptionL"));
                    System.out.println(idArticle+ " : " +IdentifierCategorieArticle(con,rs.getInt("idArticle")));
                    System.out.println("Nom : "+rs.getString("descriptionC"));
                    System.out.println("Description : "+rs.getString("descriptionL"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    public static List<Article> RechercherCategorieArticle (Connection con, int id) throws SQLException {
        List<Article> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from articles where idCategorie = ? "
        )) {
            pst.setInt(1, id);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Article (rs.getInt("idArticle"),rs.getInt("idCategorie"),
                            rs.getString("descriptionC"), rs.getString("DescriptionL")));
                    System.out.println(rs.getInt("idArticle")+ " : "+ IdentifierCategorieArticle(con,rs.getInt("idArticle")));
                    System.out.println("Nom : "+rs.getString("descriptionC"));
                    System.out.println("Description : "+rs.getString("descriptionL"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    public static void UpdateEmailArticle (Connection con, int id, String email){        
    }
    
    public static void UpdateDescriptionCArticle (Connection con, int id, String descriptionC){   
    }
    
    public static void UpdateDescriptionArticle (Connection con, int id, String descriptionL){   
    }
    
    public static void UpdateCatégorieArticle (Connection con, int id, int idCategorie){   
    }   
    
}
