package src.fr.triedge.sekai.server.database;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import src.fr.triedge.sekai.common.model.Charact;
import src.fr.triedge.sekai.common.model.Account;

public class DB4O implements Database{
	
	protected ObjectContainer db;

	public void open(String path){
		db = Db4oEmbedded.openFile(path);
	}
	
	public void close(){
		db.close();
	}

	@Override
	public boolean userExists(String username, String password) {
		Account user = new Account();
		user.setName(username);
		user.setPassword(password);
		ObjectSet<Account> res = db.queryByExample(user);
		if (res.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public boolean createUser(String username, String password) {
		Account user = new Account();
		user.setName(username);
		user.setPassword(password);
		
		// Get last id
		int lastId = 0;
		ObjectSet<Account> users = db.query(Account.class);
		if (!users.isEmpty()){
			for (Account us : users){
				if (us.getId() > lastId)
					lastId = us.getId();
			}
		}
		user.setId(++lastId);
		db.store(user);
		db.commit();
		ObjectSet<Account> usr = db.queryByExample(user);
		if (usr.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public Account getUser(String username, String password) {
		Account user = new Account();
		user.setName(username);
		user.setPassword(password);
		ObjectSet<Account> res = db.queryByExample(user);
		if (res.isEmpty())
			return null;
		return res.get(0);
	}

	@Override
	public boolean createCharact(Account user, String username) {
		Charact charact = new Charact();
		charact.setName(username);
		//user.getCharacters().add(charact);
		
		db.store(charact);
		db.commit();
		return false;
	}

	@Override
	public int getLastIdUser() {
		int lastId = 0;
		ObjectSet<Account> users = db.query(Account.class);
		if (!users.isEmpty()){
			for (Account us : users){
				if (us.id > lastId)
					lastId = us.id;
			}
		}
		return lastId;
	}

	@Override
	public int getLastIdCharact() {
		int lastId = 0;
		ObjectSet<Charact> chars = db.query(Charact.class);
		if (!chars.isEmpty()){
			for (Charact us : chars){
				if (us.id > lastId)
					lastId = us.id;
			}
		}
		return lastId;
	}
}
