
package src.fr.triedge.sekai.client.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import de.gurkenlabs.litiengine.Game;
import src.fr.triedge.sekai.client.ui.LoginDialog;
import src.fr.triedge.sekai.common.model.Config;
import src.fr.triedge.sekai.common.network.SekaiMessage;
import src.fr.triedge.sekai.common.network.SekaiMessageFactory;
import src.fr.triedge.sekai.common.network.SekaiProtocol;
import src.fr.triedge.sekai.common.network.SekaiSession;
import src.fr.triedge.sekai.common.network.SekaiSocket;
import src.fr.triedge.sekai.common.network.SekaiSocketManager;

public class Controller {
	
	public static final String DEFAULT_CONF_PATH				= "client/config/client.properties";
	static final Logger log = Logger.getLogger(Controller.class);
	private LoginDialog loginDialog;

	public void init(){
		setupLog4j();
		log.debug("START: init()");
		
		// Load config
		initConfig();
		
		// StartLogin
		initLogin();
		
		// Start Console
		//startConsole();
		
		
		log.debug("END: init()");
	}
	
	private void initLogin() {
		setLoginDialog(new LoginDialog(null, this));
		getLoginDialog().setVisible(true);
	}

	private void initConfig(){
		// Load config file
		log.debug("START: initConfig()");
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(DEFAULT_CONF_PATH)));
			Config.param = prop;
		} catch (IOException e) {
			log.error("Cannot load properties file "+DEFAULT_CONF_PATH,e);
		}
		
		log.debug("END: initConfig()");
	}
	
	public void requestLoginToServer(String username, String password) {
		String host = Config.param.getProperty("server.host");
		int senderPort = Integer.valueOf(Config.param.getProperty("server.sender.port"));
		int receiverPort = Integer.valueOf(Config.param.getProperty("server.receiver.port"));
		
		SekaiSocket socket;
		try {
			socket = SekaiSocketManager.getSekaiSocket(host, senderPort, receiverPort);
			SekaiSession session = SekaiSocketManager.getSession(socket);
			SekaiMessage mes_ask_login = SekaiMessageFactory.createMesAskLoginToServer(username, password);
			log.debug("Login to server with user: "+username+" to server: "+host+"[S:"+senderPort+"][R:"+receiverPort+"]");
			try {
				log.debug("Sending message...");
				SekaiMessage ans_login = session.sendMessageWithResponse(mes_ask_login);
				log.debug("Message sent");
				if (ans_login.code == SekaiProtocol.SERVER_RESPOND_LOGIN_OK){
					log.debug("LOGIN OK");
					log.debug("Character list:");
					//Get the user's characters
					for (Entry<String, Object> param : ans_login.getParams().entrySet()){
						log.debug(param.getKey());
					}
					log.debug("Choose a character");
					
					
				}else{
					log.debug("LOGIN FAILED");
				}
			} catch (ClassNotFoundException | IOException e) {
				log.error("Failed to send message", e);
			}
		} catch (IOException e) {
			log.error("Could not contact server", e);
		}
	}
	
	public void startConsole(){
		SekaiSocket socket;
		try {
			socket = SekaiSocketManager.getSekaiSocket("localhost", 2017, 2008);
			SekaiSession session = SekaiSocketManager.getSession(socket);
			CommandLine cl = new CommandLine(session);
			
			Thread th = new Thread(cl);
			th.start();
		} catch (IOException e) {
			log.error("Cannot login",e);
		}
		
	}
	
	public void setupLog4j(){
		String log4JPropertyFile = "client/config/log4j.properties";
		Properties p = new Properties();

		try {
		    p.load(new FileInputStream(log4JPropertyFile));
		    PropertyConfigurator.configure(p);
		} catch (IOException e) {
			
		}
	}

	public LoginDialog getLoginDialog() {
		return loginDialog;
	}

	public void setLoginDialog(LoginDialog loginDialog) {
		this.loginDialog = loginDialog;
	}
}
