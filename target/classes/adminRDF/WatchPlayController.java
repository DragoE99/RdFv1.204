package adminRDF;

import gui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import playerRdF.ClientRMI;
import util.Match;

import java.io.IOException;


public class WatchPlayController {

    @FXML
    Button PlayButton;
    @FXML
    Button WatchButton;
    @FXML
    Button CancelButton;
    @FXML
    Label SelectedMatch;

    static String response="";
    Match current;
   public void setSelectedMatch(Match match){
       current=match;
       SelectedMatch.setText("Selected Match "+match.getMatchName());
       if(match.getState().equals("R")){
           PlayButton.setDisable(true);
           PlayButton.setVisible(false);
       }
   }
    private void closeStage(ActionEvent event) {
        System.out.println(response);
        try {
            HomePageController.setActionOnMatch(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.equals("PLAY")||response.equals("WATCH")){
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/gui/WaitingRoom.fxml"));
                root.getStylesheets().add("/resources/PrimaryTheme.css");
                Main.getStage().setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();


    }
    @FXML
    public void setPlay(ActionEvent event){
        response="PLAY";
        if(ClientRMI.getInstance().addPlayer(current))System.out.println("Aggiunto: "+ClientRMI.getInstance().getUser().getName() );
        closeStage(event);
    }
    @FXML
    public void setWatch(ActionEvent event){
       ClientRMI.getInstance().addObserver(current);
        response="WATCH";
        closeStage(event);
    }
    @FXML
    public void setCancel(ActionEvent event){
        response="CANCEL";
        closeStage(event);
    }

}
