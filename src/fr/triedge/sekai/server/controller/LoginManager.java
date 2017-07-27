package fr.triedge.sekai.server.controller;

import java.io.IOException;

import org.apache.log4j.Logger;

import fr.triedge.sekai.common.model.Charact;
import fr.triedge.sekai.common.model.User;
import fr.triedge.sekai.common.network.SekaiMessage;
import fr.triedge.sekai.common.network.SekaiMessageFactory;
import fr.triedge.sekai.common.network.SekaiProtocol;
import fr.triedge.sekai.common.network.SekaiSession;
import fr.triedge.sekai.common.network.SekaiSocketManager;

public class LoginManager implements Runnable{

	static final Logger log = Logger.getLogger(LoginManager.class);
	private Controller server;
	private SekaiSession session;
	private boolean running = true;
	
	public LoginManager(Controller server, SekaiSession session) {
		super();
		this.server = server;
		this.session = session;
	}

	@Override
	public void run() {
		log.debug("START: run()");
		while (running) {
			// Waiting for client username/password
			try {
				log.info("Waiting for user username/password...");
				SekaiMessage mes_user = session.receiveMessage();
				if (mes_user.code==SekaiProtocol.CLIENT_ASK_LOGIN){
					String user = mes_user.getParams().get("login").toString();
					String pwd = mes_user.getParams().get("password").toString();
					log.debug("Trying to login with user: "+user);
					boolean userExists = getServer().getDatabase().userExists(user, pwd);
					SekaiMessage ans_user;
					if (userExists){
						ans_user = SekaiMessageFactory.createMesLoginSuccessful(null);
						log.debug("Login successful: "+user);
						log.debug("Sending user's characters");
						User user_obj = getServer().getDatabase().getUser(user, pwd);
						ans_user.getParams().put(user_obj.getCharact().getName(), user_obj.getCharact().getName());
						session.respondToMessage(ans_user);
						SekaiMessage mes_char = session.receiveMessage();
						if (mes_char.code == SekaiProtocol.CLIENT_ASK_SELECT_CHAR){
							
						}else if (mes_char.code == SekaiProtocol.CLIENT_ASK_CREATE_CHAR){
							String username = mes_char.getParams().get("user").toString();
							getServer().getDatabase().createCharact(user_obj, username);
						}
						//TODO - send response with char info
					}else{
						ans_user = SekaiMessageFactory.createMesLoginFailed(null);
						session.respondToMessage(ans_user);
						log.debug("Login failed: "+user);
					}
					
					running = false;
				}else if (mes_user.code==SekaiProtocol.CLIENT_ASK_CREATE_USER){
					String user = mes_user.getParams().get("login").toString();
					String pwd = mes_user.getParams().get("password").toString();
					log.debug("Trying create user: "+user);
					boolean userExists = getServer().getDatabase().createUser(user,pwd);
					SekaiMessage ans_user;
					if (userExists){
						ans_user = SekaiMessageFactory.createMesCreateUserSuccessful(null);
						log.debug("User created: "+user);
					}else{
						ans_user = SekaiMessageFactory.createMesCreateUserFailed(null);
						log.debug("User creation failed: "+user);
					}
					session.respondToMessage(ans_user);
				}else{
					log.warn("Unknow massage code: "+mes_user);
				}
			} catch (ClassNotFoundException | IOException e) {
				log.error("Cannot receive user/password message",e);
			}
			
		}
		log.debug("END: run()");
	}

	public Controller getServer() {
		return server;
	}

	public void setServer(Controller server) {
		this.server = server;
	}

	public SekaiSession getSession() {
		return session;
	}

	public void setSession(SekaiSession session) {
		this.session = session;
	}

	
}
