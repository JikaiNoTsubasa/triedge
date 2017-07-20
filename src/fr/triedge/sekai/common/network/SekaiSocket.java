package fr.triedge.sekai.common.network;

import java.net.Socket;

public class SekaiSocket {
	
	private Socket outSocket;
	private Socket inSocket;

	public Socket getOutSocket() {
		return outSocket;
	}

	public void setOutSocket(Socket outSocket) {
		this.outSocket = outSocket;
	}

	public Socket getInSocket() {
		return inSocket;
	}

	public void setInSocket(Socket inSocket) {
		this.inSocket = inSocket;
	}
}
