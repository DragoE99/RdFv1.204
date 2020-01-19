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

    /**
     * Constructor
     * @param ipAddress
     * @param port
     * @param dbName
     * @param dbUser
     * @param dbPassword
     * */
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
        setDbName("brandodb");
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

      /**
     * Method that return a User from the database identified by his email and password if present
       * @param email
       * @param password
     */
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
                }else return null;
            }
            if(role.equals("v")){
            	System.out.println("ruolo v");
                 temp = new Player(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
                if(!verificationMailCheck(email)){
                    changeUserRole(temp,"p");
                    return temp;
                }else return null;
            }

            if(role.equals("a")){
                temp = new Admin(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
                return temp;
            }
            if(role.equals("p")){
                 temp = new Player(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        password,
                        rs.getInt("id"));
                 return temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //TODO getUserByID da eliminare?
    /**
     * Method that return a User from the database identified by his userId
     * @param id
     */
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

    /**
     * Method that return a User from the database identified by his mail
     * @param mail
     */
    public User getUserByMail(String mail){
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail= '" + mail + "'");
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
    //TODO da elimimare
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

    /**
     * Method that update the information associated with a user on the database
     * @param newUser
     */
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
    /**
     * Method that update the role of an User on the database
     * @param user
     * @param role
     */
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

    /**
     * Method that insert a new User in the database
     * @param newUser
     */
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
            //TODO cambiare ruolo 'a' in 't' e 'p' in 'v'
                if(newUser instanceof Admin){
                    pstmt.setString(6, "t");
                }else if(newUser instanceof Player){
                    pstmt.setString(6, "v");
                }else {
                    pstmt.setString(6, "u");
                }
                new Thread(() -> {
                    try {
                        System.out.println("vado a dormire ");
                        Thread.sleep(600000);
                        System.out.println("Buongiornisimo ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(verificationMailCheck(newUser.getEmail()))deleteQuery(
                            StringManager.getString("usersTableName"),
                            new String[] {StringManager.getString("users_column_mail")},
                            new String[] {newUser.getEmail()});
                }).start();


            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
        	ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

        return affectedrows;
    }


    /* ******************************** query sentence table****************************************/
    /**
     * Method that add a list of sentence to the database and return the number of sentence added
     * @param sentences
     * @param user creator
     */
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
    /**
     * Method that update the list associated with a sentence that represent who have seen that sentence
     */
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
    /**
     * General query for checking an element from a specific table column,
     * return true in case of success, else return false
     */
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

    /**
     * Method that verify the presence of at least one Admin in the database
     */
    public boolean checkAdminExistence() {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("user_column_role")},
                new String[]{StringManager.getString("adminRole")}
        );
    }

    /**
     * Method that verify that the mail passed like parameter is present in the column mail in the users table\
     * return true if mail is present
     * @param mail
     */
    public boolean checkMailExistence(String mail) {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("users_column_mail")},
                new String[]{mail}
        );
    }

    /**
     * Method that verify if a nickname is already used return true if it is
     * @param nickname
     */
    public synchronized boolean checkNicknameExistence(String nickname) {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("nickname_column")},
                new String[]{nickname}
        );
    }

    /**
     *  check if a user is present in the database through mail and password
     * @param mail
     * @param password
     */
    public boolean logInCheck(String mail, String password) {
        if(verificationMailCheck(mail)){
        if(checkVerificationTime(mail)){
            deleteQuery(StringManager.getString("usersTableName"),
                   new String[] {StringManager.getString("users_column_mail")},
                    new String[] {mail});
            return false;
        }
        }
        String[] column = {StringManager.getString("users_column_mail"), StringManager.getString("users_column_password")};
        return checkQuery(
                StringManager.getString("usersTableName"),
                column,
                new String[]{mail, password}
        );
    }

    /**
     * Method that return true if the user mail is present in the database's verification table
     * @param mail
     */
    public synchronized boolean verificationMailCheck(String mail) {

        return checkQuery(
                StringManager.getString("verifications_table_name"),
                new String[]{StringManager.getString("user_mail_ver.table_name")},
                new String[]{mail}
        );
    }
    /**
     * Check if a non verified user is in the database more than 10 minutes
     * @param mail
     * */
    public boolean checkVerificationTime(String mail){
        String qry= "SELECT * FROM users WHERE mail = '"+mail+"'";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Timestamp tempo = rs.getTimestamp("last_change_date");
            System.out.println("ultima modifica: "+tempo);
            long timeDifference =( new Timestamp(System.currentTimeMillis()).getTime() - tempo.getTime())/600000;
            System.out.println("in minuti: "+timeDifference);
            if(timeDifference>10){
                return true;
            }else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
        return false;
    }
    /**
     * method that check if there is the  passed like parameter and if found delete that string and return true
     * else return false
     * @param verificationCode
     * @param mail
     * @return boolean
     */
    public boolean verificationCodeCheck(String verificationCode, String mail) {
        if (checkQuery(StringManager.getString("verifications_table_name"),
                new String[]{StringManager.getString("verification.code_column_name"),"user_mail"},
                new String[]{verificationCode, mail}
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

    /**Insert a new verification code associated with a user in the database*/
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
    /**General method for deleting something from the database */
    private int deleteQuery(String tableName, String[] column, String[] valueToDelete) {
        String qry = "DELETE FROM " + tableName + " WHERE " + column[0] + " = '" + valueToDelete[0] + "'";
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

    /**********************match query***********************/
    /**Check if a Match name is already in use return true if it is
     * @param matchName*/
    public synchronized boolean matchNameCheck(String matchName) {
        String qry = "SELECT COUNT(*) FROM matches WHERE  (match_name = '" +
                matchName + "' AND state = '"+StringManager.getString("match_state_running_convention")+
                "') OR (match_name = '" +
                matchName +
                "' AND state = '"+StringManager.getString("match_state_created_convention")+
                "')";

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

    /** Create a new match in the database
     * @param id creator id
     * @param matchName
     * */
    public void createMatch(Integer[] id, String matchName) {

        //id = new Integer[]{3, 4, 5};

        String sql = "INSERT INTO matches (state, creator_id, user_id, match_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", id);
            pstmt.setString(1, StringManager.getString("match_state_created_convention"));   // Set state
            pstmt.setInt(2, id[0]); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers
            pstmt.setString(4, matchName);	//set match name

            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * Method that update the information of a match in the database
     * @param machId
     * @param playerId
     */
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
                pstmt.setString(2, StringManager.getString("match_state_running_convention"));
            } else pstmt.setString(2, StringManager.getString("match_state_created_convention"));  // Set state
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

    /**
     * Method that change the state of a match to ended on the database
     * @param matchId
     */
    public void endMatch(int matchId){
        String qry = "UPDATE matches " +
                "SET  state = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, StringManager.getString("match_state_ended_convention"));
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
        String qry = "SELECT * FROM matches WHERE state = '"+
                StringManager.getString("match_state_created_convention")+
                "' OR state = '"+StringManager.getString("match_state_running_convention")+"'";
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
