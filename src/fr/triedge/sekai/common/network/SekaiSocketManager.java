package fr.triedge.sekai.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SekaiSocketManager {

	public static SekaiSocket getSekaiSocket(String host, int port, int localPort) throws UnknownHostException, IOException{
		SekaiSocket saik = new SekaiSocket();
		Socket outSok = new Socket(host,port);
		saik.setOutSocket(outSok);
		ObjectOutputStream oos = new ObjectOutputStream(outSok.getOutputStream());
		oos.flush();
		//ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());
		SekaiMessage mes = new SekaiMessage(SekaiMessage.MSG_SEND_PORT).append("port", localPort);
		oos.writeObject(mes);
		oos.flush();
		ServerSocket server = new ServerSocket(localPort);
		Socket inSok = server.accept();
		server.close();
		saik.setInSocket(inSok);
		return saik;
	}
	
	public static SekaiSocket receiveSekaiSocket(int listeningPort) throws IOException, ClassNotFoundException{
		ServerSocket server = new ServerSocket(listeningPort);
		Socket inSok = server.accept();
		server.close();
		ObjectInputStream ois = new ObjectInputStream(inSok.getInputStream());
		SekaiMessage mes = (SekaiMessage) ois.readObject();
		int remotePort = (int) mes.getParams().get("port");
		Socket outSok = new Socket(inSok.getInetAddress().getHostName(), remotePort);
		ObjectOutputStream oos = new ObjectOutputStream(outSok.getOutputStream());
		oos.flush();
		SekaiSocket saik = new SekaiSocket();
		saik.setInSocket(inSok);
		saik.setOutSocket(outSok);
		return saik;
	}
	
	public static SekaiSession getSession(SekaiSocket sekaiSocket){
		return new SekaiSession(sekaiSocket);
	}
}
