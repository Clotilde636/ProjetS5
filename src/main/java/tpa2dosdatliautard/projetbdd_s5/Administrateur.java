package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.lang.*;

public class Administrateur extends Personne {
//Attributs
    private int randomVariable; //modèle à suivre pour rajouter des paramètres pour Administrateur, en plus de celles déjà créées par la superclasse Personne
            
//Constructeur
    public Administrateur (String email,String pass,String nom,String prenom, int codePostal,int randomValue){
        super(email,pass,nom,prenom,codePostal); //appelle le constructeur de la superclasse Personne
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
    
}
