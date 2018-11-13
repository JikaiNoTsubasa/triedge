package src.fr.triedge.sekai.server.database;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;

import src.fr.triedge.sekai.common.model.Account;
import src.fr.triedge.sekai.common.model.Item;
import src.fr.triedge.sekai.common.model.Model;
import src.fr.triedge.sekai.common.model.Npc;

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
		model.setAccounts(Collections.synchronizedList(accounts));
		
		// Load items
		log.debug("###############################################################");
		log.debug("# Items                                                       #");
		log.debug("###############################################################");
		ArrayList<Item> items = db.getAllItems();
		if (items == null) {
			log.error("Items are null, exiting");
			System.exit(-1);
		}
		model.setItems(Collections.synchronizedList(items));
		
		// Load npc
		log.debug("###############################################################");
		log.debug("# npc                                                         #");
		log.debug("###############################################################");
		ArrayList<Npc> npcs = db.getAllNPC();
		if (npcs == null) {
			log.error("Npcs are null, exiting");
			System.exit(-1);
		}
		model.setNpcs(Collections.synchronizedList(npcs));
		
		return model;
	}
}
