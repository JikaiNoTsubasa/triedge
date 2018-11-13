package src.fr.triedge.sekai.common.model;

import java.util.ArrayList;

public class Account {

	private String name, password;
	public int id;
	private ArrayList<Charact> characters = new ArrayList<>();
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Charact> getCharacters() {
		return characters;
	}
	public void setCharacters(ArrayList<Charact> characters) {
		this.characters = characters;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[id:");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	
}
