package adminRDF;

import gui.Main;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
import util.Actions;
import util.Match;
import util.User;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class GameController extends Pane implements RemoteGameObserverInterface , Serializable {

    @FXML
    Pane sentencePane;
    @FXML
    Label currentlyPlayingName;
    @FXML
    Label currentPalyerLocalWallet;
    @FXML
    Label currentPlayerGlobalWallet;
    @FXML
    Label lastPlayerName;
    @FXML
    Label lastPlayerLocalWallet;
    @FXML
    Label lastPlayerGlobalWallet;
    @FXML
    Label nextPlayerLocalWallet;
    @FXML
    Label nextPlayerGlobalWallet;
    @FXML
    Label nextPlayerName;
    @FXML
    TextField letterTextField;
    @FXML
    Label solutionLabel;

    @FXML
    Button spinButton;
    @FXML
    Button solutionButton;
    @FXML
    Button buyVowelButton;

    @FXML
    Label pointVariationLabel;

    @FXML
    Label hintLabel;
    @FXML
    Label resultLabel;

    private static Match currentMatch;
    ClientRMI currentClient;
    private static final long serialVersionUID = 1L;
    private static final int NUM_OF_ROW = 4;
    private static final int NUM_PER_ROW = 15;
    Pane pane;
    User currentlyPlaying;

    @Override
    public void update(Object observable, Object updateMsg) throws RemoteException {
        System.out.println("updated object from gameController");

        Platform.runLater(
                () -> {
                    // Update UI here.
                    currentMatch=currentClient.getMatch();
                    setPlayer();


                }
        );
    }

    public void updateUI(){
        setPlayer();
    }
    public void initialize() {
        /*
        * qui dentro verranno inizializzate le variabili tipo match
        *  e la frase da utilizzare per la manche
        * */

        System.out.println("entrato nella funzione");


        currentMatch= ClientRMI.getInstance().getMatch();
        setPlayer();
        pane=createContent();
        sentencePane.getChildren().addAll(pane);
        hintLabel.setText(currentMatch.getCurrentManche().getSentence().getHint());

    }

    private void setPlayer(){
        int i= currentMatch.getPlayerTurn();
        currentlyPlaying=currentMatch.getPlayers().get(currentMatch.getPlayerTurn());
        currentlyPlayingName.setText(currentMatch.getPlayers().get(currentMatch.getPlayerTurn()).getName());
        nextPlayerName.setText(currentMatch.getPlayers().get((currentMatch.getPlayerTurn()+1)%3).getName());
        lastPlayerName.setText(currentMatch.getPlayers().get((currentMatch.getPlayerTurn()+2)%3).getName());
        if(currentMatch.getManches().size()<1){
            currentPlayerGlobalWallet.setText(""+0);
            nextPlayerGlobalWallet.setText(""+0);
            lastPlayerGlobalWallet.setText(""+0);
        }else{
            Integer[] wallet= currentMatch.getGlobalWallets();
            currentPlayerGlobalWallet.setText(""+wallet[i]);
            nextPlayerGlobalWallet.setText(""+wallet[(i+1)%3]);
            lastPlayerGlobalWallet.setText(""+wallet[(i+2)%3]);
        }
        currentPalyerLocalWallet.setText(""+currentMatch.getCurrentManche().getPlayerWallet()[i]);
        nextPlayerLocalWallet.setText(""+currentMatch.getCurrentManche().getPlayerWallet()[(i+1)%3]);
        lastPlayerLocalWallet.setText(""+currentMatch.getCurrentManche().getPlayerWallet()[(i+2)%3]);
    }

    //TODO eliminare
    public void rotatePlayer(){
        String temp = currentlyPlayingName.getText();
        currentlyPlayingName.setText(nextPlayerName.getText());
        nextPlayerName.setText(lastPlayerName.getText());
        lastPlayerName.setText(temp);

    }
    public Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        char[][] test= sentenceTokenizer(currentMatch.getCurrentManche().getSentence().getSentence());


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

        //TODO da eliminare
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
    public void revealLetter(String letter){
        for(Node n : pane.getChildren()){
            Tile tile = (Tile) n;
            if(tile.getText().getText().equals(letter)){
                if(!tile.isOpen())tile.open(() -> {});
                else resultLabel.setText("Letter Already Present");
            }else resultLabel.setText("Letter not Present");
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
            try {
                char[] row= panelRow[j].toCharArray();
                for (int k=0;k<row.length;k++) {
                    table[j][k]=row[k];
                }
            } catch (Exception e) {
               // e.printStackTrace();
            }
        }
        return table;
    }

    public void quit() throws IOException {
        ClientRMI.getInstance().removePlayer();
        Parent root = FXMLLoader.load(getClass().getResource("/adminRDF/HomePage.fxml"));
        root.getStylesheets().add("/resources/PrimaryTheme.css");
        Main.getStage().setScene(new Scene(root));
    }

    Actions action = new Actions();
    @FXML
    public void spinWheel(){
        Random rand = new Random();
        int spinResult = (rand.nextInt(9)-1)*100;
        if(spinResult<0){
            action.setActionName("LOSE ALL");
            //set variable currently player tipo user
            pointVariationLabel.setText("");
            //TODO completare inizializzazione action infilarla in match e chiamare il DB
            // controllare se si puo' usere il jolly
        }
        if(spinResult==0){
            action.setActionName("PASS");
            pointVariationLabel.setText("PASS");
        }else{
            pointVariationLabel.setText(""+spinResult);
            String consonant = openInsertDialog("Insert Consonant").toUpperCase();
            if(consonant.equals("")){
                action.setActionName("PASS");
                resultLabel.setText("PASS");
            }else{
                if(consonant.length()>1)consonant=consonant.substring(0,1);
                revealLetter(consonant);
                action.setLetter(consonant);
            }

        }
        letterTextField.setDisable(false);
        letterTextField.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null ;
            } else {
                revealLetter(change.getControlNewText().toUpperCase());
                action.setLetter(change.getControlNewText());
                return change ;
            }
        }));
    }

    @FXML
    public void giveSolution(){
        solutionLabel.setText(openInsertDialog("Insert Solution").toUpperCase());
       String rightSolution =new String(currentMatch.getCurrentSentence());
        System.out.println("frase immessa ");
        System.out.println("frase attuale "+rightSolution);

        if(currentMatch.getCurrentSentence().equals(solutionLabel.getText())){
            action.setActionName("SOLUTION");
            resultLabel.setText("Correct solution "+currentlyPlaying.getName()+" WINS");
            //finire la manche
        }else action.setActionName("PASS");
        resultLabel.setText("Wrong solution "+currentlyPlaying.getName()+" PASS");
        resultLabel.setTextFill(Color.RED);
    }

    public void buyVowel(){
        /*if(points<1000)notEnoughPoints();
         * else {
         *   String solution=" da fare"; //fare la get da input di testo
         *   points-=1000;}*/
    }

    @FXML
    TextField testTextField;
    public void getTestText(){
        if(currentMatch.getCurrentSentence().equals(testTextField.getText().toUpperCase())){System.out.println("uguali");}

    }


    public String openInsertDialog(String nameToDisplay){
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/adminRDF/InsertDialog.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
            parent.getStylesheets().add("/resources/PrimaryTheme.css");
            InsertDialogController dialogController = fxmlLoader.<InsertDialogController>getController();
            dialogController.setThingsToInsertLabel(nameToDisplay);
            String diritorno =dialogController.display(nameToDisplay, parent);
            System.out.println(diritorno);
            return diritorno;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
