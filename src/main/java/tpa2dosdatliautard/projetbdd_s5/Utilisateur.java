package tpa2dosdatliautard.projetbdd_s5;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur extends Personne {
    
    //Attributs 
    private String nom;
    private String prenom;
    private String codePostal;
    private String ville;
    
    //Constructeur
    public Utilisateur (String email,String pass,String nom,String prenom, String codePostal, String ville){
        super(email,pass); //appelle le constructeur de la superclasse Personne
        this.nom=nom;
        this.prenom = prenom;
        this.codePostal = codePostal;
        this.ville=ville;
    }
    
    //Méthodes Get Set
    public String get_nom(){
        return this.nom;
    } 
    public String get_prenom(){
        return this.prenom;
    } 
    public String get_codePostal(){
        return this.codePostal;
    } 
    public String get_ville(){
        return this.ville;
    } 
    
    public void set_nom(String newNom) {
        this.nom = newNom;
    }
    public void set_prenom(String newPrenom) {
        this.prenom = newPrenom;
    }
    public void set_codePostal(String newCodePostal) {
        this.codePostal = newCodePostal;
    }
    public void set_ville(String newVille) {
        this.ville = newVille;
    }
    
    //Méthode ToString Utilisateur
    public void ToString (){
        System.out.println(this.nom+" "+this.prenom);
        System.out.println(this.email);
        System.out.println(this.get_pass());
        System.out.println(this.codePostal);
        System.out.println(this.ville);
    }
    
    //Méthode pour savoir si un utilisateur existe déjà
    public static boolean emailUtilisateurExiste(Connection con, String email) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                "select id from personne where email = ?")) {
            pst.setString(1, email);
            ResultSet res = pst.executeQuery();
            return res.next();
        }    
    }
    
    public static List<Utilisateur> tousLesUtilisateurs(Connection con) throws SQLException {
        List<Utilisateur> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement(
                """
               select email, nom, prenom, password, codePostal, ville 
                 from personne
                 order by nom asc
               """
        )) {
            try ( ResultSet rs = pst.executeQuery()) {
                System.out.println("liste des utilisateurs :");
                System.out.println("------------------------");
                while (rs.next()) {
                    Utilisateur uti = new Utilisateur(rs.getString("email"), rs.getString("password"),rs.getString("nom")
                    ,rs.getString("prenom"),rs.getString("codePostal"),rs.getString("ville"));
                    res.add(uti);
                    uti.ToString();
                }
                return res;
            }
        }
    }

    public static class EmailExisteDejaException extends Exception {
        //public EmailExisteDejaException (){
        //    System.out.println();
        //}
    }
    
    public static void afficheTousLesUtilisateur(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet tlu = st.executeQuery("select * from personne")) {
                System.out.println("liste des utilisateurs :");
                System.out.println("------------------------");
                while (tlu.next()) {
                    int id = tlu.getInt("idPersonne");
                    String email = tlu.getString("email");
                    String nom = tlu.getString("nom");
                    String prenom = tlu.getString("prenom");
                    String pass = tlu.getString("password");
                    String codepostal = tlu.getString("codepostal");
                    String ville = tlu.getString("ville");
                    System.out.println( +id + " : " + email + " (" + nom + " ,"+ prenom+ " ,"+pass+" ," +codepostal+" )" );
                }
            }
        }
    }
    
    public static Utilisateur ProfilUtilisateur(Connection con, String email) throws SQLException {
        
        try ( PreparedStatement pst = con.prepareStatement(
                "select * from personne where email = ? "
        )) {
            pst.setString(1, email);
            try ( ResultSet rs = pst.executeQuery()) {
                Utilisateur profil = null;
                while (rs.next()) {
                    profil = new Utilisateur(rs.getString("email"),
                            rs.getString("password"), rs.getString("nom"), rs.getString("prenom"),
                            rs.getString("codePostal"),rs.getString("ville"));
                }
                profil.ToString();
                return profil;
            }
        }
    }
    
    public static void DeleteEnchereParUtilisateur (Connection con, int idEnchere) throws SQLException{  
        con.setAutoCommit(false);
        try{
            try ( PreparedStatement pst = con.prepareStatement(
                    "delete from encheres where idEnchere = ? and etatEnchere = ? or etatEnchere = ?")) {
                pst.setInt(1, idEnchere);
                pst.setInt(2, 0);
                pst.setInt(3, 1);
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
    
    public static void UpdateNomUtilisateur (Connection con, int id, String nom){        
    }
    
    public static void UpdatePrenomUtilisateur (Connection con, int id, String prenom){        
    }
    
    public static void UpdatePasswordUtilisateur (Connection con, int id, String pass){        
    }
    
    public static void UpdateCodePostalUtilisateur (Connection con, int id, String codePostal){        
    }
    
    public static void UpdateVilleUtilisateur (Connection con, int id, String vill){        
    }
}
