package adminRDF;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.Match;


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

   /* Match toShow;
    User current;

    public String response() throws IOException {

        Stage window= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("WatchPlayDialog.fxml"));
        root.getStylesheets().add("/resources/PrimaryTheme.css");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(new Scene(root));
        window.show();
        return response;
    }

    public void initialize(){
        if(match.getState().equals("r")|| current.getRole().equals("a")){
            PlayButton.setDisable(true);
            PlayButton.setVisible(false);
        }
    }*/
   public void setSelectedMatch(Match match){
       SelectedMatch.setText("Selected Match "+match.getMatchName());
       if(match.getState().equals("r")){
           PlayButton.setDisable(true);
           PlayButton.setVisible(false);
       }
   }
    private void closeStage(ActionEvent event) {
        System.out.println(response);
        HomePageController.actionOnMatch=response;
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void setPlay(ActionEvent event){
        response="PLAY";
        closeStage(event);
    }
    @FXML
    public void setWatch(ActionEvent event){
        response="WATCH";
        closeStage(event);
    }
    @FXML
    public void setCancel(ActionEvent event){
        response="CANCEL";
        closeStage(event);
    }

}
