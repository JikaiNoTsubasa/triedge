package fr.triedge.sekai.common.network;

import java.util.HashMap;


public class SekaiMessageFactory {

	// S2C
	public static SekaiMessage createMesLoginSuccessful(HashMap<String, String> params){
		return new SekaiMessage(SekaiProtocol.SERVER_RESPOND_LOGIN_OK);
	}
	
	// S2C
	public static SekaiMessage createMesLoginFailed(HashMap<String, String> params){
		return new SekaiMessage(SekaiProtocol.SERVER_RESPOND_LOGIN_NOK);
	}
	
	// S2C
		public static SekaiMessage createMesCreateUserSuccessful(HashMap<String, String> params){
			return new SekaiMessage(SekaiProtocol.SERVER_RESPOND_CREATE_USER_OK);
		}
		
		// S2C
		public static SekaiMessage createMesCreateUserFailed(HashMap<String, String> params){
			return new SekaiMessage(SekaiProtocol.SERVER_RESPOND_CREATE_USER_NOK);
		}
	
	// S2C
	/*
	public static SekaiMessage createMesUserInfoLogin(ArrayList<ServerCharacter> chs){
		SekaiMessage mes = new SekaiMessage(SekaiProtocol.SERVER_SEND_USER_INFO_AFTER_LOGIN);
		ArrayList<String> names = new ArrayList<>();
		for (ServerCharacter c : chs){
			names.add(c.getName());
		}
		mes.getParams().put("characters", names);
		return mes;
	}*/
	
	// C2S
	public static SekaiMessage createMesAskLoginToServer(String user, String password){
		return new SekaiMessage(SekaiProtocol.CLIENT_ASK_LOGIN).append("login", user).append("password", password);
	}

	// C2S
	public static SekaiMessage createMesAskCreateUserToServer(String user, String password){
		return new SekaiMessage(SekaiProtocol.CLIENT_ASK_CREATE_USER).append("login", user).append("password", password);
	}

	// C2S
	public static SekaiMessage createMesReplyCharacterSelectionToServer(String name){
		return new SekaiMessage(SekaiProtocol.CLIENT_RESPOND_CHARACTER_SELECTION).append("character", name);
	}
}
