package src.fr.triedge.sekai.server.controller;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import src.fr.triedge.sekai.common.model.Charact;
import src.fr.triedge.sekai.common.model.User;

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
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		ObjectSet<User> res = db.queryByExample(user);
		if (res.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public boolean createUser(String username, String password) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		
		// Get last id
		int lastId = 0;
		ObjectSet<User> users = db.query(User.class);
		if (!users.isEmpty()){
			for (User us : users){
				if (us.getId() > lastId)
					lastId = us.getId();
			}
		}
		user.setId(++lastId);
		db.store(user);
		db.commit();
		ObjectSet<User> usr = db.queryByExample(user);
		if (usr.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public User getUser(String username, String password) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		ObjectSet<User> res = db.queryByExample(user);
		if (res.isEmpty())
			return null;
		return res.get(0);
	}

	@Override
	public boolean createCharact(User user, String username) {
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
		ObjectSet<User> users = db.query(User.class);
		if (!users.isEmpty()){
			for (User us : users){
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
