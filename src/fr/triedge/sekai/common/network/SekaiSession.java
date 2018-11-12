package src.fr.triedge.sekai.common.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SekaiSession {

	private SekaiSocket sekaiSocket;
	private ObjectOutputStream oosOut, oosIn;
	private ObjectInputStream oisOut, oisIn;
	
	public SekaiSession(SekaiSocket sekaiSocket) {
		super();
		this.sekaiSocket = sekaiSocket;
	}
	
	public void sendMessage(SekaiMessage mes) throws IOException{
		if (getOosOut() == null)
			setOosOut(new ObjectOutputStream(getSekaiSocket().getOutSocket().getOutputStream()));
		getOosOut().writeObject(mes);
		getOosOut().flush();
	}
	
	public SekaiMessage sendMessageWithResponse(SekaiMessage mes) throws IOException, ClassNotFoundException{
		sendMessage(mes);
		if (getOisOut() == null)
			setOisOut(new ObjectInputStream(getSekaiSocket().getOutSocket().getInputStream()));
		SekaiMessage rep = (SekaiMessage) getOisOut().readObject();
		return rep;
	}
	
	public SekaiMessage receiveMessage() throws IOException, ClassNotFoundException{
		if (getOisIn() == null)
			setOisIn(new ObjectInputStream(getSekaiSocket().getInSocket().getInputStream()));
		SekaiMessage rep = (SekaiMessage) getOisIn().readObject();
		return rep;
	}
	
	public void respondToMessage(SekaiMessage mes) throws IOException{
		if (getOosIn() == null)
			setOosIn(new ObjectOutputStream(getSekaiSocket().getInSocket().getOutputStream()));
		getOosIn().writeObject(mes);
		getOosIn().flush();
	}

	public SekaiSocket getSekaiSocket() {
		return sekaiSocket;
	}

	public void setSekaiSocket(SekaiSocket sekaiSocket) {
		this.sekaiSocket = sekaiSocket;
	}

	public ObjectOutputStream getOosOut() {
		return oosOut;
	}

	public void setOosOut(ObjectOutputStream oosOut) {
		this.oosOut = oosOut;
	}

	public ObjectInputStream getOisOut() {
		return oisOut;
	}

	public void setOisOut(ObjectInputStream oisOut) {
		this.oisOut = oisOut;
	}

	public ObjectOutputStream getOosIn() {
		return oosIn;
	}

	public void setOosIn(ObjectOutputStream oosIn) {
		this.oosIn = oosIn;
	}

	public ObjectInputStream getOisIn() {
		return oisIn;
	}

	public void setOisIn(ObjectInputStream oisIn) {
		this.oisIn = oisIn;
	}
}
