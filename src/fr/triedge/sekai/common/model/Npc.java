package fr.triedge.sekai.common.model;

public class Npc {

	public float x,y,speed;
	public int level = 1, id;
	private String name, type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
