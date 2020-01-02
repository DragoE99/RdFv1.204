package serverRdF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {
    Integer[] getplayerId() throws RemoteException;
}
