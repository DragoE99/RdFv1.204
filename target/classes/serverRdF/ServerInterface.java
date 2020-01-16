package serverRdF;

import util.Match;
import util.Sentence;
import util.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface ServerInterface extends Remote {
    int deleteUser(User user) throws RemoteException;
    /* LogIn releted method*/
    boolean logInCheck(String mail, String password) throws RemoteException;
    boolean checkMailExistence(String mail) throws RemoteException;
    boolean checkAdminExistence() throws RemoteException;
    boolean verificationCodeCheck(String verificationCode) throws RemoteException;
    boolean verificationMailCheck(String mail) throws RemoteException;

    /* Match related metod*/
    boolean matchNameCheck(String name) throws RemoteException;
    Match createMatch(Match match) throws RemoteException;
    Match updateMatch(Match match) throws RemoteException;
    Match addPlayer(Match match, User userToAdd) throws RemoteException;
    HashMap<String,  Match> getActiveMatch() throws RemoteException;

    /* Active Match related*/
    void updateActiveMatch(Match match) throws RemoteException;
    Match getMatchFromHash(String matchName) throws RemoteException;
    void addObserver(Match match, RemoteGameObserverInterface o, User observer) throws RemoteException;
    void removePlayer(User player, Match match, RemoteGameObserverInterface o) throws  RemoteException;
    void removeObserver(String matchName, RemoteGameObserverInterface o) throws  RemoteException;


    void insertSentences(List<Sentence> sentences, User user) throws RemoteException;
    int insertUser(User newUser) throws RemoteException;
    int modifyUser(User newUser) throws RemoteException;
    User getOneUser(String email, String password) throws RemoteException;



}
