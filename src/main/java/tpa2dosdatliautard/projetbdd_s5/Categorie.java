/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpa2dosdatliautard.projetbdd_s5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cloti
 */
public class Categorie {
    // Attribut
    private String def;
    
    //Constructeur (Catégorie de base)
    public Categorie (String nom){
        this.def= nom;
    }
    
    //Créer un tableau dans BDD avec les catégories pour pouvoir en ajouter et que cela soit stocké et updaté en permanence
    public void Afficher (){
       
    }
    
    //Méthode Get Set
    public String get_listeCategorie(){
        return this.def;
    } 
    
    public void set_dateDebut(String nom) {
        this.def = nom;
    }
    
    //Afficher les catégories
    public static List<Categorie> touteslesCategories (Connection con) throws SQLException {
        List<Categorie> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idCategorie, categorie
                 from categories
                 order by idCategorie asc
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Categorie (rs.getString("categorie")));
                    System.out.println(rs.getInt("idCategorie")+" : "+rs.getString("categorie"));
                }
                System.out.println("============================");
                return res;
            }
        }
    }
    
    //Ajouter une catégorie
    public static int AjouterUneCategorie (Connection con, String newCategorie) throws SQLException {
        con.setAutoCommit(false);
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into categories (categorie) values (?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, newCategorie);
                pst.executeUpdate();
                con.commit();
                try ( ResultSet rid = pst.getGeneratedKeys()) {
                    rid.next();
                    int id = rid.getInt(1);
                    return id;
            }    
        }
    }     
    
    //Rechercher la catégorie par rapport à son numéro
    public static String IdentifierCategorieArticle (Connection con, int idCategorie) throws SQLException {
        String res ="";
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from categories where idCategorie = ? "
        )) {
            pst.setInt(1, idCategorie);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    System.out.println("=========Categorie==========");
                    res = rs.getString("categorie");
                    System.out.println(rs.getString("categorie"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }     
    
    //Rechercher la catégorie par rapport à son nom
    public static int IdentifierNumeroCategorieArticle (Connection con, String Categorie) throws SQLException {
        int res = 1;
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from categories where categorie = ? "
        )) {
            pst.setString(1, Categorie);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    System.out.println("=========Indentifiant de la Categorie==========");
                    res = rs.getInt("idCategorie");
                    System.out.println(rs.getInt("idCategorie"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    } 
    
    //Retirer une catégorie 
    public static void ModifierUneCategorie (Connection con, int idCategorie) throws SQLException {
    con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "update from categories where idCategorie = ?")) {
                pst.setInt(1, idCategorie);
                pst.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    } 
}
