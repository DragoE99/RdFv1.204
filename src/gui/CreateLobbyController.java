package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CreateLobbyController implements Initializable {

	private ImageView imgView;
	@FXML private Button back;
	
	@FXML private Node pane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Stage stage = Main.getStage();
		stage.setTitle("Create lobby");
		
		//imgView  = new ImageView(new Image("judo.jpeg"));
		//back = new Button("", imgView);

	}

	public void create(ActionEvent e) throws IOException {

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("WaitingRoom.fxml"))));
	}

	public void back(ActionEvent e) throws IOException {

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
	}

}
