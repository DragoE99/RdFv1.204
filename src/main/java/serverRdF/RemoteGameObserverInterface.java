package serverRdF;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteGameObserverInterface extends Remote {
    void update(Object observable, Object updateMsg) throws RemoteException;
}
