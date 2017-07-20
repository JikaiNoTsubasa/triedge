package fr.triedge.sekai.common.network;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class SekaiMessage implements Serializable{

	public static final int MSG_SEND_PORT									= 1;

	public int code;
	private HashMap<String, Object> params = new HashMap<>();
	
	public SekaiMessage(int code) {
		super();
		this.code = code;
	}
	public HashMap<String, Object> getParams() {
		return params;
	}
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	
	public SekaiMessage append(String key, Object value){
		getParams().put(key, value);
		return this;
	}
}
