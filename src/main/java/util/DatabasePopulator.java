package util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import serverRdF.DataBaseConnection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DatabasePopulator {
    public DatabasePopulator() {
    }

    static ArrayList<Sentence> sentences = new ArrayList<>();

    public static void main(String args[]) throws IOException {
        DatabasePopulator test = new DatabasePopulator();
        User mio = test.getUserById(2);
        sentences = test.getAllSentence();
        ArrayList<User> users = test.getAllPlayer();
      /*  System.out.println(mio.getName() + " cognome " + mio.getSurname() + " ruolo " + mio.getRole() + " altro");
        System.out.println("lista user " + users.size() + " lista frasi " + sentences.size());
        Integer[] iduser = new Integer[3];
        for (int i = 4; i < 7; i++) {
            iduser[i - 4] = users.get(i).getId();
        }*/
        //test.updateSeenByUserSentence(74,iduser);
        //System.out.println("numero frasi giocabili: " + test.getMatchSentence(iduser).size());
        //test.createMatch(iduser,"match1");
       /* DataBaseConnection perMatch = new DataBaseConnection();
        Match mioMatch = perMatch.getDbActiveMatch().get(0);
        mioMatch.setMancheSentences(sentences);
        System.out.println("test inserimento e ritorno Manches prima frase:\n " + sentences.get(0).getSentence() + "\n");
        Manches nuovaManche = new Manches();
        nuovaManche.setSentence(sentences.get(0));
        mioMatch.addManche(nuovaManche);
        nuovaManche = test.insertManches(mioMatch);
        if (nuovaManche != null) {
            System.out.println("nuova manche ritornata id: " + nuovaManche.getId());
            System.out.println("frase " + nuovaManche.getSentence().getSentence());
        }*/
        test.checkVerificationTime("de@de.it");
        /*Match provaRmiGame= new Match(iduser,"match1");
        GameServer testGame = new GameServer(provaRmiGame);
        testGame.start();
        System.out.println("match name "+ provaRmiGame.getMatchName());
        GameRmi giocatore = new GameRmi(provaRmiGame.getMatchName());*/

    }

    /* *********************************Query da testare************************************************/


    public void checkVerificationTime(String mail){
        String qry= "SELECT * FROM users WHERE mail = '"+mail+"'";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            Timestamp tempo = rs.getTimestamp("last_change_date");
            System.out.println("ultima modifica: "+tempo);
            long timeDifference = new Timestamp(System.currentTimeMillis()).getTime() - tempo.getTime() ;
            System.out.println("in minuti: "+(timeDifference/60000));
        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
    }
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
        String qry = "SELECT* FROM manches WHERE match_id='"
                + match.getIdMatch()
                + "' AND sentence_id='"
                + match.getCurrentManche().getSentence().getId() + "'";
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(qry);
            rs.next();
            Manches temp = null;
            int i = rs.getInt("sentence_id");
            for (Sentence s : sentences) {
                if (s.getId() == i) {
                    temp = new Manches(rs.getInt("id"), s);
                    System.out.println("trovata frase");
                }
            }
            return temp;

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
            Array wallets= conn.createArrayOf("INTEGER", manches.playerWallet);
            pstmt.setArray(1, array);           // Set ID watchers
            pstmt.setArray(2, wallets);         //set playerWallet
            pstmt.setInt(3, manches.getId());   // Set id manches
            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String ipAddress = "localhost";
    private String port = "5432";
    private String dbName = "postgres";

    private Connection getConnectionInstance() throws SQLException {

        System.out.println("prima della connessione");
        String url = "jdbc:postgresql://" + ipAddress + ":" + port + "/" + dbName;
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");
        Connection connection = DriverManager.getConnection(url, props);
        System.out.println("connessione avvenuta");
        return connection;
    }

    public void insertUser() {
        String SQL = "INSERT INTO users (name, surname, mail, password, nickname, role) VALUES(?, ?, ?, ?, ?, ?)";
        int affectedrows = 0;
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            int i = 0;
            for (i = 0; i < 20; i++) {
                User newUser = new User("utente" + i, "cognome" + i, i + "user@gmil.com", "number" + i, "1234");
                pstmt.setString(1, newUser.getName());
                pstmt.setString(2, newUser.getSurname());
                pstmt.setString(3, newUser.getEmail());
                pstmt.setString(4, newUser.getPassword());
                pstmt.setString(5, newUser.getNickname());
                /*if (newUser instanceof Admin) {
                    pstmt.setString(6, "a");
                } else if (newUser instanceof Player) {
                    pstmt.setString(6, "p");
                } else {
                    pstmt.setString(6, "u");
                }*/
                affectedrows += pstmt.executeUpdate();
            }
            System.out.println("righe inserite" + affectedrows);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertSentenceFromCsv(String filePath) {
        try {
            //TODO COLLEGARE CON CSVFILECONTROLLER
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] nextLine = new String[2];
            List<Sentence> sentences = new ArrayList<Sentence>();
            while ((nextLine = reader.readNext()) != null) {
                Sentence sentence = new Sentence(nextLine[0], nextLine[1]);
                if (sentence.getSentence().length() < 60 && sentence.getHint().length() < 60) {
                    sentences.add(sentence);
                }
            }
            //TODO PASSARE LA LISTA A SERVER THREAD E POI A DATABASECONNECTION
            DataBaseConnection query = new DataBaseConnection();

            query.insertSentences(sentences, query.getOneUser("i@i.it", "boia"));

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void randomMatch() {
        Integer[] id = {3, 4, 5};

        String sql = "INSERT INTO matches (state, creator_id, user_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", id);
            pstmt.setString(1, "e");   // Set state possible state running, ended, created, interrupted
            pstmt.setInt(2, 2); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers

            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE id= " + id);
            rs.next();
            System.out.println(rs.getString("name") + " " + rs.getString("surname"));
            if (rs.getString("role").equals("p")) {
                System.out.println("player");
                return new User(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("id"));
            } else {
                System.out.println("admin");
                return new User(rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getAllPlayer() {

        ArrayList<User> userList = new ArrayList<User>();
        Connection conn = null;
        try {
            conn = getConnectionInstance();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            //ciclo while che ritorna una COLONNA cioè lo stesso campo per tutte le righe
            // tipo tutti i nomi degli utenti
            /*se vuoi fare la get dell'id devi usare gli int*/
            int i = 0;
            while (rs.next()) {

                if (rs.getString("role").equals("p")) {
                    System.out.println("player");
                    User temp = new User(rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("mail"),
                            rs.getString("nickname"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getInt("id"));
                    userList.add(temp);
                }
                i++;
                System.out.println(rs.getString("name") + " " + rs.getString("surname") + " numero di riga " + i);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
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
                " ) " +
                "LIMIT 5";
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

    public void createMatch(Integer[] id, String matchName) {

        id = new Integer[]{3, 4, 5};

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

    public void endMatch(int matchId) {
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
}