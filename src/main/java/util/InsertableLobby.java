package util;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * @author achil
 * Classe che in sostanza trasforma i campi di Lobby in property, cosi' da poter
 * essere inseriti nella TableView.
 * Azione necessaria per via del fatto che i campi property non implementano 
 * serializable e quindi se avessimo lasciato Lobby con i campi SimpleXProperty
 * non sarebbe stato possibile inviare la HashMap di Lobby e sarebbe stata 
 * lanciata l'eccezione NotSerializableException
 */

public class InsertableLobby extends Lobby{
		
		private final SimpleStringProperty lobbyName;
		private final SimpleIntegerProperty nPlayers;
		private final SimpleIntegerProperty nSpectators;
		private final SimpleBooleanProperty status;
		private Match match;
		private Lobby lobby;
		
		public InsertableLobby(Lobby lobby){
			//super();
			this.lobby = lobby;
			this.lobbyName = new SimpleStringProperty(lobby.getLobbyName());
			this.nPlayers = new SimpleIntegerProperty(lobby.getNPlayers());
			this.nSpectators = new SimpleIntegerProperty(lobby.getNSpectators());
			this.status = new SimpleBooleanProperty(lobby.isActive());
			this.match = lobby.getMatch();
		}

		
		public String getLobbyName() {
			return lobbyName.get();
		}

		public Integer getNPlayers() {
			return nPlayers.get();
		}

		public Integer getNSpectators() {
			return nSpectators.get();
		}

		public Boolean getStatus() {
			return status.get();
		}
		
		public Match getMatch() {
			return match;
		}


		public Lobby getLobby() {

			return lobby;
		}
	}