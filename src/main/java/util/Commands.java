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
	ENDACTION, 
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
	MODIFYSENTENCE;


}
