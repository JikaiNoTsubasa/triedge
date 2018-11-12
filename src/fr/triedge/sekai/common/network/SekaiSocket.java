package src.fr.triedge.sekai.common.network;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IN: ");
		builder.append(inSocket.getInetAddress().getHostName());
		builder.append(":");
		builder.append(inSocket.getPort());
		builder.append(" - ");
		builder.append("OUT: ");
		builder.append(outSocket.getInetAddress().getHostName());
		builder.append(":");
		builder.append(outSocket.getPort());
		return builder.toString();
	}
	
	
}
