package gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import playerRdF.Client;
import playerRdF.GameLogic;
import util.Action;
import util.Commands;
import util.Sentence;

/**
 * The controller for game window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class GameController implements Initializable {

	@FXML private Label label0_0; 
	@FXML private Label label0_1; 
	@FXML private Label label0_2; 
	@FXML private Label label0_3; 
	@FXML private Label label0_4; 
	@FXML private Label label0_5; 
	@FXML private Label label0_6; 
	@FXML private Label label0_7;
	@FXML private Label label0_8; 
	@FXML private Label label0_9; 
	@FXML private Label label0_10; 
	@FXML private Label label0_11; 
	@FXML private Label label0_12; 
	@FXML private Label label0_13; 
	@FXML private Label label0_14;
	@FXML private Label label1_0; 
	@FXML private Label label1_1; 
	@FXML private Label label1_2; 
	@FXML private Label label1_3; 
	@FXML private Label label1_4; 
	@FXML private Label label1_5; 
	@FXML private Label label1_6; 
	@FXML private Label label1_7;
	@FXML private Label label1_8; 
	@FXML private Label label1_9; 
	@FXML private Label label1_10; 
	@FXML private Label label1_11; 
	@FXML private Label label1_12; 
	@FXML private Label label1_13; 
	@FXML private Label label1_14;
	@FXML private Label label2_0; 
	@FXML private Label label2_1; 
	@FXML private Label label2_2; 
	@FXML private Label label2_3; 
	@FXML private Label label2_4; 
	@FXML private Label label2_5; 
	@FXML private Label label2_6; 
	@FXML private Label label2_7;
	@FXML private Label label2_8; 
	@FXML private Label label2_9; 
	@FXML private Label label2_10; 
	@FXML private Label label2_11; 
	@FXML private Label label2_12; 
	@FXML private Label label2_13; 
	@FXML private Label label2_14;
	@FXML private Label label3_0; 
	@FXML private Label label3_1; 
	@FXML private Label label3_2; 
	@FXML private Label label3_3; 
	@FXML private Label label3_4; 
	@FXML private Label label3_5; 
	@FXML private Label label3_6; 
	@FXML private Label label3_7;
	@FXML private Label label3_8; 
	@FXML private Label label3_9; 
	@FXML private Label label3_10; 
	@FXML private Label label3_11; 
	@FXML private Label label3_12; 
	@FXML private Label label3_13; 
	@FXML private Label label3_14;

	@FXML private Pane pane0_0; 
	@FXML private Pane pane0_1; 
	@FXML private Pane pane0_2; 
	@FXML private Pane pane0_3; 
	@FXML private Pane pane0_4; 
	@FXML private Pane pane0_5; 
	@FXML private Pane pane0_6; 
	@FXML private Pane pane0_7;
	@FXML private Pane pane0_8; 
	@FXML private Pane pane0_9; 
	@FXML private Pane pane0_10; 
	@FXML private Pane pane0_11; 
	@FXML private Pane pane0_12; 
	@FXML private Pane pane0_13; 
	@FXML private Pane pane0_14;
	@FXML private Pane pane1_0; 
	@FXML private Pane pane1_1; 
	@FXML private Pane pane1_2; 
	@FXML private Pane pane1_3; 
	@FXML private Pane pane1_4; 
	@FXML private Pane pane1_5; 
	@FXML private Pane pane1_6; 
	@FXML private Pane pane1_7;
	@FXML private Pane pane1_8; 
	@FXML private Pane pane1_9; 
	@FXML private Pane pane1_10; 
	@FXML private Pane pane1_11; 
	@FXML private Pane pane1_12; 
	@FXML private Pane pane1_13; 
	@FXML private Pane pane1_14;
	@FXML private Pane pane2_0; 
	@FXML private Pane pane2_1; 
	@FXML private Pane pane2_2; 
	@FXML private Pane pane2_3; 
	@FXML private Pane pane2_4; 
	@FXML private Pane pane2_5; 
	@FXML private Pane pane2_6; 
	@FXML private Pane pane2_7;
	@FXML private Pane pane2_8; 
	@FXML private Pane pane2_9; 
	@FXML private Pane pane2_10; 
	@FXML private Pane pane2_11; 
	@FXML private Pane pane2_12; 
	@FXML private Pane pane2_13; 
	@FXML private Pane pane2_14;
	@FXML private Pane pane3_0; 
	@FXML private Pane pane3_1; 
	@FXML private Pane pane3_2; 
	@FXML private Pane pane3_3; 
	@FXML private Pane pane3_4; 
	@FXML private Pane pane3_5; 
	@FXML private Pane pane3_6; 
	@FXML private Pane pane3_7;
	@FXML private Pane pane3_8; 
	@FXML private Pane pane3_9; 
	@FXML private Pane pane3_10; 
	@FXML private Pane pane3_11; 
	@FXML private Pane pane3_12; 
	@FXML private Pane pane3_13; 
	@FXML private Pane pane3_14;

	@FXML private Label randVal; //TODO cambierei in spinResult
	@FXML private TextField insertConsonant;
	@FXML private Label hint;

	@FXML private Button buyVowelButton;
	@FXML private Button jollyButton;
	@FXML private Button giveSolutionButton;
	@FXML private Button spinButton;
	@FXML private Label wallet;
	@FXML private Label userName;
	@FXML private ImageView userIcon;
	@FXML private ImageView walletIcon;
	@FXML private ImageView depositIcon;
	@FXML private Label deposit;
	@FXML private Label currentPlayer;

	@FXML private Button a;
	@FXML private Button e;
	@FXML private Button i;
	@FXML private Button o;
	@FXML private Button u;

	@FXML private TextField insertSolution;

	@FXML private Label nManche;
	@FXML private Label timer;


	private Label [][] labels;	
	private Pane [][] panes;
	private Sentence sentence;
	private static boolean inGame = false;


	/**
	 * Creates a table that will contain a sentence.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Client.getProxy().setInGameWindow(true);

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


										resetScreen();

										userName.setText(Client.getUser().getNickname());

										if(Client.isSpectator()) {
											buyVowelButton.setVisible(false);
											spinButton.setVisible(false);
											jollyButton.setVisible(false);
											giveSolutionButton.setVisible(false);
											deposit.setVisible(false);
											wallet.setVisible(false);
											userName.setVisible(false);
											userIcon.setVisible(false);
											depositIcon.setVisible(false);
											walletIcon.setVisible(false);
											
											if(sentence.getLettersShown() != null) {
												for (String letter  : sentence.getLettersShown()) {
													setLabels(letter);
												}
											}
										}

	}

	public void resetScreen() {

		for (int i = 0; i < panes.length; i++) {
			for (int j = 0; j < panes[0].length; j++) {

				panes[i][j].setBackground(new Background(new BackgroundFill(Paint.valueOf("#21232F"), null, null)));

			}
		}

		sentence = Client.getMatch().getSentences().get(Client.getMatch().getManche());

		insertSentence(sentence);

		hint.setText(sentence.getHint());

		hint.setTextFill(Paint.valueOf("#C2D3E4"));

		wallet.setText(0 + "");

		nManche.setText("Manche: " + Client.getMatch().getManche());

	}

	/**
	 * Inserts sentence in game table.
	 * @param sentence The sentence you want to be displayed.
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
	 * Generates a random value, if major 0 then spin result a multiplier for the score, if = 0 spin result is skip, if = -1 spin result is loose, if = -2 spin result is jolly.
	 * @param e Action on "Spin".
	 * @throws IOException .
	 */
	public void spin(ActionEvent e) throws IOException { 

		Client.getProxy().buttonPressedNotification(Commands.SPINBUTTON);

		spinButton.setDisable(true);

		insertConsonant.setDisable(false);

		randVal.setText(GameLogic.spinWheel());

		sendUpdate(randVal.getText(), Commands.UPDATEGAMEWHEEL);

		if (randVal.getText().equals("PERDI")) {
			wallet.setText("0");
			disable();
			createAndSendAction(0, Client.getUser().getId(), "LOSE", Client.getUser().hasJolly(), 0, 
					0, "", Client.getMatch().getId(), Client.getMatch().getManche());


		}
		
		

		if(randVal.getText().equals("PASSA")) { 
			disable();
			createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), 0, 
					0, "", Client.getMatch().getId(), Client.getMatch().getManche());
			useJollyIfPresent();
		} else {
			if (randVal.getText().equals("JOLLY")) {
				spinButton.setDisable(false);

				insertConsonant.setDisable(true);
				
				createAndSendAction(0, Client.getUser().getId(), "RECIVE_JOLLY", Client.getUser().hasJolly(), 0, 
						0, "", Client.getMatch().getId(), Client.getMatch().getManche());
			}
			
			
			//spinButton.setDisable(false); TODO
		}
		
		GameLogic.handleSpinResult(randVal.getText());

	}


	public void disable() {
		buyVowelButton.setDisable(true);
		giveSolutionButton.setDisable(true);
		spinButton.setDisable(true);
		insertConsonant.setDisable(true);
		jollyButton.setDisable(true);
		insertSolution.setVisible(false);
		insertConsonant.setText("");
		insertSolution.setText("");

	}

	private void enable() {
		buyVowelButton.setDisable(false);
		giveSolutionButton.setDisable(false);
		spinButton.setDisable(false);
		insertConsonant.setDisable(false);

	}

	public void checkConsonant(ActionEvent e) throws IOException {


		insertConsonant.setDisable(true);

		sendUpdate(insertConsonant.getText(), Commands.UPDATEGAMETEXT);

		if (insertConsonant.getText().length() != 1) {

			disable();
			Client.getProxy().endTurn();

			createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), 0, 
					0, insertConsonant.getText(), Client.getMatch().getId(), Client.getMatch().getManche());


		} else {

			if (!GameLogic.handleInsertedConsonant(insertConsonant.getText())) {
				disable();

				createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), 0, 
						0, insertConsonant.getText(), Client.getMatch().getId(), Client.getMatch().getManche());

				useJollyIfPresent();
			} else {

				int counter = GameLogic.consonantOccurrences(this.sentence.getSentence(), insertConsonant, labels);

				if(counter > 0) {
					if (isVisibleAsLabel(insertConsonant.getText())) {
						
						disable();
						
						createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), 0, 
								0, insertConsonant.getText(), Client.getMatch().getId(), Client.getMatch().getManche());
						
						useJollyIfPresent();
						
					} else {
						Client.getProxy().buttonPressedNotification(Commands.CONSONANTOK);
						spinButton.setDisable(false);
						sendUpdate(insertConsonant.getText(), Commands.UPDATEGAMETABLE);

						int actionWallet = Integer.parseInt(randVal.getText()) * counter;

						createAndSendAction(0, Client.getUser().getId(), "CONSONANT", Client.getUser().hasJolly(), actionWallet, 
								0, insertConsonant.getText(), Client.getMatch().getId(), Client.getMatch().getManche());

						wallet.setText((Integer.parseInt(randVal.getText()) * counter + Integer.parseInt(wallet.getText())+""));
					}
				} else {
					disable();

					createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), 0, 
							0, insertConsonant.getText(), Client.getMatch().getId(), Client.getMatch().getManche());

					useJollyIfPresent();
				}



			}

		}
	}




	private boolean isVisibleAsLabel(String insertConsonant) {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 15; j++) {
				if (labels[i][j].getText().equalsIgnoreCase(insertConsonant) && labels[i][j].isVisible()) {
					return true;
				}
			}
		}

		return false;
	}


	private void useJollyIfPresent() {
		if(Client.getUser().hasJolly()) {
			jollyButton.setDisable(false);
			Client.getProxy().activateJolly();
		} else {
			try {
				Client.getProxy().endTurn();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

	/**
	 * 
	 * @param e
	 */
	public void useJolly(ActionEvent e) {
		Client.getProxy().buttonPressedNotification(Commands.CONSONANTOK); //L'effetto che si ottiene e' quello di resettare il timer a 5 secondi invece di perdere il turno
		jollyButton.setDisable(true);
		Client.getUser().setJolly(false);

		createAndSendAction(0, Client.getUser().getId(), "USE_JOLLY", Client.getUser().hasJolly(), 0, 
				0, "", Client.getMatch().getId(), Client.getMatch().getManche());

		enable();

	}

	private void sendUpdate(String s, Commands command) throws IOException {
		Client.getProxy().sendGameUpdate(s, command);
	}

	private Label getHint() {
		return hint;
	}

	public void setHint(String s) {
		this.hint.setText(s);
		this.hint.setTextFill(Paint.valueOf("green"));
	}

	private Button getBuyVowel() {
		return buyVowelButton;
	}

	private void setBuyVowel(Button buyVowel) {
		this.buyVowelButton = buyVowel;
	}

	private Label getRandVal() {
		return randVal;
	}


	public void setRandVal(String s) {
		this.randVal.setText(s);
	}

	private TextField getInsertConsonant() {
		return insertConsonant;
	}

	/**
	 * Displays in the prompt text the character in the argument.
	 * @param s the character you want to be displayed in the prompt text.
	 */
	public void setInsertConsonant(String s) {
		//TODO anche qui e' meglio mettere un char e non una string
		this.insertConsonant.setPromptText(s);
	}

	private Label getWallet() {
		return wallet;
	}

	private void setWallet(Label wallet) {
		this.wallet = wallet;
	}

	private Label[][] getLabels() {
		return labels;
	}

	/**
	 * Finds and displays occurrences of the parameter character.
	 * @param string Character you want to display in the sentence.
	 */
	public void setLabels(String string) {
		//TODO l'argomento un verita' dovrebbe essere un carattere e non una stringa
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 15; j++) {
				if (labels[i][j].getText().equalsIgnoreCase(string))
					this.labels[i][j].setVisible(true);
			}
		}
	}


	private Pane[][] getPanes() {
		return panes;
	}

	private void setPanes(Pane[][] panes) {
		this.panes = panes;
	}

	/**
	 * Returns the sentence.
	 * @return Returns the sentence.
	 */
	private Sentence getSentence() {
		return sentence;
	}

	/**
	 * Sets the sentence.
	 * @param sentence It's the sentence you want to register.
	 */
	private void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}

	/**
	 * Returns the status of the match.
	 * @return Returns true if it's ongoing.
	 */
	public static boolean isInGame() {
		return inGame;
	}

	/**
	 * Sets the match status.
	 * @param inGame Status of the match.
	 */
	public static void setInGame(boolean inGame) {
		GameController.inGame = inGame;
	}

	public void play() {

		buyVowelButton.setDisable(false);
		giveSolutionButton.setDisable(false);
		spinButton.setDisable(false);

	}

	public void setCurrentPlayer(String current) {
		currentPlayer.setText(current);

	}

	String pressed = null;

	public void pressA(ActionEvent e) {
		pressed = "A";
		pressedVowel(pressed);
	}

	public void pressE(ActionEvent e) {
		pressed = "E";
		pressedVowel(pressed);
	}

	public void pressI(ActionEvent e) {
		pressed = "I";
		pressedVowel(pressed);
	}

	public void pressO(ActionEvent e) {
		pressed = "O";
		pressedVowel(pressed);
	}

	public void pressU(ActionEvent e) {
		pressed = "U";
		pressedVowel(pressed);
	}

	private void pressedVowel(String pressed) {
		
		if (!sentence.getSentence().contains(pressed) || isVisibleAsLabel(pressed)) {
			
			createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), -1000, 
					0, pressed, Client.getMatch().getId(), Client.getMatch().getManche());
			
			disable();
			
			useJollyIfPresent();
			
		} else {	
			Client.getProxy().buttonPressedNotification(Commands.CONSONANTOK);
			
			createAndSendAction(0, Client.getUser().getId(), "VOWEL", Client.getUser().hasJolly(), -1000, 
					0, pressed, Client.getMatch().getId(), Client.getMatch().getManche());

			enable();
		}
		try {
			sendUpdate(pressed, Commands.UPDATEGAMETABLE);
			sendUpdate(pressed, Commands.UPDATEGAMETEXT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		invisibleVowel();

	}

	private void invisibleVowel() {
		a.setVisible(false);
		e.setVisible(false);
		i.setVisible(false);
		o.setVisible(false);
		u.setVisible(false);
	}

	private void visibleVowel() {
		a.setVisible(true);
		e.setVisible(true);
		i.setVisible(true);
		o.setVisible(true);
		u.setVisible(true);
	}

	public void selectVowel(ActionEvent e) {
		if(Integer.parseInt(wallet.getText()) >= 1000) {
			Client.getProxy().buttonPressedNotification(Commands.SPINBUTTON);
			disable();
			visibleVowel();
			wallet.setText(Integer.parseInt(wallet.getText()) - 1000 + "");
		} else {			
			disable();
			System.out.println("Non hai abbastanza soldi");

			try {
				Client.getProxy().endTurn();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}



	public void giveSolution(ActionEvent e) {
		Client.getProxy().buttonPressedNotification(Commands.SOLUTIONBUTTON);
		insertSolution.setVisible(true);
		giveSolutionButton.setDisable(true);
	}



	public void checkSolution(ActionEvent e) {

		if (insertSolution.getText().trim().equalsIgnoreCase(sentence.getSentence())) {
			
			deposit.setText(Integer.parseInt(deposit.getText()) + Integer.parseInt(wallet.getText()) + "");
			Client.getProxy().sendDeposit(Integer.parseInt(deposit.getText()));
			
			try {
				sendUpdate("Manche vinta da " + Client.getUser().getNickname() + "!" , Commands.MANCHEWON);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			wallet.setText(0 + "");
			disable();
			
			createAndSendAction(0, Client.getUser().getId(), "SOLUTION", Client.getUser().hasJolly(), 0, 
					0, insertSolution.getText(), Client.getMatch().getId(), Client.getMatch().getManche());

		} else {
			disable();
			
			createAndSendAction(0, Client.getUser().getId(), "PASS", Client.getUser().hasJolly(), 0, 
					0, insertSolution.getText(), Client.getMatch().getId(), Client.getMatch().getManche());
			
			useJollyIfPresent();
		}

		insertSolution.setVisible(false);

	}

	public void setSentenceVisible() {

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 15; j++) {
				this.labels[i][j].setVisible(true);
			}
		}
	}

	public void setTimer(String s) {

		timer.setText("Remaining time: " + s + " sec");

	}

	/**
	 * Back to the menu window when the match is finished
	 */
	public void exitMatch() {
		FXMLLoader newLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		try {
			Main.getStage().setScene(new Scene(newLoader.load()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Main.setLoader(newLoader);	
	}


	private void createAndSendAction(Integer turn, Integer playerId, String actionName, boolean jolly, Integer actionWallet, Integer playerNumber,
			String letterCalled, Integer matchId, Integer mancheNumber) {
		Action action = new Action(turn, playerId, actionName, jolly, actionWallet, playerNumber, letterCalled, matchId, mancheNumber);

		Client.getProxy().sendAction(action);

	}

	/**
	 * Exits from the match and return to lobby list screen.
	 * @param e Action on "Quit".
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	public void quit(ActionEvent e) throws IOException, ClassNotFoundException {
		Client.getProxy().quit();
	}
	
	public void exit() {
		try {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


