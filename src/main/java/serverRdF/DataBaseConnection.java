package serverRdF;

import util.*;

import java.sql.*;
import java.util.ArrayList;
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

        User temp;
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail='" + email + "' AND password = '" + password + "'");
            rs.next();
            //System.out.println(rs.getString("name") + " " + rs.getString("surname"));
            String role = rs.getString("role");

            if(role.equals("t")){
                 temp = new Admin(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
                if(!verificationMailCheck(email)){
                    changeUserRole(temp,"a");
                    return temp;
                }
            }else return null;
            if(role.equals("v")){
                 temp = new Player(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
                if(!verificationMailCheck(email)){
                    changeUserRole(temp,"p");
                    return temp;
                }
            }else return null;

            if(role.equals("a")){
                return temp = new Admin(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
            }
            if(role.equals("p")){
                return temp = new Admin(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    public User getUserById(int id){
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE id= " + id);
            rs.next();
            System.out.println(rs.getString("name") + " " + rs.getString("surname"));
            if(rs.getString("role")=="p"){
            return new Player(rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("mail"),
                    rs.getString("nickname"),
                    rs.getString("password"),
                    rs.getInt("id"));
            }else {
                return new Admin(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getInt("id"));
            }
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
                + " surname = ?,"
                + " mail = ?,"
                + " nickname = ?,"
                + " password = ?,"
                + " last_change_date = CURRENT_TIMESTAMP "
                + "WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUser.getName());
            pstmt.setString(2, newUser.getSurname());
            pstmt.setString(3, newUser.getEmail());
            pstmt.setString(4, newUser.getNickname());
            pstmt.setString(5, newUser.getPassword());
            pstmt.setInt(6, newUser.getId());

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return affectedrows;

    }
    public int changeUserRole(User user, String role) {
        String SQL = "UPDATE users "
                + "SET role = ? "
                + "WHERE id = ?";

        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, role);
            pstmt.setInt(2, user.getId());

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return affectedrows;

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
                if(newUser instanceof Admin){
                    pstmt.setString(6, "a");
                }else if(newUser instanceof Player){
                    pstmt.setString(6, "p");
                }else {
                    pstmt.setString(6, "u");
                }

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
    public void insertSentences(List<Sentence> sentences, User user) throws SQLException {

        Connection conn = getConnectionInstance();
        int count = 0;

        try (CallableStatement insElem = conn.prepareCall("{ ? = call sentence_insert( ?, ?, ?) }")) {
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

    //todo cambiare la classe sentence from List<User> to List<Integer>
    public void updateSeenByUserSentence(int sentenceId, Integer[] seenBy) {
        String qry = "UPDATE sentences " +
                "SET    seen_by_user = (select array_agg(distinct e) from unnest(seen_by_user || ?) e) " +
                "WHERE  id = ?";

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {

            Array array = conn.createArrayOf("INTEGER", seenBy);
            pstmt.setArray(1, array);  // Set ID palyers
            pstmt.setInt(2, sentenceId); //set creator id


            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public boolean checkNicknameExistence(String nickname) {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("nickname_column")},
                new String[]{nickname}
        );
    }

    public boolean logInCheck(String mail, String password) {
        String[] column = {StringManager.getString("users_column_mail"), StringManager.getString("users_column_password")};
        return checkQuery(
                StringManager.getString("usersTableName"),
                column,
                new String[]{mail, password}
        );
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
            return affectedRow >=0 ;

            }

        return false;
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

    public int deleteUser(User user){
        //TODO pensare se voler identificare tramite mail o id
        return deleteQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("user_id_column_name")},
                new String[]{user.getEmail()}
        );
    }

    /**********************match query***********************/
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
    public void createMatch(Integer[] id, String matchName) {

        //id = new Integer[]{3, 4, 5};

        String sql = "INSERT INTO matches (state, creator_id, user_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", id);
            pstmt.setString(1, "c");   // Set state
            pstmt.setInt(2, id[1]); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers

            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Match updateMatch(Integer[] playerId, int machId) {
        String qry = "UPDATE matches " +
                "SET user_id = ? ," +
                " state = ? " +
                "WHERE id = ?";
        Match toUpdate = new Match();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {

            Array array = conn.createArrayOf("INTEGER", playerId);
            pstmt.setArray(1, array);  // Set ID palyers
            if (playerId.length == 3) {
                pstmt.setString(2, "r");
            } else pstmt.setString(2, "c");  // Set state
            pstmt.setInt(3, machId); //set creator id


            ResultSet rs = pstmt.executeQuery();  // Execute the query
            rs.next();
            toUpdate = new Match();     //da aggingere i campi qui sotto commentati per inizializzarlo
                /*
                rs.getString("match_name");
                rs.getInt("id");
                rs.getArray("user_id");
                 */

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return toUpdate;
    }

    public void endMatch(int matchId){
        String qry = "UPDATE matches " +
                "SET  state = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, "e");
            pstmt.setInt(2, matchId);

            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sentence> getMatchSentence(Integer[] idPlayer) {
        String qry = "SELECT * FROM sentences EXCEPT ( " +
                "SELECT * " +
                "FROM sentences WHERE ? = ANY(seen_by_user)" +
                " UNION " +
                "SELECT * " +
                "FROM sentences WHERE ? = ANY(seen_by_user)" +
                " UNION " +
                "SELECT * " +
                "FROM sentences WHERE ? = ANY(seen_by_user)" +
                " )";
        ArrayList<Sentence> playableSentence = new ArrayList<>();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {


            pstmt.setInt(1, idPlayer[0]);   // Set ID palyers
            pstmt.setInt(2, idPlayer[1]); // Set ID palyers
            pstmt.setInt(3, idPlayer[2]);  // Set ID palyers

            ResultSet rs = pstmt.executeQuery();  // Execute the query
            while (rs.next()) {
                playableSentence.add(new Sentence(rs.getString("sentence"),
                        rs.getString("hint"),
                        rs.getInt("id"),
                        rs.getInt("create_by_user")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playableSentence;
    }





    public ArrayList<Match> getPlayableMatch() {
        String qry = "SELECT * FROM matches WHERE state = 'c'";
        ArrayList<Match> playableMatch = new ArrayList();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {

            ResultSet rs = pstmt.executeQuery();  // Execute the query
            while (rs.next()) {
                playableMatch.add(new Match());     //da aggingere i campi qui sotto commentati per inizializzarlo
                /*
                rs.getString("match_name");
                rs.getInt("id");
                rs.getArray("user_id");
                 */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playableMatch;
    }

}
