package fr.triedge.sekai.common.network;

public class SekaiProtocol {

	// From Server To Client
	public static final int SERVER_RESPOND_LOGIN_OK										= 800;
	public static final int SERVER_RESPOND_LOGIN_NOK									= 801;
	public static final int SERVER_SEND_USER_INFO_AFTER_LOGIN							= 802;
	public static final int SERVER_RESPOND_CREATE_USER_OK								= 803;
	public static final int SERVER_RESPOND_CREATE_USER_NOK								= 804;
	
	// From Client To Server
	public static final int CLIENT_ASK_LOGIN											= 100;
	public static final int CLIENT_RESPOND_CHARACTER_SELECTION							= 101;
	public static final int CLIENT_ASK_CREATE_USER										= 102;
	public static final int CLIENT_ASK_SELECT_CHAR										= 103;
	public static final int CLIENT_ASK_CREATE_CHAR										= 104;
	
	public static final int CLIENT_ASK_MOVE												= 200;
}
