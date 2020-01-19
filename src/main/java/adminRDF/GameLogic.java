package adminRDF;

import util.Actions;
import util.Match;

import java.util.Random;
/*TODO nel game controller inserire come variabili globali le variabili per lo stato
*  tra cui: currentPoint, tablePane, varie varabili FXML presenti nella schermata*/
public class GameLogic {
    Match match;
    Actions action = new Actions();
    int spinResult;

    //TODO stato del giocatore tipo quanti punti ha ..
    public void SpinWheel(){
        Random rand = new Random();
         spinResult = (rand.nextInt(9)-1)*100;
        if(spinResult<0){
            action.setActionName("LOSE ALL");
            //TODO completare inizializzazione action infilarla in match e chiamare il DB
            // controllare se si puo' usere il jolly
        }
        if(spinResult==0)action.setActionName("PASS");
    }

    public void giveSolution(){
        String solution=" da fare"; //fare la get da input di testo
        if(solution.equals(match.getCurrentSentence())){
            action.setActionName("SOLUTION");
            //finire la manche
        }
    }
    public void buyVowel(){
        /*if(points<1000)notEnoughPoints();
        * else {
        *   String solution=" da fare"; //fare la get da input di testo
        *   points-=1000;}*/
    }
    public void getConsonant(){

    }

}
