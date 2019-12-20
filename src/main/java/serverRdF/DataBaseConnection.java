package serverRdF;

import util.User;

import java.sql.*;
import java.util.Properties;

public class DataBaseConnection {
    public DataBaseConnection() {
    }

    //TODO ELIMINARE COMMENTI
    // INUTILE FARE SINGLETON SULLA CONNESSIONE PERCHE TANTO DOPO UN PO' LA CHIUDE
    private static Connection getConnectionInstance() throws SQLException {

            System.out.println("prima della connessione");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","postgres");
            Connection connection = DriverManager.getConnection(url, props);
            System.out.println("connessione avvenuta");
        return connection;
    }

    /* ******************** Query users table *************************/
    //TODO FAR INIZIALIZZARE L'UTENTE SERVE NEL LOGIN. IL SERVER THREAD DEVE AVERE UN UTENTE STATIC?
    public boolean getOneUser(String email, String password){
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail='"+email+"' AND password = '"+ password+"'");
            while (rs.next())
            {
                System.out.println(rs.getString("name")+" "+rs.getString("surname"));
                /*rs.getString("name");
                rs.getString("nickname");
                rs.getString("surname");
                rs.getString("mail");
                rs.getInt("id");*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //funzione che ritorna falso se non ci sono admin o vero se c'è ne almeno uno
    public boolean thereIsAdmin(){
        String qry ="SELECT COUNT(*) FROM users WHERE role = 'a'";
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry);
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //FUNZIONE che stampa tutti gli utenti presenti nel server utile più che altro per test
    public void getAllUsers() throws SQLException {
        Connection conn =getConnectionInstance();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users");
        //ciclo while che ritorna una COLONNA cioè lo stesso campo per tutte le righe
        // tipo tutti i nomi degli utenti
        /*se vuoi fare la get dell'id devi usare i long*/
        while (rs.next())        {

            System.out.println(rs.getString("name")+" "+rs.getString("surname"));

        }
        rs.close();
        st.close();
    }

    public int modifyName(String newUserName){
        String SQL = "UPDATE users "
                + "SET name = ?,"
                + "last_change_date = CURRENT_TIMESTAMP "
                + "WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUserName);
            pstmt.setInt(2, 3);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }

    public int modifySurname(String newUserName){
        String SQL = "UPDATE users "
                + "SET surname = ?,"
                + "last_change_date = CURRENT_TIMESTAMP "
                + "WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUserName);
            pstmt.setInt(2, 3);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }
    public boolean checkMail(String mail){
        String qry ="SELECT COUNT(*) FROM users WHERE mail = '"+mail+"'";
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry);
            rs.next();
            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public int modifyUser (User newUser){
        String SQL = "UPDATE users "
                + "SET name = ?,"
                + "SET surname = ?,"
                + "SET mail = ?,"
                + "SET nickname = ?,"
                + "last_change_date = CURRENT_TIMESTAMP "
                + "WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            /*pstmt.setString(1, newUser.getName());
            pstmt.setString(2,newUser.getSurname() );
            pstmt.setString(3,newUser.getMail());
            pstmt.setString(4, newUser.getNickname());
            pstmt.setInt(5, newUser.getId());*/

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }




    /* ********************query match table********************************/

    public void insertMatch() {

        Integer[] id = {3,4,5};


        String sql = "INSERT INTO matches (state, creator_id, user_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", id);
            pstmt.setString(1, "e");   // Set state
            pstmt.setInt(2, 2); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers

            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
