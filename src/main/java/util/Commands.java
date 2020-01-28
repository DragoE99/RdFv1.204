package util;

import java.io.Serializable;

/**
 * 
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
	ALREADYON,
	INSERTSENTENCES,
	GETALLSENTENCES;
	//tutti gli altri comandi

}
