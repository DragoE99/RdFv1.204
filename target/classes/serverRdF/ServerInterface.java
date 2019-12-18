package serverRdF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public int modifyName(String newUserName) throws RemoteException;
}
