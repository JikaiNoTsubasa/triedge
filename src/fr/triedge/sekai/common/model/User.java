package src.fr.triedge.sekai.common.model;

public class User {

	private String name, password;
	public int id;
	private Charact charact;
	
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
	public Charact getCharact() {
		return charact;
	}
	public void setCharact(Charact charact) {
		this.charact = charact;
	}
}
