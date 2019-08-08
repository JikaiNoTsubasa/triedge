package fr.triedge.sekai.server.utils;

import java.util.Iterator;
import java.util.List;

import fr.triedge.sekai.common.model.Account;
import fr.triedge.sekai.common.model.Charact;

public class Utils {

	public static Charact getCharacterByID(List<Charact> characters, int id) {
		synchronized (characters) {
			Iterator<Charact> i = characters.iterator(); // Must be in synchronized block
		      while (i.hasNext()) {
		    	  Charact c = i.next();
		    	  if (c.id == id)
		    		  return c;
		      }
		}
		return null;
	}
	
	public static Charact getCharacterByIDFromAccount(List<Account> accounts, int id) {
		synchronized (accounts) {
			Iterator<Account> i = accounts.iterator(); // Must be in synchronized block
		      while (i.hasNext()) {
		    	  Account c = i.next();
		    	  Iterator<Charact> it = c.getCharacters().iterator();
		    	  while (it.hasNext()) {
		    		  Charact ch = it.next();
		    		  if (ch.id == id)
		    			  return ch;
		    		  
		    	  }
		      }
		}
		return null;
	}
	
	public static Account getAccountByID(List<Account> accounts, int id) {
		synchronized (accounts) {
			Iterator<Account> i = accounts.iterator(); // Must be in synchronized block
		      while (i.hasNext()) {
		    	  Account c = i.next();
		    	  if (c.id == id)
		    		  return c;
		      }
		}
		return null;
	}
}
