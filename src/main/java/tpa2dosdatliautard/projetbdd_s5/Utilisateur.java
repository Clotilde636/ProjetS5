package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.lang.*;

public class Utilisateur extends Personne {
//Attributs
    private int nombreEncheres;
            
//Constructeur
    public Utilisateur (String email,String pass,String nom,String prenom, int codePostal,int nombreEncheres){
        super(email,pass,nom,prenom,codePostal); //appelle le constructeur de la superclasse Personne
        this.nombreEncheres=nombreEncheres; //rajout de paramètres par rapport à la superclasse Personne
    }
    
//Méthodes Get Set
    //Exemple pour appeler les attributs de utilisateur déjà dans la superclasse Personne: super.get_nom
    public int get_nombreEncheres(){
        return this.nombreEncheres;
    } 
     public void set_nombreEncheres(int newNombreEncheres) {
        this.nombreEncheres = newNombreEncheres;
    }
    
}
