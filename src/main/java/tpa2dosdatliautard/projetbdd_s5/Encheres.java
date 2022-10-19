/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package tpa2dosdatliautard.projetbdd_s5;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acoulibaly01
 */
public class Encheres {

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

    public static void creeSchema(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement crÃ©Ã© ou pas du tout
        // je vais donc gÃ©rer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table Encheres (
                        idEnchere integer not null primary key
                        generated always as identity,
                        dateDebut varchar(30) not null,
                        dateFin varchar(30) not null
                        idArticle integer not null,
                        etatEnchere integer not null
                        emailVendeur varchar(30) not null,
                        prixMinimal float not null
                    )
                    """);
            st.executeUpdate(
                    """
                    create table Personne (
                        email varchar(30) not null primary key
                        generated always as identity,
                        nom varchar(30) not null,
                        prenom varchar(30) not null,
                        password varchar(30) not null,
                        codePostal varchar(30) not null,
                        ville varchar(30) not null,
                    )
                    """);
            st.executeUpdate(
                    """
                    create table Articles (
                        idArticle varchar(30) not null primary key
                        generated always as identity,
                        descriptionC varchar(200) not null,
                        descriptionL varchar(1000) not null,
                        idCategorie integer not null,
                        emailVendeur varchar(50) not null
                    )
                    """);
            st.executeUpdate(
                    """
                    alter table Articles
                        add constraint fk_articles_emailVendeur
                        foreign key (emailVendeur) references Personne(email)
                    """);
            st.executeUpdate(
                    """
                    alter table Encheres
                        add constraint fk_encheres_idArticle
                        foreign key (idArticle) references Articles(idArticle)
                    """);
            st.executeUpdate(
                    """
                    alter table Encheres
                        add constraint fk_encheres_emailVendeur
                        foreign key (emailVendeur) references Personne(email)
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
                    alter table aime
                        drop constraint fk_aime_u1
                             """);
                System.out.println("constraint fk_aime_u1 dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    alter table aime
                        drop constraint fk_aime_u2
                    """);
                System.out.println("constraint fk_aime_u2 dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate(
                        """
                    drop table aime
                    """);
                System.out.println("dable aime dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    drop table utilisateur
                    """);
                System.out.println("table utilisateur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Connection con = defautConnect();
            System.out.println("Connection OK");
            creeSchema(con);
            System.out.println("creation OK");
        } catch (ClassNotFoundException ex) {
            throw new Error(ex);
        } catch (SQLException ex) {
            throw new Error(ex);
        }
    }
}

