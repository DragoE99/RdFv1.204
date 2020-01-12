package adminRDF;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import playerRdF.ClientRMI;
import serverRdF.RemoteGameObserverInterface;
import util.Match;
import util.StringManager;
import util.User;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.StringTokenizer;

public class GameController extends Pane implements RemoteGameObserverInterface , Serializable {

    @FXML
    Pane sentencePane;
    @FXML
    Label currentlyPlaying;
    @FXML
    Label lastPlayer;
    @FXML
    Label nextPlayer;
    @FXML
    TextField testTextField;

    VBox vboxtest;

    private static Match currentMatch;
    ClientRMI currentClient;
    private static final long serialVersionUID = 1L;

    private static final int NUM_OF_ROW = 4;
    private static final int NUM_PER_ROW = 15;
    Pane pane;
   /* public static void main(String[] args) {

       launch(args);
    }*/

  /*  public GameController(){
    }*/
    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        System.out.println("updated object from gameController");
        currentMatch=currentClient.getMatch();
    }
  /*  @Override
    public void start(Stage primaryStage) throws IOException {
        //sentenceTokenizer("VINSERO BATTAGLIE GRAZIE ALLA LORO FUGA");
        ArrayList<Integer> creatorId = new ArrayList<>();
        creatorId.add(3);
        Match match= new Match(creatorId,"secondoMatch");
        ClientRMI client;
        try {

            client = ClientRMI.getInstance();
            currentClient=client;
            client.setMatch(match);
            if(client.createMatch(match)){
                System.out.println("nome esistente");
                *//*TODO durante il check del nome inserire in active match un match vuoto con tale key
                 *  e fare il check con active match e non il DB *//*
                match=client.getMatch();

            }else {
                match= client.updateMatch(match);
                match.addPlayer(4);
                System.out.println("aggiunto player 4 lunghezza: "+match.getId_players().size());
                client.addObserver(match);
                client.getMatch().addObserver(this);
                client.updateActiveMatch(match);
                match= client.updateMatch(match);
                System.out.println("match updated");
            }
            //TODO when adding a player do this
            *//*
            currentMatch.getCurrentManche().setPlayers(me);
            currentMatch.getId_players().add(myID); // variabile 'myId' sta per il l-id dell'utente corrente
            if(client.addPlayer(match)){
                client.getMatch().addObserver(this);
                currentMatch=client.getMatch();
                //go to waiting room
            }*//*


        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(match!=null)
            System.out.println("match id: "+ match.getIdMatch());

        Parent root = FXMLLoader.load(getClass().getResource("GameGui.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //new Scene(createContent())

    }*/


    public void initialize() {
        /*
        * qui dentro verranno inizializzate le variabili tipo match
        *  e la frase da utilizzare per la manche
        * */

        System.out.println("entrato nella funzione");
        pane=createContent();
        sentencePane.getChildren().addAll(pane);
        //labelTest = new Label();
        currentlyPlaying.setText("Jotaro");
        nextPlayer.setText("Dio");
        lastPlayer.setText("Joseph");

    }

    public void rotatePlayer(){
        String temp = currentlyPlaying.getText();
        currentlyPlaying.setText(nextPlayer.getText());
        nextPlayer.setText(lastPlayer.getText());
        lastPlayer.setText(temp);

    }
    public Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        char[][] test= sentenceTokenizer("VINSERO BATTAGLIE GRAZIE ALLA LORO FUGA");


        for (int j=0;j<4;j++) {

            for (int i=0;i<NUM_PER_ROW;i++) {
                Tile tile =  new Tile(String.valueOf(test[j][i]));
                tile.setTranslateX(50 * ( i % NUM_PER_ROW));
                tile.setTranslateY(60 * (j));
                root.getChildren().add(tile);
            }
        }

        pane=root;
        return root;
    }
    public class Tile extends StackPane {
        private Text text = new Text();

        public Tile(String value) {
            Rectangle border = new Rectangle(50, 60);
            border.setFill(Paint.valueOf("#676b96"));
            border.setStroke(Color.BLACK);

            if(value.equals("\u0000")|| value.equals(" ")){
                System.out.println("valorae null dela tile");
                border.setFill(Paint.valueOf("#9699c7"));
            }else {
                text.setText(value);
                text.setFont(Font.font(30));
                text.setFill(Color.WHITE);
                System.out.println("valore: "+value);
            }

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            //setOnMouseClicked(this::handleMouseClick);
            //close();
        }

        public Text getText() {
            return text;
        }
    }
    public void hideLetter(ActionEvent e){
        System.out.println("entrato nella funzione");
        for(Node n : pane.getChildren()){
            Tile tile = (Tile) n;
            System.out.println("lettera corretta"+ tile);
            if(tile.getText().getText().equals("A")){
                System.out.println("lettera corretta indice  ");
                tile.getText().setOpacity(0);
            }
        }

    }

    public char[][] sentenceTokenizer(String sentence){

        StringTokenizer tok = new StringTokenizer(sentence, " ");


        String word;
        String nextWord = new String();
        ArrayList<String> wordList = new ArrayList<>();
        String[] panelRow = new String[4];
        panelRow[0]= "";
        int i=0;
        while (tok.hasMoreTokens()){
            word= tok.nextToken();
            //wordList.add(tok.nextToken());
            if(panelRow[i].length()+word.length()<15){
                panelRow[i]+=word+" ";
            }else{
                i++;
                panelRow[i]= word+" ";
            }
        }

        char[][] table =new char[NUM_OF_ROW][NUM_PER_ROW];

        for (int j=0;j<4;j++) {
            char[] row= panelRow[j].toCharArray();
            for (int k=0;k<row.length;k++) {
                table[j][k]=row[k];
            }
        }
        return table;
    }


}
