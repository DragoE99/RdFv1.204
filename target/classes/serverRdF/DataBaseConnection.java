package serverRdF;

import java.sql.*;
import java.util.Properties;

public class DataBaseConnection {
    public DataBaseConnection() {
    }

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

    public void getAllUsers() throws SQLException {
        Connection conn =getConnectionInstance();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users");
        //ciclo while che ritorna una COLONNA cioè lo stesso campo per tutte le righe
        // tipo tutti i nomi degli utenti
        /*se vuoi fare la get dell'id devi usare i long*/
        while (rs.next())
        {

            System.out.println(rs.getString("name")+" "+rs.getString("surname"));

        }
        rs.close();
        st.close();
    }
}
