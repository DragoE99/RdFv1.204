package adminRDF;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import playerRdF.ClientRMI;
import util.Match;
import util.User;

import java.io.IOException;
import java.util.HashMap;

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
            if (m.getState().equals("c")){
                System.out.println("match aggiunto a playMatch");
                playMatch.add(m);}
            else watchMatch.add(m);*/
        }
        }else System.out.println("hash map vuota");


        playMatchName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Match, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Match, String> param) {
                System.out.println("dentro set value factory");
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
                String temp = param.getValue().getState().equals("c")? "Play":"Watch";
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

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
