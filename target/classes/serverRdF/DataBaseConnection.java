package serverRdF;

import util.*;

import javax.jws.soap.SOAPBinding;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

public class DataBaseConnection extends Thread implements ServerInterface {

    /* Match that are currently running or created */
    private HashMap<String, Match> activeMatch = new HashMap<>();
    private HashMap<Match, ArrayList<User>> createdMatchObserver = new HashMap();

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
            obj.getDbActiveMatch();
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
       // System.out.println("prima della connessione");
        String url = "jdbc:postgresql://" + ipAddress + ":" + port + "/" + dbName;
        Properties props = new Properties();
        props.setProperty("user", dbUser);
        props.setProperty("password", dbPassword);
        Connection connection = DriverManager.getConnection(url, props);
        //System.out.println("connessione avvenuta");
        return connection;
    }
    /* ******
     *
     * ************************* QUERY  *******************************
     *
     */

    /* ******************      LOGIN  ******************************/

    /**
     * Method that check if user's mail and password is correct
     */
    @Override
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
     * Method that return a User from the database identified by his email and password
     */
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

            if (temp.getRole().equals("t")) {
                if (!verificationMailCheck(email)) {
                    temp.setRole("a");
                    modifyUser(temp);
                } else return null;
            }
            if (temp.getRole().equals("v")) {
                if (!verificationMailCheck(email)) {
                    temp.setRole("p");
                    modifyUser(temp);
                } else return null;
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method that update the information associated with a user on the database
     */
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

    /*
     *****************SIGN IN ********************
     */

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
               if(newUser.getRole().equals("a")){
                    pstmt.setString(6, "t");
                }else if(newUser.getRole().equals("p")){
                    pstmt.setString(6, "v");
                }else {
                    pstmt.setString(6, "u");
                }
            new Thread(() -> {
                try {
                    Thread.sleep(600000);
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
            System.out.println(ex.getMessage());
        }

        return affectedrows;
    }

    /**
     * Method that verify that the mail passed like parameter is present in the column mail in the users table
     */
    public boolean checkMailExistence(String mail) {
        return checkQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("users_column_mail")},
                new String[]{mail}
        );
    }

    /*
     * ********** VERIFICATION ********
     */
    public void insertVerificationCode(User user, String verificationCode) {
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

    /**
     * Method that return true if the user mail is present in the database's verification table
     */
    public boolean verificationMailCheck(String mail) {
        return checkQuery(
                StringManager.getString("verifications_table_name"),
                new String[]{StringManager.getString("user_mail_ver.table_name")},
                new String[]{mail}
        );
    }

    /**
     * method that check if there is the  passed like parameter and if found delete that string and return true
     * else return false
     */
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

    /*
     * ******************** GAME QUERY *******************************
     */

    /**
     * method that add a match to the local hashMap and the database if is not present another active match with the same name
     */
    public Match createMatch(Match match) {
        //deleteMatch(match);
        synchronized (this) {
            if (!activeMatch.containsKey(match.getMatchName())) {
                activeMatch.put(match.getMatchName(), match);

            } else return null;
        }

        String sql = "INSERT INTO matches (state, creator_id, user_id, match_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Array array = conn.createArrayOf("INTEGER", match.getIdsPlayer().toArray());
            pstmt.setString(1, StringManager.getString("match_state_created_convention"));   // Set state
            pstmt.setInt(2, match.getPlayers().get(0).getId()); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers
            pstmt.setString(4, match.getMatchName()); //set Match Name

            pstmt.executeUpdate();
            Match temp = getMatchbyName(match);
            if (temp != null) {
                activeMatch.replace(temp.getMatchName(), temp);
                createdMatchObserver.put(temp,temp.getPlayers());
                return temp;
            } else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Method that return a match from the database identified by his name
     */
    private Match getMatchbyName(Match match) {
        String qry = "SELECT * FROM matches WHERE match_name = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, match.getMatchName());
            ResultSet rs = pstmt.executeQuery();  // Execute the query
            rs.next();
            ArrayList<User> userID = new ArrayList<User>();
            Integer[] arrayId = (Integer[]) rs.getArray("user_id").getArray();
            for (Integer id : arrayId
            ) {
                userID.add(getUserById(id));
            }
            return new Match(rs.getInt("id"), userID, rs.getString("match_name"), rs.getString("state"));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**Check if a Match name is already in use return true if it is
     * @param matchName*/
    public synchronized boolean matchNameCheck(String matchName) {
        if (activeMatch.containsKey(matchName)) return false;
        String qry = "SELECT COUNT(*) FROM matches WHERE  (match_name = '"
                + matchName
                + "' AND state = '"
                + StringManager.getString("match_state_running_convention")
                + "') OR (match_name = '"
                + matchName
                + "' AND state = '" + StringManager.getString("match_state_created_convention")
                + "')";
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
     * Method that return a user taken from the database identified by his id
     */
    private User getUserById(Integer id) {
        String qry = "SELECT * FROM users WHERE id = '" + id + "'";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            //System.out.println(rs.getString("name") + " " + rs.getString("surname"));
            User temp = new User(rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("mail"),
                    rs.getString("nickname"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getInt("id"));

            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method that return the list of active match taken from the database
     */
    public ArrayList<Match> getDbActiveMatch() {
        String qry = "SELECT * FROM matches WHERE state = 'C' OR state = 'R'";
        ArrayList<Match> playableMatch = new ArrayList();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {

            ResultSet rs = pstmt.executeQuery();  // Execute the query

            while (rs.next()) {
                ArrayList<User> userID = new ArrayList<User>();
                Integer[] arrayId = (Integer[]) rs.getArray("user_id").getArray();
                for (Integer id : arrayId
                ) {
                    //System.out.println("lunghezza arryID: " + arrayId.length);
                    userID.add(getUserById(id));
                    //System.out.println("lunghezza Arraylst: " + userID.size());
                }
                playableMatch.add(new Match(rs.getInt("id"),
                        userID,
                        rs.getString("match_name"),
                        rs.getString("state")));     //da aggingere i campi qui sotto commentati per inizializzarlo
                /*
                rs.getString("match_name");
                rs.getInt("id");
                rs.getArray("user_id");
                 */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Match> temp = playableMatch;
        for (Match m : temp) {
            activeMatch.put(m.getMatchName(), m);
            createdMatchObserver.put(m, new ArrayList<>());
        }
        return playableMatch;
    }

    /**
     * Method that update the information of a match in the local hashMap and in the database
     */
    //todo modificare
    public Match updateMatch(Match match) {
        /*match = getMatchbyName(match);
        activeMatch.put(match.getMatchName(), match);*/
        activeMatch.get(match.getMatchName()).setNextPlayerTurn();
        //activeMatch.replace(match.getMatchName(),match);
        String qry = "UPDATE matches " +
                "SET user_id = ? ," +
                " state = ? " +
                "WHERE id = ?";
        Match toUpdate = new Match();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {

            Array array = conn.createArrayOf("INTEGER", match.getPlayers().toArray());
            pstmt.setArray(1, array);  // Set ID palyers
            if (match.getPlayers().size() == 3) {
                pstmt.setString(2, StringManager.getString("match_state_running_convention"));
            } else pstmt.setString(2, StringManager.getString("match_state_created_convention"));  // Set state
            pstmt.setInt(3, match.getIdMatch()); //match Id


            pstmt.executeUpdate();
            toUpdate = getMatchbyName(match);
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
     * Method that remove a match from the local hasMap and update his state
     */
    public void endMatch(Match match) {
        activeMatch.remove(match.getMatchName());
        String qry = "UPDATE matches " +
                "SET  state = ? " +
                "WHERE id = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, StringManager.getString("match_state_ended_convention"));
            pstmt.setInt(2, match.getIdMatch());

            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ******************************** query sentence table****************************************/

    /**
     * Method that add a list of sentence to the database and return the number of sentence added
     */
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

    /**
     * Method that return the Sentences for a match
     */
    public ArrayList<Sentence> getMatchSentence(Match match) {
        String qry = "SELECT * FROM sentences EXCEPT ( " +
                "SELECT * " +
                "FROM sentences WHERE ? = ANY(seen_by_user)" +
                " UNION " +
                "SELECT * " +
                "FROM sentences WHERE ? = ANY(seen_by_user)" +
                " UNION " +
                "SELECT * " +
                "FROM sentences WHERE ? = ANY(seen_by_user)" +
                " ) " +
                "LIMIT 5";
        ArrayList<Sentence> playableSentence = new ArrayList<>();
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {


            pstmt.setInt(1, match.getPlayers().get(0).getId());   // Set ID palyers
            pstmt.setInt(2, match.getPlayers().get(1).getId()); // Set ID palyers
            pstmt.setInt(3, match.getPlayers().get(2).getId());  // Set ID palyers

            ResultSet rs = pstmt.executeQuery();  // Execute the query
            while (rs.next()) {
                playableSentence.add(new Sentence(
                        rs.getString("sentence"), //sentence
                        rs.getString("hint"),      //hint
                        rs.getInt("id"),        //sentence id
                        rs.getInt("create_by_user"))); //created BY
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        match.setMancheSentences(playableSentence);
        updateActiveMatch(match);
        return playableSentence;
    }

    /* ************************* Check Query **********************************/

    /**
     * General query for checking an element from a specific table column, return true in case of success, else return false
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

    /*
     ******************************  Matches Query  **********************
     */

    public Manches insertManches(Match match) {
        String qry = "INSERT INTO manches(match_id, sentence_id) VALUES(?,?)";

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {

            pstmt.setInt(1, match.getIdMatch());   // Set id match
            pstmt.setInt(2, match.getCurrentManche().getSentence().getId()); //set sentence id
            pstmt.executeUpdate();  // Execute the query

            return getManche(match);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Manches getManche(Match match) {
        /*valutare se fare questo metodo void che aggiorna direttamente l'hasmap*/
        String qry = "SELECT* FROM manches WHERE match_id='"
                + match.getIdMatch()
                + "' AND sentence_id='"
                + match.getCurrentManche().getSentence().getId() + "'";
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry);
            rs.next();
            return new Manches(rs.getInt("id"), match.getCurrentManche().getSentence());

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateManche(Manches manches) {
        String qry = "UPDATE manches " +
                "SET " +
                "seenByUser= ? " +
                "manche_wallet = ? " +
                "WHERE id = ? ";

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {

            Array array = conn.createArrayOf("INTEGER", manches.getSeenBy().toArray());
            Array wallets = conn.createArrayOf("INTEGER", manches.getPlayerWallet());
            pstmt.setArray(1, array);           // Set ID watchers
            pstmt.setArray(2, wallets);         //set playerWallet
            pstmt.setInt(3, manches.getId());   // Set id manches
            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /* ************************* Delete Query **********************************/

    /**
     * General query for deleting an element from a specific table column, return the number of column affected, if any,
     * else in case of error return -1
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

    /**
     * Query for deleting a user, make use of the general deleteQuery,
     * return the number of column affected or, in case of error, -1
     */
    public int deleteUser(User user) {
        //TODO pensare se voler identificare tramite mail o id
        return deleteQuery(
                StringManager.getString("usersTableName"),
                new String[]{StringManager.getString("user_id_column_name")},
                new String[]{user.getEmail()}
        );
    }

    public int deleteMatch(Match match) {
        //todo ricrearla con l'id se serve al posto del nome forse
        int i =deleteQuery("matches", new String[]{"match_name"}, new String[]{match.getMatchName()});
        createdMatchObserver.remove(activeMatch.get(match.getMatchName()));
        activeMatch.remove(match.getMatchName());
        return i;
    }

    /*
     *
     * *****************      ALTRI METODI
     *
     * */

    /**
     * Method that update the corresponding match passed like parameter
     */
    public void updateActiveMatch(Match match) {
        activeMatch.get(match.getMatchName()).setMatch(match);
    }

    /**
     * Method that return a match with the name passed like parameter from the local hashMap
     */
    public Match getMatchFromHash(String matchName) {
        return activeMatch.get(matchName);
    }

    /**
     * Method that add an observer to a specific match
     */
    public void addObserver(Match match, RemoteGameObserverInterface o, User observer) {
        activeMatch.get(match.getMatchName()).addObserver(o);
        createdMatchObserver.get(activeMatch.get(match.getMatchName())).add(observer);

    }

    /**
     * Method that add a player to a match and return that match, if possible,
     * else return null
     */
    @Override
    public Match addPlayer(Match match, User userToAdd) throws RemoteException {
        synchronized (this) {
            if (activeMatch.get(match.getMatchName()).getPlayers().size() < 3) {
                System.out.println("meno di 3 giocatori");
                activeMatch.get(match.getMatchName()).addPlayer(userToAdd);
                /*TODO creare metodo start match che cambia lo stato da creatd a running
                 *  costruttore Manches solo con sentence +chiamata a db per id di tale manche
                 * valutare  se mettere tutto qui o meno*/
            }else return null;}
            if (activeMatch.get(match.getMatchName()).getPlayers().size() == 3) {
                    ArrayList<Sentence> play = getMatchSentence(activeMatch.get(match.getMatchName()));
                    if (play.size() < 5) {
                        //TODO avviso amministratore frasi insufficienti
                        activeMatch.get(match.getMatchName()).setState("E");
                    } else {

                        activeMatch.get(match.getMatchName()).startMatch(play, createdMatchObserver.get(match));
                        activeMatch.get(match.getMatchName()).getCurrentManche().setId(insertManches(activeMatch.get(match.getMatchName())).getId());
                    }
                }
        return activeMatch.get(match.getMatchName());
    }


    /**
     * Method that return the list of active match from the local HasMap
     */
    @Override
    public HashMap<String, Match> getActiveMatch() throws RemoteException {
        /*ArrayList<Match> temp = getDbActiveMatch();
        for (Match m : temp) {
            activeMatch.put(m.getMatchName(), m);
            createdMatchObserver.put(m, new ArrayList<>());
        }*/
        return activeMatch;
    }

    /*
     *
     * ***************QUERY DI TESTING
     * */
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

    @Override
    public synchronized void removePlayer(User player, String matchName, RemoteGameObserverInterface o) {
        activeMatch.get(matchName).removePlayer(player);
        if(activeMatch.get(matchName).getPlayers().size()==0){
            activeMatch.get(matchName)
                    .getState()
                    .equals(StringManager.getString("match_state_created_convention"));
                deleteMatch(activeMatch.get(matchName));
                return;
        }
        if(activeMatch.get(matchName)
                .getState()
                .equals(StringManager.getString("match_state_running_convention"))){
            activeMatch.get(matchName)
                    .setState(StringManager.getString("match_state_interrupted_convention"));
        }
        activeMatch.get(matchName).removeObserver(o);

    }

    @Override
    public void removeObserver(String matchName, RemoteGameObserverInterface o){
        activeMatch.get(matchName).removeObserver(o);
    }
}
