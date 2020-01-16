package adminRDF;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import playerRdF.ClientRMI;
import serverRdF.RemoteGameObserverInterface;
import util.Match;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        System.out.println("updated object from gameController");
        currentMatch=currentClient.getMatch();
    }

    public void initialize() {
        /*
        * qui dentro verranno inizializzate le variabili tipo match
        *  e la frase da utilizzare per la manche
        * */

        System.out.println("entrato nella funzione");
        pane=createContent();
        sentencePane.getChildren().addAll(pane);
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
                text.setOpacity(0);
                System.out.println("valore: "+value);
            }

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(this::handleMouseClick);
            close();
        }

        public void handleMouseClick(MouseEvent event) {
            if (isOpen())close();
            else open(() -> {});
        }

        public boolean isOpen() {
            return text.getOpacity() == 1;
        }
        public void open(Runnable action) {
            RotateTransition rt = new RotateTransition(Duration.seconds(0.5), this);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.7), text);
            rt.setAxis(Rotate.Y_AXIS);
            rt.setByAngle(180);
            rt.setCycleCount(1);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            rt.play();
            ft.play();
        }

        public void close() {
            RotateTransition rt = new RotateTransition(Duration.seconds(0.5), this);
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
            rt.setAxis(Rotate.Y_AXIS);
            rt.setByAngle(180);
            rt.setCycleCount(1);
            ft.setToValue(0);
            rt.play();
            ft.play();
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
