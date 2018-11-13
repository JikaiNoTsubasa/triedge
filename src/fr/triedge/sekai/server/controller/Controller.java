package src.fr.triedge.sekai.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import src.fr.triedge.sekai.common.model.Charact;
import src.fr.triedge.sekai.common.model.Config;
import src.fr.triedge.sekai.common.model.Model;
import src.fr.triedge.sekai.common.network.SekaiSession;
import src.fr.triedge.sekai.server.database.DatabaseManagement;
import src.fr.triedge.sekai.server.database.DatabaseModelLoader;
import src.fr.triedge.sekai.server.database.JDBC;
import src.fr.triedge.sekai.server.model.DatabaseInfo;
import src.fr.triedge.sekai.server.utils.Utils;

public class Controller {
	
	static final Logger log = Logger.getLogger(Controller.class);
	
	//FIXME - Scheduler that checks online chars and update DB, also remove online from db when disconnected
	
	//private DB4O database;
	private DatabaseManagement db;
	private ConnectionListener connectionListener;
	private Model model;
	private Engine engine;
	private List<ServerDelegate> delegates = new ArrayList<>();
	
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
		
		initModel();
		log.info("Model loaded");
		
		log.info("Starting Connection Listener...");
		initConnectionListener();
		
		log.info("Starting Engine...");
		initEngine();
		
		log.info("Server Done");
		
		log.debug("END: init()");
	}
	
	private void initEngine() {
		log.debug("START: initEngine()");
		setEngine(new Engine(this));
		Thread th = new Thread(getEngine());
		th.setName("Engine");
		th.start();
		log.debug("END: initEngine()");
	}

	private void initModel() {
		log.debug("START: initModel()");
		setModel(DatabaseModelLoader.loadModel(db));
		log.debug("END: initModel()");
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

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Instantiates the character into the game and creates the server delegate
	 * for the user's session
	 * @param id
	 * @param session
	 */
	public void loginCharacter(int id, SekaiSession session) {
		log.debug("START: loginCharacter()");
		Charact ch = Utils.getCharacterByIDFromAccount(getModel().getAccounts(), id);
		getModel().getOnlineCharacters().add(ch);
		log.debug("Character "+ch+" added to Online list");
		
		ServerDelegate del = new ServerDelegate(ch, this, session);
		getDelegates().add(del);
		log.debug("Created ServerDelegate for character "+ch);
		
		Thread th = new Thread(del);
		th.setName(ch.getName());
		th.start();
		log.debug("END: loginCharacter()");
	}

	public List<ServerDelegate> getDelegates() {
		return delegates;
	}

	public void setDelegates(List<ServerDelegate> delegates) {
		this.delegates = delegates;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	
}
