package serverRdF;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import util.Sentence;
import util.User;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DataBaseConnection {
    private String ipAddress;
    private String port;
    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    private String dbUser;
    private String dbPassword;

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public DataBaseConnection(String ipAddress, String port, String dbName, String dbUser, String dbPassword) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public DataBaseConnection() {
        setIpAddress("localhost"); //$NON-NLS-1$
        setPort("5432"); //$NON-NLS-1$
        setDbName(Messages.getString("DataBaseConnection.word_postgres")); //$NON-NLS-1$
        setDbUser("postgres"); //$NON-NLS-1$
        setDbPassword("postgres"); //$NON-NLS-1$

    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getPort() {
        return port;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    //TODO ELIMINARE COMMENTI
    // INUTILE FARE SINGLETON SULLA CONNESSIONE PERCHE TANTO DOPO UN PO' LA CHIUDE
    private Connection getConnectionInstance() throws SQLException {

        System.out.println("prima della connessione"); //$NON-NLS-1$
        String url = "jdbc:postgresql://"+ipAddress+":"+port+"/"+dbName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        Properties props = new Properties();
        props.setProperty("user",dbUser); //$NON-NLS-1$
        props.setProperty("password",dbPassword); //$NON-NLS-1$
        Connection connection = DriverManager.getConnection(url, props);
        System.out.println("connessione avvenuta"); //$NON-NLS-1$
        return connection;
    }

    /* ******************** Query users table *************************/
    //TODO FAR INIZIALIZZARE L'UTENTE SERVE NEL LOGIN. IL SERVER THREAD DEVE AVERE UN UTENTE STATIC?
    public boolean getOneUser(String email, String password){
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail='"+email+"' AND password = '"+ password+"'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            while (rs.next())
            {
                System.out.println(rs.getString("name")+" "+rs.getString("surname")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
        String qry ="SELECT COUNT(*) FROM users WHERE role = 'a'"; //$NON-NLS-1$
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
        ResultSet rs = st.executeQuery("SELECT * FROM users"); //$NON-NLS-1$
        //ciclo while che ritorna una COLONNA cioè lo stesso campo per tutte le righe
        // tipo tutti i nomi degli utenti
        /*se vuoi fare la get dell'id devi usare i long*/
        while (rs.next())        {

            System.out.println(rs.getString("name")+" "+rs.getString("surname")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        }
        rs.close();
        st.close();
    }

    public int modifyName(String newUserName){
        String SQL = Messages.getString("DataBaseConnection.23") //$NON-NLS-1$
                + "SET name = ?," //$NON-NLS-1$
                + "last_change_date = CURRENT_TIMESTAMP " //$NON-NLS-1$
                + "WHERE id = ?"; //$NON-NLS-1$

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
        String SQL = "UPDATE users " //$NON-NLS-1$
                + "SET surname = ?," //$NON-NLS-1$
                + "last_change_date = CURRENT_TIMESTAMP " //$NON-NLS-1$
                + "WHERE id = ?"; //$NON-NLS-1$

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
        String qry ="SELECT COUNT(*) FROM users WHERE mail = '"+mail+"'"; //$NON-NLS-1$ //$NON-NLS-2$
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
        String SQL = "UPDATE users " //$NON-NLS-1$
                + "SET name = ?," //$NON-NLS-1$
                + "SET surname = ?," //$NON-NLS-1$
                + "SET mail = ?," //$NON-NLS-1$
                + "SET nickname = ?," //$NON-NLS-1$
                + "last_change_date = CURRENT_TIMESTAMP " //$NON-NLS-1$
                + "WHERE id = ?"; //$NON-NLS-1$

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUser.getName());
            pstmt.setString(2,newUser.getSurname() );
            pstmt.setString(3,newUser.getEmail());
            pstmt.setString(4, newUser.getNickname());
            pstmt.setInt(5, newUser.getId());

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }


    public int insertUser(User newUser){
        String SQL = "INSERT INTO users (name, surname, mail, password, nickname, role) VALUES(?, ?, ?, ?, ?, ?)"; //$NON-NLS-1$
        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

                pstmt.setString(1, newUser.getName());
                pstmt.setString(2,newUser.getSurname());
                pstmt.setString(3,newUser.getEmail());
                pstmt.setString(4, newUser.getPassword());
                pstmt.setString(5, newUser.getNickname());
               /* if(newUser instanceof admin){
                    pstmt.setString(6, "a");
                }else if(newUser instanceof user){
                    pstmt.setString(6, "p");
                }else {
                    pstmt.setString(6, "u");
                }*/

                affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return affectedrows;
    }


    /* ********************query match table********************************/

    public void insertMatch() {

        Integer[] id = {3,4,5};

        String sql = "INSERT INTO matches (state, creator_id, user_id) VALUES (?, ?, ?)"; //$NON-NLS-1$
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", id); //$NON-NLS-1$
            pstmt.setString(1, "e");   // Set state //$NON-NLS-1$
            pstmt.setInt(2, 2); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers

            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
/* ******************************** query sentence table****************************************/
public void insertSentences(List<Sentence> sentences, User user) throws SQLException {

    Connection conn = getConnectionInstance();
    int count=0;

    try (CallableStatement insElem = conn.prepareCall("{ ? = call sentence_insert( ?, ?, ?) }")) //$NON-NLS-1$
    {
        for (Sentence sentence:
                sentences) {
            insElem.registerOutParameter(1, Types.INTEGER);
            insElem.setString(2, sentence.getSentence());
            insElem.setString(3, sentence.getHint());
            //TODO inseriment frase usare la get user ID per l'id da passare
            insElem.setInt(4, user.getId());    //id creatore frase
            insElem.execute();
            count+=insElem.getInt(1);
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    //TODO OPZIONALE: RITORNARE IL NUMERO DI FRASI INSERITE
    System.out.println("nuove frasi inserite: "+count); //$NON-NLS-1$
}
}
