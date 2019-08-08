package fr.triedge.sekai.server.controller;

import java.io.IOException;

import org.apache.log4j.Logger;

import fr.triedge.sekai.common.model.Charact;
import fr.triedge.sekai.common.network.SekaiMessage;
import fr.triedge.sekai.common.network.SekaiProtocol;
import fr.triedge.sekai.common.network.SekaiSession;
import fr.triedge.sekai.server.model.ClientInputs;

public class ServerDelegate implements Runnable{
	
	static final Logger log = Logger.getLogger(ServerDelegate.class);
	
	private Charact character;
	private Controller controller;
	private SekaiSession session;
	private ServerDelegateReceiver receiver;
	private boolean running = true;
	
	public ClientInputs inputs = null;
	
	public ServerDelegate(Charact character, Controller controller, SekaiSession session) {
		super();
		this.character = character;
		this.controller = controller;
		this.session = session;
		this.receiver = new ServerDelegateReceiver();
		Thread th = new Thread(receiver);
		th.start();
	}

	@Override
	public void run() {
		log.debug("START: run("+character+")");
		while(isRunning()) {
			try {
				//TODO - send server data - map - characters etc
				//session.sendMessage(mes);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("STOPPED", e);
			}
		}
		log.debug("END: run("+character+")");
	}
	
	public void processIncomingMessage(SekaiMessage mes) {
		//TODO - process incoming inputs
		switch (mes.code) {
		case SekaiProtocol.CLIENT_ASK_MOVE:
			
			break;

		default:
			break;
		}
	}
	
	private class ServerDelegateReceiver implements Runnable{

		@Override
		public void run() {
			while (isRunning()) {
				try {
					SekaiMessage incomingMes = session.receiveMessage();
					processIncomingMessage(incomingMes);
				} catch (IOException | ClassNotFoundException e) {
					log.error("Failed to receive message from client for character "+character, e);
				}
			}
		}
		
	}

	public Charact getCharacter() {
		return character;
	}

	public void setCharacter(Charact character) {
		this.character = character;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public SekaiSession getSession() {
		return session;
	}

	public void setSession(SekaiSession session) {
		this.session = session;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public ServerDelegateReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(ServerDelegateReceiver receiver) {
		this.receiver = receiver;
	}

}
