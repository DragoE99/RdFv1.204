package playerRdF;

import serverRdF.GameInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameRmi{
    private Registry registry;
    private GameInterface stub;
    private String registryName;

    public GameRmi(String registryName){
        this.registryName=registryName;

        System.out.println("run gameRmi");
        try {
            Registry registry = LocateRegistry.getRegistry(4444);
            stub = (GameInterface) registry.lookup(this.registryName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void stampa(){

        Integer[] giocatori = getplayerId();
        System.out.println("giocatore 1 "+giocatori[0]+"\n giocatore 2 "+giocatori[1]);

    }
    public Integer[] getplayerId(){
        try {
            Integer[] test =stub.getplayerId();
            return test;

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
