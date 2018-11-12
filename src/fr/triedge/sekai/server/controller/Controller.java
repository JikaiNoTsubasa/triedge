package src.fr.triedge.sekai.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import src.fr.triedge.sekai.common.model.Config;
import src.fr.triedge.sekai.server.database.DatabaseManagement;
import src.fr.triedge.sekai.server.database.JDBC;
import src.fr.triedge.sekai.server.model.DatabaseInfo;

public class Controller {
	
	static final Logger log = Logger.getLogger(Controller.class);
	
	//private DB4O database;
	private DatabaseManagement db;
	private ConnectionListener connectionListener;
	
	public static final String DEFAULT_CONF_PATH				= "server/config/server.properties";
	public static final String DEFAULT_DB_PATH					= "server/data/Sekai.db";
	
	public void init(){
		setupLog4j();
		log.debug("START: init()");
		
		log.info("Starting Server...");
		initConfig();
		log.info("Configuration loaded");
		
		initDatabase();
		log.info("Database loaded");
		
		log.info("Starting Connection Listener...");
		initConnectionListener();
		
		
		log.info("Server Done");
		
		log.debug("END: init()");
	}
	
	private void initConnectionListener() {
		log.debug("START: initConnectionListener()");
		connectionListener = new ConnectionListener(this);
		Thread th = new Thread(connectionListener, "ConnectionListener");
		th.start();
		log.debug("END: initConnectionListener()");
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
	
	private void initDatabase(){
		// Load database
		log.debug("START: initDatabase()");
		DatabaseInfo info = new DatabaseInfo(
				Config.param.getProperty("db.host"),
				Config.param.getProperty("db.user"), 
				Config.param.getProperty("db.password"), 
				Config.param.getProperty("db.name"), 
				Integer.valueOf(Config.param.getProperty("db.port"))
				);
		
		try {
			db = new DatabaseManagement(JDBC.connect(info));
		} catch (SQLException e) {
			log.error("Cannot contact database with Info: "+info, e);
			System.exit(-1);
		}
		
		//setDatabase(new DB4O());
		//getDatabase().open(DEFAULT_DB_PATH);
		log.debug("END: initDatabase()");
	}
	
	public void closeDatabase(){
		log.debug("START: closeDatabase()");
		if (db != null) {
			try {
				db.disconnect();
			} catch (SQLException e) {
				log.error("Cannot close databasee", e);
			}
		}
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

	public DatabaseManagement getDB() {
		return db;
	}

	public void setDB(DatabaseManagement db) {
		this.db = db;
	}

	
}
