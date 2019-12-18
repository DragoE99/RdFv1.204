package serverRdF;

import java.sql.*;
import java.util.Properties;

public class DataBaseConnection {
    public DataBaseConnection() {
    }

    //TODO ELIMINARE COMMENTI
    // INUTILE FARE SINGLETON SULLA CONNESSIONE PERCHE TANTO DOPO UN PO' LA CHIUDE
    private static Connection getConnectionInstance() throws SQLException {

       // if(connection==null){
            System.out.println("prima della connessione");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","postgres");
            Connection connection = DriverManager.getConnection(url, props);
            System.out.println("connessione avvenuta");
       // }
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

}
