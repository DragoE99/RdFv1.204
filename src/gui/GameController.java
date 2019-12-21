package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import adminRdF.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import util.Sentence;

public class GameController implements Initializable {
	
	
	@FXML Label label0_0; 
	@FXML Label label0_1; 
	@FXML Label label0_2; 
	@FXML Label label0_3; 
	@FXML Label label0_4; 
	@FXML Label label0_5; 
	@FXML Label label0_6; 
	@FXML Label label0_7;
	@FXML Label label0_8; 
	@FXML Label label0_9; 
	@FXML Label label0_10; 
	@FXML Label label0_11; 
	@FXML Label label0_12; 
	@FXML Label label0_13; 
	@FXML Label label0_14;
	@FXML Label label1_0; 
	@FXML Label label1_1; 
	@FXML Label label1_2; 
	@FXML Label label1_3; 
	@FXML Label label1_4; 
	@FXML Label label1_5; 
	@FXML Label label1_6; 
	@FXML Label label1_7;
	@FXML Label label1_8; 
	@FXML Label label1_9; 
	@FXML Label label1_10; 
	@FXML Label label1_11; 
	@FXML Label label1_12; 
	@FXML Label label1_13; 
	@FXML Label label1_14;
	@FXML Label label2_0; 
	@FXML Label label2_1; 
	@FXML Label label2_2; 
	@FXML Label label2_3; 
	@FXML Label label2_4; 
	@FXML Label label2_5; 
	@FXML Label label2_6; 
	@FXML Label label2_7;
	@FXML Label label2_8; 
	@FXML Label label2_9; 
	@FXML Label label2_10; 
	@FXML Label label2_11; 
	@FXML Label label2_12; 
	@FXML Label label2_13; 
	@FXML Label label2_14;
	@FXML Label label3_0; 
	@FXML Label label3_1; 
	@FXML Label label3_2; 
	@FXML Label label3_3; 
	@FXML Label label3_4; 
	@FXML Label label3_5; 
	@FXML Label label3_6; 
	@FXML Label label3_7;
	@FXML Label label3_8; 
	@FXML Label label3_9; 
	@FXML Label label3_10; 
	@FXML Label label3_11; 
	@FXML Label label3_12; 
	@FXML Label label3_13; 
	@FXML Label label3_14;
	
	
	
	@FXML Pane  pane0_0; 
	@FXML Pane pane0_1; 
	@FXML Pane pane0_2; 
	@FXML Pane pane0_3; 
	@FXML Pane pane0_4; 
	@FXML Pane pane0_5; 
	@FXML Pane pane0_6; 
	@FXML Pane pane0_7;
	@FXML Pane pane0_8; 
	@FXML Pane pane0_9; 
	@FXML Pane pane0_10; 
	@FXML Pane pane0_11; 
	@FXML Pane pane0_12; 
	@FXML Pane pane0_13; 
	@FXML Pane pane0_14;
	@FXML Pane pane1_0; 
	@FXML Pane pane1_1; 
	@FXML Pane pane1_2; 
	@FXML Pane pane1_3; 
	@FXML Pane pane1_4; 
	@FXML Pane pane1_5; 
	@FXML Pane pane1_6; 
	@FXML Pane pane1_7;
	@FXML Pane pane1_8; 
	@FXML Pane pane1_9; 
	@FXML Pane pane1_10; 
	@FXML Pane pane1_11; 
	@FXML Pane pane1_12; 
	@FXML Pane pane1_13; 
	@FXML Pane pane1_14;
	@FXML Pane pane2_0; 
	@FXML Pane pane2_1; 
	@FXML Pane pane2_2; 
	@FXML Pane pane2_3; 
	@FXML Pane pane2_4; 
	@FXML Pane pane2_5; 
	@FXML Pane pane2_6; 
	@FXML Pane pane2_7;
	@FXML Pane pane2_8; 
	@FXML Pane pane2_9; 
	@FXML Pane pane2_10; 
	@FXML Pane pane2_11; 
	@FXML Pane pane2_12; 
	@FXML Pane pane2_13; 
	@FXML Pane pane2_14;
	@FXML Pane pane3_0; 
	@FXML Pane pane3_1; 
	@FXML Pane pane3_2; 
	@FXML Pane pane3_3; 
	@FXML Pane pane3_4; 
	@FXML Pane pane3_5; 
	@FXML Pane pane3_6; 
	@FXML Pane pane3_7;
	@FXML Pane pane3_8; 
	@FXML Pane pane3_9; 
	@FXML Pane pane3_10; 
	@FXML Pane pane3_11; 
	@FXML Pane pane3_12; 
	@FXML Pane pane3_13; 
	@FXML Pane pane3_14;
	
	@FXML private Label hint;
	
	@FXML private MenuItem callCons;
	
	@FXML private Button buyVowel;
	
	@FXML private Label multiplier;
	
	@FXML private TextField insertConsonant;
	
	@FXML private Label wallet;
	
	private Label [][] labels;
		
	private Pane [][] panes;
	
	private Sentence sentence;

	

	/**
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		labels = new Label[][]{{label0_0, label0_1, label0_2, label0_3, label0_4, label0_5, label0_6, label0_7,
			label0_8, label0_9, label0_10, label0_11, label0_12, label0_13, label0_14},
			{label1_0, label1_1, label1_2, label1_3, label1_4, label1_5, label1_6, label1_7,
			label1_8, label1_9, label1_10, label1_11, label1_12, label1_13, label1_14},
			{label2_0, label2_1, label2_2, label2_3, label2_4, label2_5, label2_6, label2_7,
			label2_8, label2_9, label2_10, label2_11, label2_12, label2_13, label2_14},
			{label3_0, label3_1, label3_2, label3_3, label3_4, label3_5, label3_6, label3_7,
			label3_8, label3_9, label3_10, label3_11, label3_12, label3_13, label3_14}};
			
		panes = new Pane[][]{{pane0_0, pane0_1, pane0_2, pane0_3, pane0_4, pane0_5, pane0_6, pane0_7,
			pane0_8, pane0_9, pane0_10, pane0_11, pane0_12, pane0_13, pane0_14},
			{pane1_0, pane1_1, pane1_2, pane1_3, pane1_4, pane1_5, pane1_6, pane1_7,
			pane1_8, pane1_9, pane1_10, pane1_11, pane1_12, pane1_13, pane1_14},
			{pane2_0, pane2_1, pane2_2, pane2_3, pane2_4, pane2_5, pane2_6, pane2_7,
			pane2_8, pane2_9, pane2_10, pane2_11, pane2_12, pane2_13, pane2_14},						
			{pane3_0, pane3_1, pane3_2, pane3_3, pane3_4, pane3_5, pane3_6, pane3_7,
			pane3_8, pane3_9, pane3_10, pane3_11, pane3_12, pane3_13, pane3_14}};

		
		sentence = new Sentence("VINSERO BATTAGLIE GRAZIE ALLA LORO FOGA", "Le amazzoni");
	
		insertSentence(sentence);
		
		hint.setText(sentence.getHint());	
		
		//splitMenuButton.setStyle("-fx-color: #424967;");
		
		
	}
	
	/**
	 * 
	 */
	private void insertSentence(Sentence sentence) {
		
		char [][] matrix = sentence.tokenize();
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				labels[i][j].setText(new String("" + matrix[i][j]));
				labels[i][j].setVisible(false);
				if(matrix[i][j] != '\u0000' && matrix[i][j] != ' ')
					panes[i][j].setBackground(new Background(new BackgroundFill(Paint.valueOf("#616285"), null, null)));
			}	
		}
	}
	
	/**
	 * 
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void quit(ActionEvent e) throws IOException, ClassNotFoundException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SelectLobby.fxml"))));
	}
	
	public void spin(ActionEvent e) {
		insertConsonant.setDisable(false);
		Random rand = new Random();
		multiplier.setText("" + (rand.nextInt(950) + 50));
	}
	
	public void checkConsonant(ActionEvent e) {
		if (insertConsonant.getText().length() != 1) {
			System.out.println("il testo non è della lunghezza indicata!");
			//Client.getProxy().giveTurn();
			
		} else {
			
			int counter = 0;
			System.out.println("sono nell'else");
			if (this.sentence.getSentence().toLowerCase().contains(insertConsonant.getCharacters())) {
				System.out.println("la frase contiene il carattere specificato");
				for (int i = 0; i < 4; i++) {
					
					for (int j = 0; j < 15; j++) {
						
						if (insertConsonant.getText().equalsIgnoreCase(labels[i][j].getText())) {
							System.out.println("sono nell'if e nei due for");
							labels[i][j].setVisible(true);
							
							counter++;
						}
					}
				}
			}
			
			wallet.setText((Integer.parseInt(multiplier.getText()) * counter + Integer.parseInt(wallet.getText().substring(0, wallet.getText().length()-2)) + " €"));
			
		}
	}

}


