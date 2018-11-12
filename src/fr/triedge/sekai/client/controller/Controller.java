
package src.fr.triedge.sekai.client.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import src.fr.triedge.sekai.common.network.SekaiSession;
import src.fr.triedge.sekai.common.network.SekaiSocket;
import src.fr.triedge.sekai.common.network.SekaiSocketManager;

public class Controller {
	
	private long window;
	static final Logger log = Logger.getLogger(Controller.class);

	public void init(){
		setupLog4j();
		log.debug("START: init()");
		// Load config
		
		// Start Console
		startConsole();
		
		/*
		if (!glfwInit()){
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		setWindow(glfwCreateWindow(640, 480, "Sekai", 0, 0));
		if (getWindow() == 0){
			throw new IllegalStateException("Failed to create video");
		}
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(getWindow(), (videoMode.width() - 640)/2, (videoMode.height() - 480)/2);
		glfwShowWindow(getWindow());
		*/
		log.debug("END: init()");
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
	
	public long getWindow() {
		return window;
	}

	public void setWindow(long window) {
		this.window = window;
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
}
