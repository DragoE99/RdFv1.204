package serverRdF;

import java.sql.*;
import java.util.Properties;

public class DataBaseConnection {
    public DataBaseConnection() {
    }
    private static Connection connection=null;
    private static Connection getConnectionInstance() throws SQLException {
        if(connection==null){
            System.out.println("prima della connessione");
            String url = "jdbc:postgresql://localhost:5432/postgres";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","postgres");
            connection = DriverManager.getConnection(url, props);
            System.out.println("connessione avvenuta");
        }
        return connection;
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
            System.out.print("Column 1 returned ");

            System.out.println(rs.getString("name")+" "+rs.getString("surname"));

        }
        rs.close();
        st.close();
    }
}
