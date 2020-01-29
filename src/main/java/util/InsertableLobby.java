package util;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 * Class that transforms Lobby into in an observable value, so that can be seen and inserted in a TableView
 */

public class InsertableLobby extends Lobby{
		
		private final SimpleStringProperty lobbyName;
		private final SimpleIntegerProperty nPlayers;
		private final SimpleIntegerProperty nSpectators;
		private final SimpleBooleanProperty status;
		private Match match;
		private Lobby lobby;
		
		/**
		 * Create a InsertableLobby with the values contained in the lobby given as argument
		 * @param lobby 
		 */
		public InsertableLobby(Lobby lobby){
			//super();
			this.lobby = lobby;
			this.lobbyName = new SimpleStringProperty(lobby.getLobbyName());
			this.nPlayers = new SimpleIntegerProperty(lobby.getNPlayers());
			this.nSpectators = new SimpleIntegerProperty(lobby.getNSpectators());
			this.status = new SimpleBooleanProperty(lobby.isActive());
			this.match = lobby.getMatch();
		}

		/**
		 * Getter
		 */
		public String getLobbyName() {
			return lobbyName.get();
		}

		/**
		 * Getter
		 */
		public Integer getNPlayers() {
			return nPlayers.get();
		}

		/**
		 * Getter
		 */
		public Integer getNSpectators() {
			return nSpectators.get();
		}

		/**
		 * Getter
		 * @return
		 */
		public Boolean getStatus() {
			return status.get();
		}
		
		/**
		 * Getter
		 */
		public Match getMatch() {
			return match;
		}


		/**
		 * Getter
		 * @return
		 */
		public Lobby getLobby() {

			return lobby;
		}
	}