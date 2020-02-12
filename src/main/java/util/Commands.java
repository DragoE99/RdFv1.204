package util;

import java.io.Serializable;

/**
 * An enumeration with all commands needed to handle ocommunication between server and client
 * @author gruppo aelv
 *
 */
public enum Commands implements Serializable {
	
	
	OK,
	NO,
	LOGIN,
	SIGNUP,
	RESET, 
	NEEDLOBBYLIST,
	CREATELOBBY, 
	MODIFYUSER,
	UPDATEGAMETABLE, 
	UPDATEGAMETEXT, 
	UPDATEGAMEWHEEL, 
	CHOSENMATCH, 
	EXISTMAIL, 
	EXISTNICK, 
	NOTVERIFIED, 
	ACTIVATIONCODE,
	QUIT, 
	ENDTURN, 
	PLAY, 
	START, 
	SPECTATE,
	ALREADYON, 
	USTATS, 
	GSTATS, 
	CURRENTPLAYERLABEL,
	INSERTSENTENCES,
	GETALLSENTENCES, 
	MANCHEWON,
	MODIFYSENTENCE, 
	DELETESENTENCE,
	REMOVEME, 
	TIMER, 
	TIMEOUT, 
	SPINBUTTON,
	SOLUTIONBUTTON,
	CONSONANTOK,
	ACTIVATEJOLLY,
	NEWMANCHE,
	ENDMATCH,
	DEPOSIT,
	EXITMATCH,
	QUITWR,
	ACTION,
	NOTANADMIN;

	
	@Override
	public String toString() {
		switch (this) {
		case TIMEOUT: return "TIMEOUT";
		
		case EXITMATCH: return "EXITMATCH";
		
		case NEWMANCHE: return "NEWMANCHE";

		default: return "altro comando";

		}
	}

}
