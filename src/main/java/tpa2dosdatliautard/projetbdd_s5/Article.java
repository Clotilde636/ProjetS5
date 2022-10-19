package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.lang.*;


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
    public int get_categorie(){
        return this.categorie;
    } 
    public String get_description_C(){
        return this.description_C;
    } 
    public String get_description_D(){
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
    
}
