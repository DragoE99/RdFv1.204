package serverRdF;

import util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * DataBaseConnection is the class that represents our database, containing all queries and utility methods
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class DataBaseConnection {
    private String ipAddress;
    private String port;
    private String dbName;
    

    private String dbUser;
    private String dbPassword;

    /**
     * Getter for the name field of this class
     * @return
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Setter for the name field of this class
     * @param dbName
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Setter for the IP address of the database
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Setter for the port of the database
     * @param port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Setter for username of the database
     * @param dbUser
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    /**
     * Setter for password of the database
     * @param dbPassword
     */
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

    /**
     * Constructor default
     */
    public DataBaseConnection() {
        setIpAddress("localhost");
        setPort("5432");
        setDbName("postgres");
        setDbUser("postgres");
        setDbPassword("postgres");

    }

    /**
     * Getter for the IP address of the database
     * @return
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Getter for the port of the database
     * @return
     */
    public String getPort() {
        return port;
    }

    /**
     * Getter for the username of the database
     * @return
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * Getter for the password of the database
     * @return
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * 
     * @return a new connection instance with the fields instantiated in the constructor
     * @throws SQLException
     */
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
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE mail='" + email + "' AND password = crypt('" + password +"', password)");
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
                + " password = crypt(?, gen_salt('bf')),"
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
        String SQL = "INSERT INTO users (name, surname, mail, password, nickname, role) VALUES(?, ?, ?, crypt('"+newUser.getPassword()+"', gen_salt('bf')), ?, ?)";
        int affectedrows = 0;

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newUser.getName());
            pstmt.setString(2, newUser.getSurname());
            pstmt.setString(3, newUser.getEmail());
           // pstmt.setString(4, "crypt('"+newUser.getPassword()+"', gen_salt('bf')");
            pstmt.setString(4, newUser.getNickname());
            //TODO cambiare ruolo 'a' in 't' e 'p' in 'v'
                if(newUser instanceof Admin){
                    pstmt.setString(5, "t");
                }else if(newUser instanceof Player){
                    pstmt.setString(5, "v");
                }else {
                    pstmt.setString(5, "u");
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
    public ArrayList<Sentence> getAllSentence() {
        String SQL = "SELECT * FROM sentences";
        ArrayList<Sentence> sentenceList = new ArrayList<>();
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                Array userid = rs.getArray("seen_by_user");

                Sentence temp;
                if (userid != null) {
                    Integer[] nullable = (Integer[]) userid.getArray();
                    temp = new Sentence(rs.getString("sentence"),
                            rs.getString("hint"),
                            rs.getInt("id"),
                            rs.getInt("create_by_user"),
                            Arrays.asList(nullable));
                } else {
                    temp = new Sentence(rs.getString("sentence"),
                            rs.getString("hint"),
                            rs.getInt("id"),
                            rs.getInt("create_by_user"),
                            (List<Integer>) userid);

                }

                sentenceList.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sentenceList;
    }

    public void modifySentence(Sentence sentence){
        String qry = "UPDATE sentences " +
                "SET sentence = ? ," +
                " hint = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {

            pstmt.setString(1, sentence.getSentence());
            pstmt.setString(2,sentence.getHint() ); //set creator id
            pstmt.setInt(3,sentence.getId());


            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteSentence(Sentence sentence){
        deleteQuery("sentences", new String[]{"sentence","hint"},new String[]{sentence.getSentence(), sentence.getHint()});
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
     * @param tableName
     * @param column
     * @param valueToCheck
     * @return
     */
    private boolean checkQuery(String tableName, String[] column, String[] valueToCheck) {
        String qry = "SELECT COUNT(*) FROM " + tableName + " WHERE " + column[0] + " = '" + valueToCheck[0] + "'";
        for (int i = 1; i < valueToCheck.length; i++) {
            if(column[i].equals(StringManager.getString("users_column_password"))){
                qry = qry + " AND " + column[i] + " = crypt('" +  valueToCheck[i] +"', "+column[i]+")";
            }else
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
     * Check if a user is present in the database through mail and password
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
     * Method that check if there is the  passed like parameter and if found delete that string and return true
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

    /**
     * Insert a new verification code associated with a user in the database
     * @param user
     * @param verificationCode
     */
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
    /**
     * General method for deleting something from the database
     * @param tableName
     * @param column
     * @param valueToDelete
     * @return the number of modified rows or -1 in case of an error
     */
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
    /**
     * Check if a Match name is already in use return true if it is
     * @param matchName
     */
    public synchronized boolean matchNameCheck(String matchName) {
        String qry = "SELECT COUNT(*) FROM matches WHERE (match_name = '" +
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
     * Method that return a match from the database identified by his name
     * @param match
     */
    private Match getMatchbyName(Match match) {
        String qry = "SELECT * FROM matches WHERE match_name = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, match.getName());
            ResultSet rs = pstmt.executeQuery();  // Execute the query
            rs.next();
            ArrayList<User> userID = new ArrayList<User>();
            Integer[] arrayId = (Integer[]) rs.getArray("user_id").getArray();
            for (Integer id : arrayId
            ) {
                userID.add(getUserById(id));
            }
           // return new Match(rs.getInt("id"), userID, rs.getString("match_name"), rs.getString("state"));
            return new Match(); //TODO costruttore con metch id e nome match

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Method that update the information of a match in the database
     * @param machId
     * @param playerId
     * @param matchState
     */
    public void updateMatch(Integer[] playerId, int machId, String matchState) {
        String qry = "UPDATE matches " +
                "SET user_id = ? ," +
                " state = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {

            Array array = conn.createArrayOf("INTEGER", playerId);
            pstmt.setArray(1, array);  // Set ID palyers
            pstmt.setString(2, matchState.toUpperCase()); // Set state
            pstmt.setInt(3, machId); //set creator id


             pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /**
     * Delete a match from database identified by the match name
     * @param match
     */
    public void deleteMatchFromName(Match match){
        deleteQuery(StringManager.getString("matches_table_name"),new String[]{StringManager.getString("match_name_column_name")},new String[]{match.getName()});
    }

    public ArrayList<Sentence> getMatchSentence(ArrayList<Integer> idPlayer) {
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


            pstmt.setInt(1, idPlayer.get(0));   // Set ID palyers
            pstmt.setInt(2, idPlayer.get(1)); // Set ID palyers
            pstmt.setInt(3, idPlayer.get(2));  // Set ID palyers

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

    /**
     * Query returning the list of matches either waiting for players or already ongoing
     * @return arraylist of the ongoing matches
     */
    public ArrayList<Match> getPlayableMatch() {
        String qry = "SELECT * FROM matches WHERE state = '"+
        StringManager.getString("match_state_created_convention")+
                "' OR state = '"+StringManager.getString("match_state_running_convention")+"'";
        ArrayList<Match> playableMatch = new ArrayList();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {

            ResultSet rs = pstmt.executeQuery();  // Execute the query
            while (rs.next()) {
            	Integer[] users = (Integer[])rs.getArray("user_id").getArray();
            	playableMatch.add(new Match(rs.getString("match_name"), rs.getInt("id"), users));     //da aggingere i campi qui sotto commentati per inizializzarlo
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
    /*
     * *************************manches query
     * */
    //da chiamare appena creata la manche
    /**
     * Insertion of the manches of a match into the database
     * @param match
     */
    public void insertManches(Match match){
        /*campi Manche on database:
         number= number of mache frmon 1 to 5,
         match_id,
         sentence_id,
         seen_by_user[]= user that have seen that manche,
         manche_wallets[] = global wallet for that manche*/
        String qry= "INSERT INTO manches(number, match_id, sentence_id) VALUES (?,?,?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {
            pstmt.setInt(1, 1); //TODO metodo che ritorna la manche Corrente
            pstmt.setInt(2,match.getId());
            pstmt.setInt(3,4); //TODO metodo che ritorna la frase associata al match corrente (o almeno il suo id)
            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // da chiamare idealmente a fine manche
    /**
     * Update the manches of a match 
     * @param match
     */
    public void updateManches(Match match){
        String qry= "UPDATE manches SET " +
                "seen_by_user = ? " +
                "manche_wallets = ? " +
                "WHERE (match_id = ?) AND (number = ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {

            pstmt.setInt(1, 1); //TODO qualcosa per ritornare chi ha visto la manche
            pstmt.setInt(2, 1);
            pstmt.setInt(3,match.getId());
            pstmt.setInt(4,4); //da agiornare qundo fatto i todos di prima
            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete query for a manche
     * @param match_id
     * @param numberOfManche
     */
    public void deleteManche(Integer match_id, Integer numberOfManche){
        deleteQuery("manches", new String[]{"match_id", "number"}, new String[]{match_id.toString(), numberOfManche.toString()});
    }

    /* **********************************Statistic query **/
    /**
     * Query returning all requested personal statistics of a user
     * @param idUser
     * @return arraylist of strings with statistics
     */
    public ArrayList<String> getUserStat(Integer idUser){
        ArrayList<String> userStat= new ArrayList<>();
        //TODO manca 1 statistica
        double matchWon =statisticQuery(idUser, "match_won", "player_id","count");
        double mancheWon =statisticQuery(idUser, "manche_won", "player_id","count");
        double loseCount = statisticQuery(idUser, "lose_all_count", "player_id","count");
        double passCount = statisticQuery(idUser, "pass_count", "player_id","count");
        double playedMatch = statisticQuery(idUser, "played_match", "id","count");
        double playedManches = statisticQuery(idUser, "played_manche", "user_id","count");
        double avgMatchPoints = statisticQuery(idUser, "avg_match_points", "player_id","user_point");
        double avgManchePoints = statisticQuery(idUser, "avg_manche_points", "player_id","user_point");
        double averageLoseMatch= playedMatch==0? 0: loseCount/playedMatch;
        double averageLoseManches= playedManches==0? 0:loseCount/playedManches;
        double averagePassMatch =playedMatch==0? 0: passCount/playedMatch;
        double averagePassManche = playedManches==0? 0: passCount/playedManches;
        String mancheWonString= new String("Number of manches won: "+mancheWon);
        String matchWonString= new String("Number of matches won: "+matchWon);

        String mancheSeen = new String("Number of manches seen: " +
                statisticQuery(idUser, "manche_seen", "user_id", "count"));

        String manchePlayed = new String("Number of manches played: " +playedManches);

        String matchPlayed = "Number of matches played: " +playedMatch;

        String avgMtP = "Average of points per match: " + avgMatchPoints;
        String avgMnP = "Average of points per manche: " + avgManchePoints;

        String LCount = "Total number of PERDI rolled: " + loseCount;
        String avgLMt = "Average of PERDI rolled per match: " + averageLoseMatch;
        String avgLMn = "Average of PERDI rolled per manche: " + averageLoseManches;

        String PCount = "Total number of PASSA rolled: " + passCount;
        String avgPMt = "Average of PASSA rolled per match: " + averagePassMatch;
        String avgPMn = "Average of PASSA rolled per manche: " + averagePassManche;

        userStat.add(mancheWonString);
        userStat.add(matchWonString);
        userStat.add(mancheSeen);
        userStat.add(manchePlayed);
        userStat.add(matchPlayed);
        userStat.add(avgMtP);
        userStat.add(avgMnP);
        userStat.add(LCount);
        userStat.add(avgLMt);
        userStat.add(avgLMn);
        userStat.add(PCount);
        userStat.add(avgPMt);
        userStat.add(avgPMn);
        return userStat;
        //lose all count fratto played match e poi played manche
    }

    /**
     * Query returning all requested global statistics
     * @return arraylist of strings representing the statistics
     */
    public ArrayList<String> getGlobalStat(){
        ArrayList<String> globalStat= new ArrayList<>();

        //numero piu' alto di  manche giocate
        String maxNumberManchePlayed= "SELECT player_id, MAX(count) FROM manche_won " +
                "WHERE count= (SELECT MAX(count) " +
                "FROM manche_won) " +
                "GROUP BY player_id ";
        globalStat.add("Higher count of manche played "+globalStatisticExecutor(maxNumberManchePlayed,"MAX"));

        //media piu' alta di punti acquisiti per manche
        String higherAvgManchePoints="SELECT player_id, MAX(user_point) FROM avg_match_points " +
                "WHERE user_point= (SELECT MAX(user_point) " +
                "FROM avg_match_points) " +
                "GROUP BY player_id";
        globalStat.add("Higher average points gained for manche "+globalStatisticExecutor(higherAvgManchePoints,"MAX"));
        //punti massimi acquisiti in un match
        String maxPointMatch = "SELECT player_id, MAX(user_point) FROM gained_points_match " +
                "WHERE user_point= (SELECT MAX(user_point) " +
                "FROM gained_points_match) " +
                "GROUP BY player_id";
        globalStat.add("Most points gained during a match "+globalStatisticExecutor(maxPointMatch,"MAX"));

        //punti massimi in una manche
        String maxPointManche= "SELECT player_id, MAX(user_point) FROM gained_points_manche " +
                "WHERE user_point=(SELECT MAX(user_point) FROM gained_points_manche) " +
                "GROUP BY player_id";
        globalStat.add("Most points gained during a manche "+globalStatisticExecutor(maxPointManche,"MAX"));


        //estratto perde tutto
        String maxLoseRolled = "SELECT player_id, MAX(count) FROM lose_all_count " +
                "WHERE count=(SELECT MAX(count) FROM lose_all_count) " +
                "GROUP BY player_id";
        globalStat.add("Rolled PERDI most time "+globalStatisticExecutor(maxLoseRolled,"MAX"));

        //maggior numero di volte perso il turno per errori
        String maxPass="SELECT player_id, MAX(count) FROM pass_count " +
                "WHERE count=(SELECT MAX(count) FROM pass_count) " +
                "GROUP BY player_id";
        globalStat.add("Lose turn most time "+globalStatisticExecutor(maxLoseRolled,"MAX"));

        globalStat.add(higerMovePoint());

        //TODO aggiungere query mancante (sul foglio delle query sul desktop)
        return globalStat;
    }
    private String higerMovePoint(){
        String qry= "SELECT a1.player_id, MAX(a1.action_wallet), a1.match_id, a1.letter_called, a1.manche_number, manches.sentence " +
                " FROM actions AS a1 JOIN ( SELECT * FROM manches JOIN sentences ON sentence_id = sentences.id) as manches " +
                "  ON (a1.match_id = manches.match_id AND  a1.manche_number = manches.number) " +
                " WHERE a1.action_wallet = (SELECT MAX(action_wallet) FROM actions)  " +
                " GROUP BY a1.player_id, a1.match_id, a1.letter_called, a1.manche_number, manches.sentence";
        String toReturn = "Higer points move: ";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {
            ResultSet rs =    pstmt.executeQuery();  // Execute the query

            ArrayList<Integer> userId= new ArrayList<>();
            while (rs.next()){
                Integer playerId= rs.getInt("player_id");

                User u =getUserById(playerId);
                String player = u.getName();
                if(!userId.contains(playerId)) {
                    String sentence = rs.getString("sentence");
                    int pointsGained = rs.getInt("max");
                    String letterCalled = rs.getString("letter_called");
                    toReturn = toReturn + player + " letter called: '" + letterCalled + "' on sentence: " + sentence + " gained points: " + pointsGained + "\n \t";
                }
                userId.add(playerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    /**
     * General query that returns the count of a column for global statistics
     * @param qry
     * @param columnCountName
     * @return a string representing a fragment of a statistic
     */
    private String globalStatisticExecutor(String qry, String columnCountName){

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            ResultSet rs = pstmt.executeQuery();
            String toReturn="";
            int i=0;
            while (rs.next()){
                toReturn= i==0? rs.getInt(columnCountName)+" player Nickname: "+getUserById(rs.getInt("player_id")).getNickname():toReturn+"/"+getUserById(rs.getInt("player_id")).getNickname();
                i++;
            }

            System.out.println(toReturn);
            return toReturn;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }

    }
    
    /**
     * General query that returns the count of a column for personal statistics
     * @param userId
     * @param viewName
     * @param columnIdName
     * @param columnCountName
     * @return a string representing a fragment of a statistic
     */
    private double statisticQuery(Integer userId, String viewName, String columnIdName, String columnCountName){
        String qry ="SELECT * FROM "+viewName+" WHERE "+columnIdName+" = "+userId;
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getDouble(columnCountName);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
