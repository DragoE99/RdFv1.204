package adminRDF;

import gui.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import playerRdF.ClientRMI;
import util.Match;
import util.StringManager;
import util.User;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HomePageController {
    @FXML
    TableView<Match> playableMatchList;
    @FXML
    TableView<Match> watchMatchList;
    @FXML
    TableColumn<Match, String> playMatchName;
    @FXML
    TableColumn<Match, Integer> playMatchPlayer;
    @FXML
    TableColumn<Match, String> playOrWatch;
    //@FXML
    //TableColumn<Match, Integer> watchMatchManche;

    @FXML
    Label newGameLabel;

    @FXML Label settingsLabel;

    static String actionOnMatch=null;


    HashMap<String, Match> matchList;
    public ObservableList<Match> playMatch = FXCollections.observableArrayList();


    public void initialize(){
        //TODO fare una lista sola di match e mischiare giocabili e non? con colonna in piu' con play o watch
        ClientRMI clientRMI = ClientRMI.getInstance();
        matchList= clientRMI.getActiveMatch();
        if(matchList!=null) {
            for (String key: matchList.keySet()) {
                playMatch.add(matchList.get(key));
           /* Match m =matchList.get(key);
            if (m.getState().equals("C")){
                System.out.println("match aggiunto a playMatch");
                playMatch.add(m);}
            else watchMatch.add(m);*/
        }
        }else System.out.println("hash map vuota");


        playMatchName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Match, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Match, String> param) {
                //System.out.println("dentro set value factory");
                return new ReadOnlyObjectWrapper(param.getValue().getMatchName());
            }
        });
        playMatchPlayer.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Match, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Match, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getPlayers().size());
            }
        });

        playOrWatch.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Match, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Match, String> param) {
                String temp = param.getValue().getState().equals(StringManager.getString("match_state_created_convention"))? "Play":"Watch";
                return new ReadOnlyObjectWrapper<>(temp);
            }
        });
        /*watchMatchManche.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Match, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Match, Integer> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getManches().size());
            }
        });*/
        playableMatchList.setItems(playMatch);
    }

    @FXML
    public void clickItem(MouseEvent event){
        if (event.getClickCount() == 2){ //Checking double click
            //TODO apertura Dialog Play or Watch if possible + passagggio alla waiting room
            System.out.println(playableMatchList.getSelectionModel().getSelectedItem().getMatchName());
            Match m=playableMatchList.getSelectionModel().getSelectedItem();
            ClientRMI.getInstance().getMatchFromHash(m.getMatchName());
            for (User u : m.getPlayers()
                 ) {
                System.out.println("player id: "+ u.getId());
                System.out.println("player name: "+ u.getName());
            }
            try {
                openDialog(m);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(actionOnMatch!= null)System.out.println("Action on match: "+ actionOnMatch);
            //todo set player/observer and go to waiting room or not

        }
    }

    void openDialog(Match m) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("WatchPlayDialog.fxml"));
        Parent parent = fxmlLoader.load();
        parent.getStylesheets().add("/resources/PrimaryTheme.css");
        WatchPlayController dialogController = fxmlLoader.<WatchPlayController>getController();
        dialogController.setSelectedMatch(m);
        ClientRMI.getInstance().setMatch(m);
        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void newGameDialog(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/gui/CreateMatch.fxml"));
        Parent parent = fxmlLoader.load();
        parent.getStylesheets().add("/resources/PrimaryTheme.css");
        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void refreshMatch(){
        ClientRMI clientRMI = ClientRMI.getInstance();

        matchList= clientRMI.getActiveMatch();
        playableMatchList.getItems().clear();
        if(matchList!=null) {
            for (String key: matchList.keySet()) {
                playMatch.add(matchList.get(key));
            }
        }else System.out.println("hash map vuota");
        playableMatchList.setItems(playMatch);


    }
    public static void setActionOnMatch(String action) throws IOException {
        actionOnMatch=action;
        /*if(actionOnMatch.equals("PLAY")||actionOnMatch.equals("WATCH")){
            Parent root = FXMLLoader.load(HomePageController.class.getResource("/gui/WaitingRoom.fxml"));
            root.getStylesheets().add("/resources/PrimaryTheme.css");
            Main.getStage().setScene(new Scene(root));
        }*/
    }

    public void goToSettings() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Settings.fxml"));
        root.getStylesheets().add("/resources/PrimaryTheme.css");
        gui.Main.getStage().setScene(new Scene(root));
    }
    public void quitApplication(){
        Main.getStage().close();
        Platform.exit();
    }
}
