package src.fr.triedge.sekai.server.database;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import src.fr.triedge.sekai.common.model.Account;
import src.fr.triedge.sekai.common.model.Item;
import src.fr.triedge.sekai.common.model.Model;

public class DatabaseModelLoader {
	
	static final Logger log = Logger.getLogger(DatabaseModelLoader.class);

	public static Model loadModel(DatabaseManagement db) {
		Model model = new Model();
		log.debug("Empty model created");
		
		// Load accounts + characters
		log.debug("###############################################################");
		log.debug("# Accounts and Characters                                     #");
		log.debug("###############################################################");
		ArrayList<Account> accounts = db.getAllUsers();
		if (accounts == null) {
			log.error("Accounts are null, exiting");
			System.exit(-1);
		}
		model.setAccounts(accounts);
		
		// Load items
		log.debug("###############################################################");
		log.debug("# Items                                                       #");
		log.debug("###############################################################");
		ArrayList<Item> items = db.getAllItems();
		if (items == null) {
			log.error("Items are null, exiting");
			System.exit(-1);
		}
		model.setItems(items);
		
		return model;
	}
}
