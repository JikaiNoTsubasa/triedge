package fr.triedge.sekai.server.database;

import fr.triedge.sekai.common.model.Account;

public interface Database {

	public boolean userExists(String username, String password);
	public boolean createUser(String username, String password);
	public boolean createCharact(Account user, String username);
	public int getLastIdUser();
	public int getLastIdCharact();
	public Account getUser(String username, String password);
	public void open(String path);
	public void close();
}
