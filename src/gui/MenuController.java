package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
	
	@FXML private Node pane;
	
	public void play(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
//		Stage loginStage = (Stage) pane.getScene().getWindow();
//		loginStage.close();
//		Stage primaryStage = new Stage();
//		Parent root = FXMLLoader.load(getClass().getResource("SelectLobby.fxml"));
//		Scene scene = new Scene(root);
//		primaryStage.setScene(scene);
//		primaryStage.show();
	}
	public void statistics(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Statistics.fxml"))));
	}
	public void settings(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml"))));
	}

}
