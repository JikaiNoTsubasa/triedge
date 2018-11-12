package src.fr.triedge.sekai.server.controller;

import java.io.IOException;

import org.apache.log4j.Logger;

import src.fr.triedge.sekai.common.model.Config;
import src.fr.triedge.sekai.common.network.SekaiSession;
import src.fr.triedge.sekai.common.network.SekaiSocketManager;

public class ConnectionListener implements Runnable{

	static final Logger log = Logger.getLogger(ConnectionListener.class);
	private Controller server;
	private boolean running = true;
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public ConnectionListener(Controller server) {
		super();
		this.server = server;
	}
	
	public Controller getServer() {
		return server;
	}
	public void setServer(Controller server) {
		this.server = server;
	}

	@Override
	public void run() {
		log.debug("START: run()");
		int port = Integer.parseInt(Config.param.getProperty("server.client.port", "2017"));
		while (isRunning()) {
			try {
				log.info("Waiting incoming connection on "+port);
				SekaiSession session = SekaiSocketManager.getSession(SekaiSocketManager.receiveSekaiSocket(port));
				log.info("Connection from: "+session.getSekaiSocket().toString());
				if (session != null){
					// Start login
					LoginManager login = new LoginManager(getServer(), session);
					log.info("LoginManager starting...");
					Thread th = new Thread(login);
					th.start();
				}
			} catch (ClassNotFoundException | IOException e) {
				log.error("Could not connect to client",e);
			}
			
		}
		log.debug("END: run()");
	}
}
