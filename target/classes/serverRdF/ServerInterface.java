package serverRdF;

import util.Sentence;
import util.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface ServerInterface extends Remote {
    int deleteUser(User user) throws RemoteException;
    boolean verificationCodeCheck(String verificationCode) throws RemoteException;
    boolean verificationMailCheck(String mail) throws RemoteException;
    boolean matchNameCheck(String name) throws RemoteException;
    boolean logInCheck(String mail, String password) throws RemoteException;
    boolean checkMailExistence(String mail) throws RemoteException;
    boolean checkAdminExistence() throws RemoteException;
    void insertSentences(List<Sentence> sentences, User user) throws RemoteException;
    int insertUser(User newUser) throws RemoteException;
    int modifyUser(User newUser) throws RemoteException;
    User getOneUser(String email, String password) throws RemoteException;



}
