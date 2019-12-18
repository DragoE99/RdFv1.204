package playerRdF;

import gui.Main;
import javafx.application.Application;
import serverRdF.ServerInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {
    private Registry registry;
    private ServerInterface stub;

    public static void main(String[] args) throws IOException {
        ClientRMI test = new ClientRMI();
        test.modifyName("incredibile");       																	//launching the proxy thread// launching the GUI

    }

    public ClientRMI() {

        try {
            Registry registry = LocateRegistry.getRegistry(3333);
            stub = (ServerInterface) registry.lookup("diocane");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int modifyName(String newUserName) {

        int x=0;
        try {
            x = stub.modifyName("pippo");
            System.out.println("ottimo ritornato "+x );
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;
    }
}
