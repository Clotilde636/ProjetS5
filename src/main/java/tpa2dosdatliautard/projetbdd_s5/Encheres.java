/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package tpa2dosdatliautard.projetbdd_s5;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static tpa2dosdatliautard.projetbdd_s5.Article.ArticleEnchere;

/**
 *
 * @author clotilde
 */
public class Encheres {
    
    //Attributs
    public final int idEncheres; //final=>variable non modifiable une fois initialisée
    private String dateDebut;
    private String dateFin;
    private int idArticle;
    private int etatEnchere; // 0:off, 1: on
    //private String email;
    private float prix;
    
    //Constructeur
    public Encheres (int idEncheres, String dateDebut,String dateFin,int idArticle, int etatEnchere, float prix){
        this.idEncheres = idEncheres;
        this.dateDebut=dateDebut;
        this.dateFin=dateFin;
        this.idArticle=idArticle;
        this.etatEnchere=etatEnchere; 
        //this.email = email;
        this.prix = prix;
    }
    
    //Méthodes Get Set 
    public int getIdEncheres(){
        return this.idEncheres;
    } 
    public String getDateDebut(){
        return this.dateDebut;
    } 
    public String getDateFin(){
        return this.dateFin;
    } 
    public int getIdArticle(){
        return this.idArticle;
    } 
    public int getEtatEnchere(){
        return this.etatEnchere;
    } 
    public float getPrix(){
        return this.prix;
    } 
    //public String get_email(){
    //    return this.email;
    //} 
    
    public void set_dateDebut(String newDate) {
        this.dateDebut = newDate;
    }
    public void set_dateFin(String newDate) {
        this.dateFin = newDate;
    }
    public void set_idArticle(int newidArticle) {
        this.idArticle = newidArticle;
    }
    public void set_etatEnchere(int newEtatEnchere) {
        if (newEtatEnchere == 1) {
            this.etatEnchere = newEtatEnchere;
        }else if (newEtatEnchere == 0){
            this.etatEnchere = newEtatEnchere;
        }else if (newEtatEnchere == -1){
            this.etatEnchere = newEtatEnchere;
        }
    }
    //public void set_email(String newEmail) {
    //    this.email = newEmail;
    //}
    public void set_prix(float newPrix) {
        this.prix = newPrix;
    }
    
    public static void creeSchema(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement crÃ©Ã© ou pas du tout
        // je vais donc gÃ©rer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table encheres (
                        idEnchere integer not null primary key
                        generated always as identity,
                        dateDebut date not null,
                        dateFin date not null,
                        idArticle integer not null,
                        etatEnchere integer not null,
                        emailVendeur varchar(50) not null,
                        prix float not null
                    )
                    """);
            st.executeUpdate(
                    """
                    create table personne (
                        idPersonne integer not null primary key
                        generated always as identity,
                        email varchar(50) not null unique,
                        nom varchar(30) not null,
                        prenom varchar(30) not null,
                        password varchar(30) not null,
                        codePostal varchar(30) not null,
                        ville varchar(30) not null
                    )
                    """);
            st.executeUpdate(
                    """
                    create table articles (
                        idArticle integer not null primary key
                        generated always as identity,
                        descriptionC varchar(200) not null,
                        descriptionL varchar(1000) not null,
                        idCategorie integer not null,
                        emailVendeur varchar(50) not null
                    )
                    """);
             st.executeUpdate(
                    """
                    create table categories (
                        idCategorie integer not null primary key
                        generated always as identity,
                        categorie varchar(30) not null
                    )
                    """);
            st.executeUpdate(
                    """
                    alter table articles
                        add constraint fk_articles_emailVendeur
                        foreign key (emailVendeur) references personne(email)
                    """);
            st.executeUpdate(
                    """
                    alter table encheres
                        add constraint fk_encheres_idArticle
                        foreign key (idArticle) references articles(idArticle)
                    """);
            st.executeUpdate(
                    """
                    alter table encheres
                        add constraint fk_encheres_emailVendeur
                        foreign key (emailVendeur) references personne(email)
                    """);
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void deleteSchema(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            // pour être sûr de pouvoir supprimer, il faut d'abord supprimer les liens
            // puis les tables
            // suppression des liens
            try {
                st.executeUpdate(
                        """
                    alter table articles
                        drop constraint fk_articles_emailVendeur
                             """);
                System.out.println("constraint fk_articles_emailVendeur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    alter table encheres
                        drop constraint fk_encheres_idArticle
                    """);
                System.out.println("constraint fk_encheres_idArticle dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate(
                        """
                    alter table encheres
                        drop constraint fk_encheres_emailVendeur
                    """);
                System.out.println("constraint fk_encheres_emailVendeur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate(
                        """
                    drop table articles
                    """);
                System.out.println("dable articles dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    drop table encheres
                    """);
                System.out.println("table encheres dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    drop table personne
                    """);
                System.out.println("table personne dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
        }
    }
    
    public static void recreeTout(Connection con) throws SQLException {
        // j'essaye d'abord de tout supprimer
        try {
            deleteSchema(con);
//            System.out.println("ancien schéma supprimé");
        } catch (SQLException ex) {
//            System.out.println("pas de suppression d'un ancien schéma");
        }
        creeSchema(con);
        System.out.println("Schema (re)-created");
    }
    
    // Date en string = "AAAA-MM-JJ"
    // Date.valueOf("AAAA-MM-JJ")
                        
    public static int creerEnchere (Connection con, String email, String DateDebut, String DateFin, int idArticle, float prix)
            throws SQLException {
        con.setAutoCommit(false);
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into encheres (dateDebut, dateFin, idArticle, etatEnchere, emailVendeur, prix) values (?,?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setDate(1, Date.valueOf(DateDebut));
                pst.setDate(2, Date.valueOf(DateFin));
                pst.setInt(3, idArticle);
                pst.setInt(4, initialisationEtatEnchere(DateDebut));
                pst.setString(5, email);
                pst.setFloat(6, prix);
                pst.executeUpdate();
                con.commit();
                try ( ResultSet rid = pst.getGeneratedKeys()) {
                    rid.next();
                    int id = rid.getInt(1);
                    return id;
            }    
        }
    }  
    
    public static void DeleteEnchere (Connection con,String mail, int idEnchere) throws SQLException {
        con.setAutoCommit(false);
        try{
            PreparedStatement pst = con.prepareStatement("DELETE from encheres WHERE emailVendeur = ? and idenchere = ?");
            pst.setString(1,mail);
            pst.setInt(2,idEnchere);
            pst.executeUpdate();
            con.commit();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }   
    }
    
    public static void DeleteAllEncheres (Connection con,String mail) throws SQLException {
        con.setAutoCommit(false);
        try{
            PreparedStatement pst = con.prepareStatement("DELETE from encheres WHERE emailVendeur = ?");
            pst.setString(1,mail);
            pst.executeUpdate();
            con.commit();
        }
        catch(SQLException ex){
            System.out.println(ex);
        }   
    }
    
    public static ObservableList<Encheres> GetAllEncheresUtilisateur (Connection con, String email) throws SQLException {
        ObservableList<Encheres> obslist = FXCollections.observableArrayList();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from encheres where emailVendeur = ? "
        )) {
            pst.setString(1, email);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    obslist.add(new Encheres(rs.getInt("idEnchere"),rs.getString("dateDebut"),rs.getString("dateFin"),
                                    rs.getInt("idArticle"),rs.getInt("etatEnchere"),rs.getFloat("prix")));
                }
                return obslist;
            }
        }
    }
    
    //Méthode pour spécifier en String l'état de l'enchère
    public static String ToStringEtatEnchere (int etat){
        if (etat == 0){
            return "Pas Commencée";
        }else if (etat == 1){
            return "En cours";
        }else{
            return "Terminée";  
        }
    }
    
    public static List<Encheres> touteslesEncheres (Connection con) throws SQLException {
        List<Encheres> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select idEnchere, dateDebut, dateFin, idArticle, etatEnchere, emailVendeur, prix
                 from encheres
                 
                 order by idEnchere asc
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Encheres(rs.getInt("idEnchere"), rs.getString("dateDebut"),rs.getString("dateFin")
                   ,rs.getInt("idArticle"),rs.getInt("etatEnchere"),rs.getFloat("prix")));
                    System.out.println(rs.getInt("idEnchere")+ " : ");
                    System.out.println("Debut : "+rs.getString("dateDebut"));
                    System.out.println("Fin : "+rs.getString("dateFin"));
                    System.out.println("Etat de l'Enchere : "+ToStringEtatEnchere(rs.getInt("etatEnchere")));
                    System.out.println("======Article=====");
                    System.out.println(ArticleEnchere ( con, rs.getInt("idArticle")));
                    System.out.println("");
                    System.out.println("Prix : "+rs.getFloat("prix"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    public static List<Encheres> ProfilEncheresUtilisateur (Connection con, String email) throws SQLException {
        List<Encheres> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from encheres where emailVendeur = ? "
        )) {
            pst.setString(1, email);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Encheres(rs.getInt("idEnchere"), rs.getString("dateDebut"),rs.getString("dateFin")
                   ,rs.getInt("idArticle"),rs.getInt("etatEnchere"),rs.getFloat("prix")));
                    System.out.println(rs.getInt("idEnchere")+ " : ");
                    System.out.println("Debut : "+rs.getString("dateDebut"));
                    System.out.println("Fin : "+rs.getString("dateFin"));
                    System.out.println("Etat de l'Enchere : "+ToStringEtatEnchere(rs.getInt("etatEnchere")));
                    System.out.println("======Article=====");
                    System.out.println(ArticleEnchere ( con, rs.getInt("idArticle")));
                    System.out.println("");
                    System.out.println("Prix : "+rs.getFloat("prix"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    //Afficher une liste d'enchère par rapport à la catégorie
    public static List<Encheres> touteslesEncheresParCategorie (Connection con, int numeroCategorie) throws SQLException{
        List<Encheres> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from encheres where idCategorie = ? "
        )) {
            pst.setInt(1, numeroCategorie);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Encheres(rs.getInt("idEnchere"), rs.getString("dateDebut"),rs.getString("dateFin")
                   ,rs.getInt("idArticle"),rs.getInt("etatEnchere"),rs.getFloat("prix")));
                    System.out.println(rs.getInt("idEnchere")+ " : ");
                    System.out.println("Debut : "+rs.getString("dateDebut"));
                    System.out.println("Fin : "+rs.getString("dateFin"));
                    System.out.println("Etat de l'Enchere : "+ToStringEtatEnchere(rs.getInt("etatEnchere")));
                    System.out.println("======Article=====");
                    System.out.println(ArticleEnchere( con, rs.getInt("idArticle")));
                    System.out.println("");
                    System.out.println("Prix : "+rs.getFloat("prix"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    //Afficher une liste d'enchère active par rapport à la catégorie
    public static List<Encheres> touteslesEncheresActivesParCategorie (Connection con, int numeroCategorie, int etat) throws SQLException {
        List<Encheres> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from encheres where etatEcnhere = ? and (select * from articles where idCategorie = ?)"
        )) {
            pst.setInt(1, etat);
            pst.setInt(2, numeroCategorie);
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Encheres(rs.getInt("idEnchere"), rs.getString("dateDebut"),rs.getString("dateFin")
                   ,rs.getInt("idArticle"),rs.getInt("etatEnchere"),rs.getFloat("prix")));
                    System.out.println(rs.getInt("idEnchere")+ " : ");
                    System.out.println("Debut : "+rs.getString("dateDebut"));
                    System.out.println("Fin : "+rs.getString("dateFin"));
                    System.out.println("Etat de l'Enchere : "+ToStringEtatEnchere(rs.getInt("etatEnchere")));
                    System.out.println("======Article=====");
                    System.out.println(ArticleEnchere ( con, rs.getInt("idArticle")));
                    System.out.println("");
                    System.out.println("Prix : "+rs.getFloat("prix"));
                    System.out.println("============================");
                }
                return res;
            }
        }
    }
    
    //Update Statut enchère en cours (1), non en cours (0) ou terminé(-1) par rapport à la date de l'enchère
    //Méthode à effectuer à chaque ouverture de l'application (en actualisant)
    public void actualisationEtatEnchere () {     
    } 
    
    //A partir de la date début, choisi l'état de l'enchère en cours (1) ou pas en cours (0)
    public static int initialisationEtatEnchere( String dateDebut){
        return 1;
    }
    
    public static void UpdateEmailEnchere (Connection con, int id, String email){        
    }
    
    //Vérifier que l'enchère est pas en cours pour le faire (etatEnchere = 0)
    public static void UpdateDateDebutEnchere (Connection con, int id, String dateDebut){   
    }
    
    //Vérifier que l'enchère est pas en cours ou en cours pour le faire (etatEnchere = 0 ou etatEnchere = 1)
    public static void UpdateDateFinEnchere (Connection con, int id, String dateFin){   
    }
    
    public static void UpdatePrixEnchere (Connection con, int id, float prix){   
    }
    
    public static void Encherir (Connection con, int idEnchere) throws SQLException{
        //Vérifier que l'article ne soit pas dans une enchère 
        //afficher les articles qui ne sont pas dans une enchère
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "update from categories where idCategorie = ?")) {
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
}

