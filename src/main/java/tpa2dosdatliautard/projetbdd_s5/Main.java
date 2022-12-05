/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpa2dosdatliautard.projetbdd_s5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static tpa2dosdatliautard.projetbdd_s5.Encheres.creerEnchere;
import static tpa2dosdatliautard.projetbdd_s5.Encheres.recreeTout;
import static tpa2dosdatliautard.projetbdd_s5.Personne.creerPersonne;
import static tpa2dosdatliautard.projetbdd_s5.Article.creerArticle;
import static tpa2dosdatliautard.projetbdd_s5.Utilisateur.EmailExisteDejaException;
import static tpa2dosdatliautard.projetbdd_s5.Utilisateur.tousLesUtilisateurs;
import static tpa2dosdatliautard.projetbdd_s5.Administrateur.DeleteArticle;
import static tpa2dosdatliautard.projetbdd_s5.Administrateur.DeleteArticleParDeleteUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Administrateur.DeleteEnchere;
import static tpa2dosdatliautard.projetbdd_s5.Administrateur.DeleteEnchereParDeleteArticle;
import static tpa2dosdatliautard.projetbdd_s5.Administrateur.DeleteEnchereParDeleteUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Administrateur.DeleteUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Article.ProfilArticleUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Article.touslesArticles;
import static tpa2dosdatliautard.projetbdd_s5.Encheres.ProfilEncheresUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Encheres.initialisationEtatEnchere;
import static tpa2dosdatliautard.projetbdd_s5.Encheres.touteslesEncheres;
import static tpa2dosdatliautard.projetbdd_s5.Personne.login;
import static tpa2dosdatliautard.projetbdd_s5.Utilisateur.DeleteEnchereParUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Utilisateur.ProfilUtilisateur;
import static tpa2dosdatliautard.projetbdd_s5.Utilisateur.afficheTousLesUtilisateur;

/**
 *
 * @author cloti
 */
public class Main {
    
    public static Connection connectGeneralPostGres(String host,
            int port, String database,
            String user, String pass)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public static Connection defautConnect()
            throws ClassNotFoundException, SQLException {
        return connectGeneralPostGres("localhost", 5439, "postgres", "postgres", "pass");
    }
    
    public static void menuSimple (Connection con) throws SQLException, EmailExisteDejaException{
    int rep = -1;
        while (rep != 0) {
            System.out.println("Menu BdD Enchere Complet");
            System.out.println("=============");
            System.out.println("1) supprimer/ recréer la BdD initiale");
            System.out.println("2) liste des utilisateurs");
            System.out.println("3) liste des articles");
            System.out.println("4) liste des enchères");
            System.out.println("5) ajouter un utilisateur");
            System.out.println("6) ajouter un utilisateur aléatoire");
            System.out.println("7) ajouter un article");
            System.out.println("8) ajouter une enchère");
            System.out.println("9) delete un utilisateur");
            System.out.println("10) delete un article");
            System.out.println("11) delete une enchère");
            System.out.println("0) quitter");
            rep = Lire.i();
            try {
                if (rep == 1) {
                    recreeTout(con);
                } else if (rep == 2) {
                    tousLesUtilisateurs(con);
                } else if (rep == 3) {
                    //tousLesArticles(con);
                } else if (rep == 4) {
                    //touteLesEnchères(con);
                } else if (rep == 5) {
                    System.out.println("Dans l'ordre, entrer : email, pass, nom, prenom, codepostal, ville");
                    String email = Lire.S();
                    String pass = Lire.S();
                    String nom = Lire.S();
                    String prenom = Lire.S();
                    String codepostal = Lire.S();
                    String ville = Lire.S();
                    creerPersonne(con,email,pass,nom,prenom,codepostal,ville);
                } else if (rep == 6) {
                    System.out.println("création d'utilisateurs 'aléatoires'");
                    System.out.println("combien d'utilisateur : ");
                    int combien = Lire.i();
                    for (int i = 0; i < combien; i++) {
                        boolean exist = true;
                        while (exist) {
                            String email = "U" + ((int) (Math.random() * 10000)) + "@mail.fr";
                            String nom = "Luc"+i;
                            String prenom = "Insa"+i;
                            String codePostal = "67000";
                            String ville = "Strasbourg";
                            try{
                                creerPersonne(con, email, "P" + ((int) (Math.random() * 10000)), nom, prenom, codePostal, ville);
                                exist = false;
                            }
                            catch (EmailExisteDejaException ex) {
                            }
                        }
                    }  
                } else if (rep == 7) {
                    System.out.println("Dans l'ordre, entrer : email, description C, description L, idCatégorie(1:meuble, 2:décoration ... autres chiffre: autre");
                    String email = Lire.S();
                    String descriptionC = Lire.S();
                    String descriptionL = Lire.S();
                    int idCategorie = Lire.i();
                    creerArticle(con, email, descriptionC, descriptionL, idCategorie);
                } else if (rep == 8) {
                    System.out.println("Dans l'ordre, entrer : email, datedebut (AAAA-MM-JJ), datefin (AAAA-MM-JJ), idArticle, prix initial");
                    String email = Lire.S();
                    String datedebut = Lire.S();
                    String datefin = Lire.S();
                    int idArticle = Lire.i();
                    int etatenchere = initialisationEtatEnchere(datedebut);
                    float prix = Lire.f();
                    creerEnchere(con, email, datedebut, datefin, idArticle, prix); 
                } else if (rep == 9) {
                    afficheTousLesUtilisateur(con);
                    System.out.println ("Email Utilisateur :");
                    String email = Lire.S();
                    try{
                        DeleteEnchereParDeleteUtilisateur (con, email);
                        System.out.println ("Enchere de l'Utilisateur "+email+" supprimée");
                    }catch (Exception e){
                       continue; 
                    }
                    try{
                        DeleteArticleParDeleteUtilisateur (con, email);
                        System.out.println ("Article de l'Utilisateur "+email+" supprimé");
                    }catch (Exception e){
                       continue; 
                    }
                    DeleteUtilisateur(con, email);
                    System.out.println ("Utilisateur "+email+" supprimé");   
                } else if (rep == 10) {
                    touslesArticles(con);
                    System.out.println ("idArticle");
                    int id = Lire.i();
                    try{
                        DeleteEnchereParDeleteArticle (con, id);
                        System.out.println ("Enchere de l'Article "+id+" supprimée");
                    }catch (Exception e){
                       continue; 
                    }
                    DeleteArticle(con, id);  
                    System.out.println ("Article "+id+" supprimé");
                } else if (rep == 11) {
                    touteslesEncheres(con);
                    System.out.println ("idEnchere");
                    int id = Lire.i();
                    DeleteEnchere(con, id);
                    System.out.println ("Ecnhere "+id+" supprimée");
                }
            } catch (SQLException ex) {
                throw new Error(ex);
            }
}
    }
    
    public static void menuUtilisateur (Connection con, String emailUtilisateur) throws SQLException, EmailExisteDejaException{
    int rep = -1;
        while (rep != 0) {
            System.out.println("Menu BdD Enchere U");
            System.out.println("=============");
            System.out.println("");
            System.out.println("Partie Affichage :");
            System.out.println("1) Son Profil (ses informations, ses articles et ses enchères)");
            System.out.println("2) liste des utilisateurs");
            System.out.println("3) liste des articles");
            System.out.println("4) liste des enchères");
            System.out.println("");
            System.out.println("Partie Vendeur (ajouter) :");
            System.out.println("5) ajouter un article");
            System.out.println("6) delete un de ses articles");
            System.out.println("7) ajouter une enchère");
            System.out.println("8) delete une de ses enchères");
            System.out.println("");
            System.out.println("Partie Acheteur :");
            System.out.println("9) Chercher un article par catégorie");
            System.out.println("10) Encherir dans une enchère");
            System.out.println("");
            System.out.println("=============");
            System.out.println("0) Se Déconnecter");
            System.out.println ("");
            rep = Lire.i();
            try {
                if (rep == 1) {
                    System.out.println ("======Profil=======");
                    ProfilUtilisateur(con,emailUtilisateur);
                    System.out.println ("");
                    System.out.println ("======Articles=======");
                    ProfilArticleUtilisateur (con, emailUtilisateur);
                    System.out.println ("");
                    System.out.println ("======Encheres=======");
                    ProfilEncheresUtilisateur(con, emailUtilisateur);
                    System.out.println ("");
                } else if (rep == 2) {
                    tousLesUtilisateurs(con);
                } else if (rep == 3) {
                    touslesArticles(con);
                } else if (rep == 4) {
                    touteslesEncheres(con);
                } else if (rep == 5) {
                    System.out.println("Donnez un nom à votre article");
                    String descriptionC = Lire.S();
                    System.out.println("Donnez une descriprion à votre article");
                    String descriptionL = Lire.S();
                    System.out.println ("======Les Catégories=======");
                    //Afficher les catégories
                    System.out.println("Choisissez une catégorie pour votre article");
                    int idCategorie = Lire.i();
                    creerArticle(con, emailUtilisateur, descriptionC, descriptionL, idCategorie);
                } else if (rep == 6) {
                    System.out.println ("======Vos Articles=======");
                    ProfilArticleUtilisateur (con, emailUtilisateur);
                    System.out.println("Choisissez un article que vous voulez supprimer (sélectionner son numéro)");
                    int idArticle = Lire.i();
                    try{
                        DeleteEnchereParDeleteArticle (con, idArticle);
                        System.out.println ("Enchere de l'Article "+idArticle+" supprimée");
                    }catch (Exception e){
                       continue; 
                    }
                    DeleteArticle(con, idArticle);  
                    System.out.println ("Article "+idArticle+" supprimé");
                } else if (rep == 7) {
                    System.out.println ("======Vos Articles=======");
                    ProfilArticleUtilisateur (con, emailUtilisateur);
                    System.out.println("Choisissez un article pour votre enchère (sélectionner son numéro)");
                    int idArticle = Lire.i();
                    System.out.println("Donnez une date de début pour votre enchère");
                    String datedebut = Lire.S();
                    int etatenchere = initialisationEtatEnchere(datedebut);
                    System.out.println("Donnez une date de fin pour votre enchère");
                    String datefin = Lire.S();
                    System.out.println("Donnez un prix minimal pour votre enchère");
                    float prix = Lire.f();
                    creerEnchere(con, emailUtilisateur, datedebut, datefin, idArticle, prix);
                } else if (rep == 8) {
                    System.out.println ("======Vos Enchères=======");
                    ProfilEncheresUtilisateur (con, emailUtilisateur);
                    System.out.println("Choisissez l'enchère que vous voulez supprimer (sélectionner son numéro), "
                            + "seulement les enchères en cours ou qui ont pas commencé sont supprimable");
                    int idEnchere = Lire.i();
                    try{
                        DeleteEnchereParUtilisateur(con, idEnchere);
                        System.out.println ("Enchere "+idEnchere+" supprimée");
                    }catch (Exception e){
                        System.out.println ("Enchere "+idEnchere+" terminée, donc non supprimable");
                       continue; 
                    }
                } else if (rep == 9) {
                      
                } else if (rep == 10) {
                    
                }
            } catch (SQLException ex) {
                throw new Error(ex);
            }
}
    }
    
    public static void menuAdministrateur (Connection con, String admin) throws SQLException, EmailExisteDejaException{
    int rep = -1;
        while (rep != 0) {
            System.out.println("Menu BdD Enchere U");
            System.out.println("=============");
            System.out.println("");
            System.out.println("Partie Affichage :");
            System.out.println("1) Son Profil (ses informations, ses articles et ses enchères)");
            System.out.println("2) liste des utilisateurs");
            System.out.println("3) liste des articles");
            System.out.println("4) liste des enchères");
            System.out.println("");
            System.out.println("Partie Vendeur (ajouter) :");
            System.out.println("5) ajouter un article");
            System.out.println("6) ajouter une enchère");
            System.out.println("");
            System.out.println("Partie Acheteur :");
            System.out.println("7) Chercher un article par catégorie");
            System.out.println("8) Encherir dans une enchère");
            System.out.println("");
            System.out.println("Partie Administrateur :");
            System.out.println("9) delete un utilisateur (pas soi-même)");
            System.out.println("10) delete un article");
            System.out.println("11) delete une enchere");
            System.out.println("");
            System.out.println("=============");
            System.out.println("0) Se Déconnecter");
            System.out.println ("");
            rep = Lire.i();
            try {
                if (rep == 1) {
                    System.out.println ("======Profil=======");
                    ProfilUtilisateur(con,admin);
                    System.out.println ("");
                    System.out.println ("======Articles=======");
                    ProfilArticleUtilisateur (con, admin);
                    System.out.println ("");
                    System.out.println ("======Encheres=======");
                    
                    System.out.println ("");
                } else if (rep == 2) {
                    tousLesUtilisateurs(con);
                } else if (rep == 3) {
                    //tousLesArticles(con);
                } else if (rep == 4) {
                    //touteLesEnchères(con);
                } else if (rep == 5) {
                    System.out.println("Dans l'ordre, entrer : description C, description L, idCatégorie(1:meuble, 2:décoration ... autres chiffre: autre");
                    String email = Lire.S();
                    String descriptionC = Lire.S();
                    String descriptionL = Lire.S();
                    int idCategorie = Lire.i();
                    creerArticle(con, email, descriptionC, descriptionL, idCategorie);
                } else if (rep == 6) {
                    System.out.println("Dans l'ordre, entrer : datedebut (AAAA-MM-JJ), datefin (AAAA-MM-JJ), idArticle, prix initial");
                    String datedebut = Lire.S();
                    String datefin = Lire.S();
                    int idArticle = Lire.i();
                    int etatenchere = initialisationEtatEnchere(datedebut);
                    int prix = Lire.s();
                    creerEnchere(con, admin, datedebut, datefin, idArticle, prix); 
                } else if (rep == 10) {
                    //afficheTousLesArticles(con);
                    System.out.println ("idArticle");
                    int id = Lire.i();
                    try{
                        DeleteEnchereParDeleteArticle (con, id);
                        System.out.println ("Enchere de l'Article "+id+" supprimée");
                    }catch (Exception e){
                       continue; 
                    }
                    DeleteArticle(con, id);  
                    System.out.println ("Article "+id+" supprimé");
                } else if (rep == 11) {
                    //afficheToutesLesEncheres(con);
                    System.out.println ("idEnchere");
                    int id = Lire.i();
                    DeleteEnchere(con, id);
                    System.out.println ("Ecnhere "+id+" supprimée");
                }
            } catch (SQLException ex) {
                throw new Error(ex);
            }
}
    }
    
    public static void menuComplet (Connection con) throws EmailExisteDejaException{
    int rep = -1;
        while (rep != 0) {
            System.out.println("");
            System.out.println("======Page Accueil=====");
            System.out.println("1) Login");
            System.out.println("2) Inscription");
            System.out.println("");
            System.out.println("0) Quitter");
            rep = Lire.i();
            try {
                if (rep == 1){
                    System.out.println("Email :");
                    String email = Lire.S();
                    System.out.println("Mot de passe :");
                    String pass = Lire.S();
                    String emailUtilisateur = null;
                    emailUtilisateur = login(con, email, pass);
                    if (emailUtilisateur != null){
                        if (emailUtilisateur == "admin@mail.fr"){
                            String emailAdmin = emailUtilisateur;
                            System.out.println("======BON RETOUR DANS AUCTION ?Admin?======");
                            System.out.println("");
                            menuAdministrateur(con,emailAdmin);
                        }else{
                            System.out.println("======BON RETOUR DANS AUCTION "+emailUtilisateur+"======");
                            System.out.println("");
                            menuUtilisateur(con,emailUtilisateur);
                        }
                    }else{
                        System.out.println("");
                        System.out.println("Email ou Mot de passe Incorrect !!!");
                    }
                }else if (rep == 2){
                    try {
                        System.out.println("Email :");
                        String email = Lire.S();
                        System.out.println("Nom :");
                        String nom = Lire.S();
                        System.out.println("Prenom :");
                        String prenom = Lire.S();
                        System.out.println("Mot de Passe :");
                        String pass = Lire.S();
                        System.out.println("Code Postal :");
                        String codePostal = Lire.S();
                        System.out.println("Ville :");
                        String ville = Lire.S();
                        creerPersonne (con,email,pass,nom,prenom, codePostal, ville);
                        System.out.println("Création Réussie !");
                        System.out.println("======BIENVENUE DANS AUCTION "+email+"======");
                        System.out.println("");
                        menuUtilisateur(con,email);
                    }catch(EmailExisteDejaException ex){
                        System.out.println("Email déjà utilisé !");
                        continue;
                    }
                }
            }catch(SQLException ex){
                throw new Error(ex);
            }
        }
    }
    
    public static void main(String[] args) {
        try ( Connection con = defautConnect()) {
            System.out.println("connecté !!!");
            System.out.println("");
            //menuSimple(con);
            menuComplet(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }
}
