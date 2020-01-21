package util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import serverRdF.DataBaseConnection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DatabasePopulator {
    public DatabasePopulator() {
    }

    private String ipAddress = "localhost";
    private String port = "5432";
    private String dbName = "brandodb";

    private Connection getConnectionInstance() throws SQLException {

        //System.out.println("prima della connessione");
        String url = "jdbc:postgresql://" + ipAddress + ":" + port + "/" + dbName;
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");
        Connection connection = DriverManager.getConnection(url, props);
        //System.out.println("connessione avvenuta");
        return connection;
    }
    public static void main(String args[]) throws IOException {
        DatabasePopulator test = new DatabasePopulator();
        //test.populate();
        //test.deletePopulate();
        test.getGlobalStat();
        //test.getUserStat(11);
        //test.insertManche(1,35,82);
        //test.insertManche(2,35,83);
        //test.deleteManche(35,1);

    }

    public ArrayList<String> getUserStat(Integer idUser){
        ArrayList<String> userStat= new ArrayList<>();
        int mancheWon =statisticQuery(idUser, "manche_won", "player_id","count");
        int loseCount = statisticQuery(idUser, "lose_all_count", "player_id","count");
        int passCount = statisticQuery(idUser, "pass_count", "player_id","count");
        int playedMatch = statisticQuery(idUser, "played_match", "player_id","count");
        int playedManches = statisticQuery(idUser, "played_manche", "player_id","count");
        int avgMatchPoints = statisticQuery(idUser, "avg_match_points", "player_id","user_point");
        int avgManchePoints = statisticQuery(idUser, "avg_manche_points", "player_id","user_point");
        int averageLoseMatch= loseCount/playedMatch;
        int averageLoseManches= loseCount/playedManches;
        int averagePassMatch = passCount/playedMatch;
        int averagePassManche = passCount/playedManches;
        String mancheWonString= new String("Number of manches won: "+mancheWon);

        String mancheSeen = new String("Number of manches seen: " +
                statisticQuery(idUser, "manche_seen", "player_id", "count"));

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

        return globalStat;
    }
    public String globalStatisticExecutor(String qry, String columnCountName){

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
    public int statisticQuery(Integer userId, String viewName, String columnIdName, String columnCountName){
        String qry ="SELECT * FROM "+viewName+" WHERE "+columnIdName+" = "+userId;
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(columnCountName);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }




    public void deletePopulate(){
        DatabasePopulator test = new DatabasePopulator();
        /* to unlock when match already created*/
        for(int matchNumber=0; matchNumber<5;matchNumber++){
            test.deleteMatchFromName("match"+matchNumber);
        }

    }

    public void testView(){
        String qry= "SELECT * FROM played_match ";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println("id utente: "+rs.getInt("id"));
                System.out.println("conteggio match giocati: "+ rs.getInt("count"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            //return null;
        }
    }


    public void populate(){
        DatabasePopulator test = new DatabasePopulator();
        ArrayList<Integer> players = test.getAllPlayer();
        ArrayList<Integer> createdMatchIds = new ArrayList<>();
        ArrayList<Integer> sentencesIds= test.getAllSentenceIds();
        Random rand = new Random();
        Integer[][] matchPlayer= new Integer[5][3];
        int[] mancheSentence = new int[5];
        Integer[] lastWallet;



        //create 5 match already ended
        for(int matchNumber=0; matchNumber<5;matchNumber++){
            //select 3 random player id from all avaiable player
            for(int i=0; i<3; i++){
                matchPlayer[matchNumber][i]= players.get(rand.nextInt(players.size()));
            }
            Integer currentMatchId=test.createEndedMatch(matchPlayer[matchNumber],"match"+matchNumber);
            System.out.println(""+createdMatchIds);
            createdMatchIds.add(currentMatchId);
        }
        //create 5 manche for every match with not so random data
        for (Integer matchID:createdMatchIds) {
            Integer currentMatch = createdMatchIds.indexOf(matchID);
            System.out.println("currentMatch variable value: "+currentMatch);
            for(int i=0; i<5; i++){
                mancheSentence[i]= sentencesIds.get(rand.nextInt(sentencesIds.size()));
            }
            lastWallet=new Integer[] {0,0,0};
            for(int mancheNumber=1; mancheNumber<6; mancheNumber++){
                lastWallet[(mancheNumber-1)%3]+=mancheNumber*500;
                test.insertManche(mancheNumber,
                        matchID,
                        mancheSentence[currentMatch],
                        matchPlayer[currentMatch],
                        lastWallet);

                //create Acton
                Integer playerTurn= (mancheNumber)%3;
                int winnerCount=0;
                Random point = new Random();
                for(int turn=1;winnerCount<3; turn++){
                    //(point.nextInt(7)+1)*100;
                    if(playerTurn==1){
                        int gainedPoint= winnerCount==2? 500:1000;
                        String actionName= winnerCount!=2? "CONSONANT": "SOLUTION";
                        String letter= winnerCount!=2? (winnerCount==0?"S":"N") : "";
                        test.insertAction(turn,matchPlayer[currentMatch][playerTurn],
                                actionName,false,gainedPoint,playerTurn,
                                letter,matchID,mancheNumber );
                        winnerCount++;
                    }else {
                        int gainedPoint= (point.nextInt(9)-2)*100;
                        String actionName= gainedPoint!=0? "CONSONANT": "PASS";
                        if(gainedPoint==-200){actionName="RECIVE_JOLLY"; gainedPoint=0;}
                        if(gainedPoint==-100){actionName="LOSE"; gainedPoint=0;}
                        if(gainedPoint==700){actionName="USE_JOLLY"; gainedPoint=0;}
                        String letter= gainedPoint>0? randomConsonant() : "";
                        test.insertAction(turn,matchPlayer[currentMatch][playerTurn],
                                actionName,actionName.equals("RECIVE_JOLLY"),gainedPoint,playerTurn,
                                letter,matchID,mancheNumber );
                    }
                    playerTurn= (playerTurn+point.nextInt(2))%3;
                }
            }
        }


    }

    public String randomConsonant(){
        String[] consonant= {"Q","W","R","T","Y","P","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
        Random rand = new Random();
        return consonant[rand.nextInt(consonant.length)];
    }

    public void insertAction(Integer turn, Integer playerId,
                             String actionName, boolean jolly,
                             Integer actionWallet, Integer playerNumber,
                             String letterCalled, Integer matchId,
                             Integer mancheNumber){
        String qry= "INSERT INTO actions(turn, player_id, " +
                "action_name, jolly, action_wallet, player_number, letter_called, match_id, manche_number) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {
            pstmt.setInt(1, turn);
            pstmt.setInt(2,playerId);
            pstmt.setString(3,actionName);
            pstmt.setBoolean(4,jolly);
            pstmt.setInt(5,actionWallet);
            pstmt.setInt(6,playerNumber);
            pstmt.setString(7, letterCalled);
            pstmt.setInt(8, matchId);
            pstmt.setInt(9,mancheNumber);
            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteManche(Integer match_id, Integer numberOfManche){
        deleteQuery("manches", new String[]{"match_id", "number"}, new String[]{match_id.toString(), numberOfManche.toString()});
    }
    /**Delete a match from database identified by the match name*/
    public void deleteMatchFromName(String matchName){
        deleteQuery(StringManager.getString("matches_table_name"),
                new String[]{StringManager.getString("match_name_column_name")},
                new String[]{matchName});
    }
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
    public void insertManche(int number, int matchId, int sentenceId, Integer[] seenBy, Integer[] wallets){
        /*campi Manche on database:
         number= number of mache frmon 1 to 5,
         match_id,
         sentence_id,
         seen_by_user[]= user that have seen that manche,
         manche_wallets[] = global wallet for that manche*/
        String qry= "INSERT INTO manches(number, match_id, sentence_id,seen_by_user, manche_wallets) VALUES (?,?,?,?,?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry);) {
            Array seenByArray= conn.createArrayOf("INTEGER", seenBy);
            Array mWallets= conn.createArrayOf("INTEGER", wallets);
            pstmt.setInt(1, number); //TODO metodo che ritorna la manche Corrente
            pstmt.setInt(2,matchId);
            pstmt.setInt(3,sentenceId); //TODO metodo che ritorna la frase associata al match corrente (o almeno il suo id)
            pstmt.setArray(4, seenByArray);
            pstmt.setArray(5,mWallets);
            pstmt.executeUpdate();  // Execute the query

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public synchronized Integer createEndedMatch(Integer[] id, String matchName) {
        String sql = "INSERT INTO matches (state, creator_id, user_id, match_name) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", id);
            pstmt.setString(1, StringManager.getString("match_state_ended_convention"));   // Set state
            pstmt.setInt(2, id[0]); //set creator id
            pstmt.setArray(3, array);  // Set ID palyers
            pstmt.setString(4, matchName);	//set match name

            pstmt.executeUpdate();  // Execute the query
            return getMatchbyName(matchName);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    private int getMatchbyName(String matchName) {
        String qry = "SELECT * FROM matches WHERE match_name = ?";
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1, matchName);
            ResultSet rs = pstmt.executeQuery();  // Execute the query
            rs.next();
            return rs.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public ArrayList<Integer> getAllPlayer(){
        ArrayList<Integer> allPlayer= new ArrayList<>();
        try(Connection conn = getConnectionInstance();
        Statement st = conn.createStatement()){
        ResultSet rs = st.executeQuery("SELECT * FROM users WHERE role = 'p'");
        while (rs.next()) {
           /*allPlayer.add( new Player(
            rs.getString("name"),
            rs.getString("surname"),
            rs.getString("mail"),
            rs.getString("nickname"),
            rs.getString("password"),
            rs.getInt("id")));*/
            System.out.println(rs.getString("name") + " " + rs.getString("surname"));
            allPlayer.add(rs.getInt("id"));
        }
        return allPlayer;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<Integer> getAllSentenceIds() {
        String SQL = "SELECT * FROM sentences";
        ArrayList<Integer> sentenceList = new ArrayList<>();
        try (Connection conn = getConnectionInstance()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()) {
                sentenceList.add(rs.getInt("id"));
            }
            return sentenceList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
   /* public void updateManche(Match match){
        String sql = "UPDATE matches " +
                "SET seen_by_user = ?, " +
                "manche_wallet_p1 = ? " +
                "manche_wallet_p2 = ? " +
                "manche_wallet_p3 = ? " +
                "WHERE id = ?";

        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            Array array = conn.createArrayOf("INTEGER", match.getId_players());
            pstmt.setArray(1, array);  // Set ID palyers
            pstmt.setInt(2, match.getMancheScore()[]);   // Set state possible state running, ended, created, interrupted
            pstmt.setInt(2, match.getSentence().getId()); //set creator id

            pstmt.executeUpdate();  // Execute the query
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/

    public void insertUser() {

        String SQL = "INSERT INTO users (name, surname, mail, password, nickname, role) VALUES(?, ?, ?, ?, ?, ?)";
        int affectedrows = 0;
        try (Connection conn = getConnectionInstance();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            int i = 0;
            for (i = 0; i < 20; i++) {
                User newUser = new Player("utente" + i, "cognome" + i, i + "user@gmil.com", "number" + i, "1234");
                pstmt.setString(1, newUser.getName());
                pstmt.setString(2, newUser.getSurname());
                pstmt.setString(3, newUser.getEmail());
                pstmt.setString(4, newUser.getPassword());
                pstmt.setString(5, newUser.getNickname());
                if (newUser instanceof Admin) {
                    pstmt.setString(6, "a");
                } else if (newUser instanceof Player) {
                    pstmt.setString(6, "p");
                } else {
                    pstmt.setString(6, "u");
                }
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
            try {

                query.insertSentences(sentences, query.getOneUser("i@i.it", "boia"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

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


    public Match updateMatch(Integer[] playerId, int machId) {
        String qry = "UPDATE matches " +
                "SET user_id = ? ," +
                " state = ? " +
                "WHERE id = ?";
        Match toUpdate = new Match();
        if (playerId.length == 3)
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
