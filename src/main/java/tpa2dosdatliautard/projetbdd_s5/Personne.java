package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import tpa2dosdatliautard.projetbdd_s5.Utilisateur.EmailExisteDejaException;


public class Personne {
//Attributs
    public final String email; //final=>variable non modifiable une fois initialisée
    private String pass;
    
//Constructeur
    public Personne(String email,String pass){
        this.email=email;
        this.pass=pass;
    }
    
//Méthodes Get Set
    //pas de méthode set pour email, car public et final
    public String get_email(){
        return this.email;
    } 
    public String get_pass(){
        return this.pass;
    } 
    
    public void set_pass(String newPass) {
        this.pass = newPass;
    }
    
    public static int creerPersonne (Connection con, String email, String pass,String nom,String prenom, String codePostal, String ville)
            throws SQLException, EmailExisteDejaException {
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
                "select idPersonne from personne where email = ?")) {
            chercheEmail.setString(1, email);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new EmailExisteDejaException();
            }
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                insert into personne (email, nom, prenom, password, codePostal, ville) values (?,?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, email);
                pst.setString(2, nom);
                pst.setString(3, prenom);
                pst.setString(4, pass);
                pst.setString(5, codePostal);
                pst.setString(6, ville);
                pst.executeUpdate();
                con.commit();
                try ( ResultSet rid = pst.getGeneratedKeys()) {
                    rid.next();
                    int id = rid.getInt(1);
                    return id;
                }
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }  
    
    public static String login(Connection con, String email, String pass) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select idPersonne from personne where email = ? and password = ?")) {
            pst.setString(1, email);
            pst.setString(2, pass);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return email;
            } else {
                String erreur="erreur";
                return erreur;
            }
        }catch(SQLException ex){
            System.out.println("Email ou Mot de passe Incorrect !!!");
            return null;
        }
    }
}
