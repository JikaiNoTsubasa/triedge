package fr.triedge.sekai.server.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Controller {
	
	static final Logger log = Logger.getLogger(Controller.class);
	
	private Database database;
	
	public static final String DEFAULT_DB_PATH				= "server/data/Sekai.db";
	
	public void init(){
		setupLog4j();
		log.debug("START: init()");
		
		log.info("Starting Server...");
		initConfig();
		log.info("Configuration loaded");
		
		initDatabase();
		log.info("Database loaded");
		
		log.info("Server Done");
		
		log.debug("END: init()");
	}
	
	private void initConfig(){
		// Load config file
		log.debug("START: initConfig()");
		
		log.debug("END: initConfig()");
	}
	
	private void initDatabase(){
		// Load database
		log.debug("START: initDatabase()");
		setDatabase(new Database());
		getDatabase().open(DEFAULT_DB_PATH);
		log.debug("END: initDatabase()");
	}
	
	public void closeDatabase(){
		log.debug("START: closeDatabase()");
		getDatabase().close();
		log.debug("END: closeDatabase()");
	}

	public void setupLog4j(){
		String log4JPropertyFile = "server/config/log4j.properties";
		Properties p = new Properties();

		try {
		    p.load(new FileInputStream(log4JPropertyFile));
		    PropertyConfigurator.configure(p);
		} catch (IOException e) {
			
		}
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}
}
