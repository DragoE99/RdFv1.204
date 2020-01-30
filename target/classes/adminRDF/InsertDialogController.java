package adminRDF;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import playerRdF.ClientRMI;

import java.io.IOException;


public class InsertDialogController {
    @FXML
    Label thingsToInsertLabel;

    @FXML
    TextField textInsertedTextField;

    static String textInserted;

    public String display(String title, Parent parent) throws IOException {
        Scene scene = new Scene(parent,300, 200);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.initStyle(StageStyle.UTILITY);
        // Set up the JavaFX button controls and listeners and the text fields
        window.setScene(scene);
        window.showAndWait();
        return textInserted;
    }
    @FXML
    public void setTextInserted(ActionEvent event){
        textInserted = textInsertedTextField.getText().toUpperCase();
        if(thingsToInsertLabel.getText().equals("Insert Solution")){
        GameController.setPlayerSol(ClientRMI.getInstance().getMatch().getCurrentSentence().equals(textInserted));

        }
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void setThingsToInsertLabel(String string){
        thingsToInsertLabel.setText(string);
    }

    @FXML
    public void quit(ActionEvent event){
        textInserted="";
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
