package serverRdF;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMI extends Thread  implements ServerInterface {
    public ServerRMI(){}
    ServerRMI obj;
    ServerInterface stub;
    Registry registry;


    public static void main(String[] args) {
        ServerRMI test = new ServerRMI();
        test.start();
    }
    @Override
    public void run() {
        System.out.println(" stampa di prova");
        //System.setProperty("java.rmi.server.hostname","18.5.28.53");
        try {

            obj = new ServerRMI();
            stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);
            registry = LocateRegistry.createRegistry(3333);
            registry.rebind("diocane", stub);

            System.out.println(" ** Server pronto");
        } catch (Exception e) {
            System.err.println(" ***** Server Error");
            e.printStackTrace();
            System.exit(0);
        }
    }
    @Override
    public int modifyName(String newUserName) {
        DataBaseConnection DB = new DataBaseConnection();
        return DB.modifyName(newUserName);
    }
}
