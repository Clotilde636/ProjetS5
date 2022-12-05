package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.lang.*;

// test push Emilien

public class Administrateur extends Personne {
//Attributs
    private int randomVariable; //modèle à suivre pour rajouter des paramètres pour Administrateur, en plus de celles déjà créées par la superclasse Personne
            
//Constructeur
    public Administrateur (String email,String pass,int randomValue){
        super(email,pass); //appelle le constructeur de la superclasse Personne
        this.randomVariable=randomValue; //rajout de paramètres par rapport à la superclasse Personne
    }
    
//Méthodes Get Set
    //Exemple pour appeler les attributs de utilisateur déjà dans la superclasse Personne: super.get_nom
    public int get_randomVariable(){
        return this.randomVariable;
    } 
     public void set_nombreEncheres(int newRandomVariable) {
        this.randomVariable = newRandomVariable;
    }
     
    public static void DeleteUtilisateur (Connection con, String email) throws SQLException{ 
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from personne where email = ?")) {
                pst.setString(1, email);
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
    
    public static void DeleteArticle (Connection con, int idArticle) throws SQLException{
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from articles where idArticle = ?")) {
                pst.setInt(1, idArticle);
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
    
    public static void DeleteArticleParDeleteUtilisateur (Connection con, String email) throws SQLException{
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from articles where emailVendeur = ?")) {
                pst.setString(1, email);
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
    
    public static void DeleteEnchere (Connection con, int idEnchere) throws SQLException{  
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from encheres where idEnchere = ?")) {
                pst.setInt(1, idEnchere);
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
    
    public static void DeleteEnchereParDeleteArticle (Connection con, int idArticle) throws SQLException{  
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from encheres where idArticle = ?")) {
                pst.setInt(1, idArticle);
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
    
    public static void DeleteEnchereParDeleteUtilisateur (Connection con, String email) throws SQLException{
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from encheres where emailVendeur = ?")) {
                pst.setString(1, email);
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
