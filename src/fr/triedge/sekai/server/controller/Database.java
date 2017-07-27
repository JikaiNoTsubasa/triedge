package fr.triedge.sekai.server.controller;

import fr.triedge.sekai.common.model.User;

public interface Database {

	public boolean userExists(String username, String password);
	public boolean createUser(String username, String password);
	public boolean createCharact(User user, String username);
	public int getLastIdUser();
	public int getLastIdCharact();
	public User getUser(String username, String password);
	public void open(String path);
	public void close();
}
