package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.lang.*;


public class Personne {
//Attributs
    public final String email; //final=>variable non modifiable une fois initialisée
    private String pass;
    private String nom;
    private String prenom;
    private int codePostal;
    
//Constructeur
    public Personne(String email,String pass,String nom,String prenom, int codePostal){
        this.email=email;
        this.pass=pass;
        this.nom=nom;
        this.prenom=prenom;
        this.codePostal=codePostal; 
    }
    
//Méthodes Get Set
    //pas de méthode get set pour email, car public et final
    public String get_pass(){
        return this.pass;
    } 
    public String get_nom(){
        return this.nom;
    } 
    public String get_prenom(){
        return this.prenom;
    } 
    public int get_codePostal(){
        return this.codePostal;
    } 
    public void set_pass(String newPass) {
        this.nom = newPass;
    }
    public void set_nom(String newNom) {
        this.nom = newNom;
    }
     public void set_prenom(String newPrenom) {
        this.prenom = newPrenom;
    }
     public void set_description_D(int newCodePostal) {
        this.codePostal = newCodePostal;
    }
    
}
