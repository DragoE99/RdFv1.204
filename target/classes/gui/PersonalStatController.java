package gui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;

/**
 * The controller for personal statistics window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class PersonalStatController {

	//TODO implementare tutta la parte delle statistiche
	@FXML private ImageView back;
	
	@FXML private ListView<String> list;

	
	
	/**
	 * 
	 */
	public void initialize() {
		ArrayList<String> stats = null;
		try {
			stats = Client.getProxy().userStats(Client.getUser().getId());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObservableList<String> ob = FXCollections.observableArrayList(stats);
		
		list.setItems(ob);
		
	}
	
	
	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Statistics.fxml"))));
		}
	}

}
