package serverRdF;

import util.Sentence;
import util.StringManager;
import util.User;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DataBaseConnection extends Thread implements ServerInterface {

    private HashMap<String, GameServer> runningMatch = new HashMap<>();

    private String ipAddress;
    private String port;
    private String dbName;

    DataBaseConnection obj;
    ServerInterface stub;
    Registry registry;


    public static void main(String[] args) {
        DataBaseConnection test = new DataBaseConnection();
        test.start();
    }

    @Override
    public void run() {
        System.out.println(" stampa di prova");
        //System.setProperty("java.rmi.server.hostname","18.5.28.53");
        try {

            obj = new DataBaseConnection();
            stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);
            registry = LocateRegistry.createRegistry(3333);
            registry.rebind("ruota", stub);

            System.out.println(" ** Server pronto");
        } catch (Exception e) {
            System.err.println(" ***** Server Error");
            e.printStackTrace();
            System.exit(0);
        }
    }

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
        setIpAddress("localhost");
        setPort("5432");
        setDbName("postgres");
        setDbUser("postgres");
        setDbPassword("postgres");

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

        System.out.println("prima della connessione");
        String url = "jdbc:postgresql://" + ipAddress + ":" + port + "/" + dbName;
        Properties props = new Properties();
        props.setProperty("user", dbUser);
        props.setProperty("password", dbPassword);
        Connection connection = DriverManager.getConnection(url, props);
        System.out.println("connessione avvenuta");
        return connection;
    }

    /* ******************** Query users table *************************/
    //TODO FAR INIZIALIZZARE L'UTENTE SERVE NEL LOGIN. IL SERVER THREAD DEVE AVERE UN UTENTE STATIC?
    public User getOneUser(String email, String password) {

            try (Connection conn = getConnectionInstance()) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail='" + email + "' AND password = '" + password + "'");
                rs.next();
                System.out.println(rs.getString("name") + " " + rs.getString("surname"));
                User temp = new User(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getString("role"),
                        rs.getInt("id"));

                if(temp.getRole().equals("t")){
                   if(!verificationMailCheck(email)){
                       temp.setRole("a");
                     modifyUser(temp);
                   }
                }else return null;
                if(temp.getRole().equals("v")){
                    if(!verificationMailCheck(email)){
                        temp.setRole("p");
                        modifyUser(temp);
                    }
                }else return null;
                return temp;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    //FUNZIONE che stampa tutti gli utenti presenti nel server utile più che altro per test
    public void getAllUsers() throws SQLException {
        Connection conn = getConnectionInstance();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM users");
        //ciclo while che ritorna una COLONNA cioè lo stesso campo per tutte le righe
        // tipo tutti i nomi degli utenti
        /*se vuoi fare la get dell'id devi usare gli int*/
        while (rs.next()) {

            System.out.println(rs.getString("name") + " " + rs.getString("surname"));

        }
        rs.close();
        st.close();
    }

    public int modifyUser(User newUser) {
        String SQL = "UPDATE users "
                + "SET name = ?,"
                + "  surname = ?,"
                + "  mail = ?,"
                + " nickname = ?,"
                + " last_change_date = CURRENT_TIMESTAMP "
                + "WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUser.getName());
            pstmt.setString(2, newUser.getSurname());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getNickname());
            pstmt.setInt(5, newUser.getId());

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }

    public void insertVerificationCode(User user, String verificationCode){
        String SQL = "INSERT INTO verifications (user_mail, verification_code) VALUES(?, ?)";

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, verificationCode);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int insertUser(User newUser) {
        String SQL = "INSERT INTO users (name, surname, mail, password, nickname, role) VALUES(?, ?, ?, ?, ?, ?)";
        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUser.getName());
            pstmt.setString(2, newUser.getSurname());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getPassword());
            pstmt.setString(5, newUser.getNickname());
               /* if(newUser.getRole().equals("a")){
                    pstmt.setString(6, "t");
                }else if(newUser.getRole().equals("p")){
                    pstmt.setString(6, "v");
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

        Integer[] id = {3, 4, 5};

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

    /* ******************************** query sentence table****************************************/
    public void insertSentences(List<Sentence> sentences, User user) {


        int count = 0;

        try (Connection conn = getConnectionInstance();
             CallableStatement insElem = conn.prepareCall("{ ? = call sentence_insert( ?, ?, ?) }")) {
            for (Sentence sentence :
                    sentences) {
                insElem.registerOutParameter(1, Types.INTEGER);
                insElem.setString(2, sentence.getSentence());
                insElem.setString(3, sentence.getHint());
                //TODO inseriment frase usare la get user ID per l'id da passare
                insElem.setInt(4, user.getId());    //id creatore frase
                insElem.execute();
                count += insElem.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //TODO OPZIONALE: RITORNARE IL NUMERO DI FRASI INSERITE
        System.out.println("nuove frasi inserite: " + count);
    }

    /* ************************* Check Query **********************************/
    private boolean checkQuery(String tableName, String[] column, String[] valueToCheck) {
        String qry = "SELECT COUNT(*) FROM " + tableName + " WHERE " + column[0] + " = '" + valueToCheck[0] + "'";
        for (int i = 1; i < valueToCheck.length; i++) {
            qry = qry + " AND " + column[i] + " = '" + valueToCheck[i] + "'";
        }
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

    public boolean checkAdminExistence() {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("user_column_role")},
                new String[]{StringManager.getString("adminRole")}
        );
    }

    public boolean checkMailExistence(String mail) {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("users_column_mail")},
                new String[]{mail}
        );
    }

    @Override
    public boolean logInCheck(String mail, String password) {
        String[] column = {StringManager.getString("users_column_mail"), StringManager.getString("users_column_password")};
        return checkQuery(
                StringManager.getString("usersTableName"),
                column,
                new String[]{mail, password}
        );
    }

    public boolean matchNameCheck(String matchName) {
        String qry = "SELECT COUNT(*) FROM matches WHERE  (match_name = '" + matchName + "' AND state = 'r') OR (match_name = '" + matchName + "' AND state = 'c')";
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

    /* ritorna true se la mail e' presente nella tabella verifications**/
    public boolean verificationMailCheck(String mail) {
        return checkQuery(
                StringManager.getString("verifications_table_name"),
                new String[]{StringManager.getString("user_mail_ver.table_name")},
                new String[]{mail}
        );
    }

    /* verifica se il codice di verifica e' presente e in caso positivo elimina tale riga e ritorna true */
    public boolean verificationCodeCheck(String verificationCode) {
        if (checkQuery(StringManager.getString("verifications_table_name"),
                new String[]{StringManager.getString("verification.code_column_name")},
                new String[]{verificationCode}
        )
        ) {

            int affectedRow = deleteQuery(
                    StringManager.getString("verifications_table_name"),
                    new String[]{StringManager.getString("verification.code_column_name")},
                    new String[]{verificationCode});
            //TODO OPZIONALE: comunicare quando si elimina piu' di una riga dato che non e' normale
            return affectedRow >= 0;

        }

        return false;
    }

    /* ************************* Delete Query **********************************/
    /* query generale per delete ritorna il numero di righe eliminate oppure -1 in caso di errore*/
    private int deleteQuery(String tableName, String[] column, String[] valueToDelete) {
        String qry = "DELETE * FROM " + tableName + " WHERE " + column[0] + " = '" + valueToDelete[0] + "'";
        for (int i = 1; i < valueToDelete.length; i++) {
            qry = qry + " AND " + column[i] + " = '" + valueToDelete[i] + "'";
        }
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            return st.executeUpdate(qry);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int deleteUser(User user) {
        //TODO pensare se voler identificare tramite mail o id
        return deleteQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("user_id_column_name")},
                new String[]{user.getEmail()}
        );
    }


}
