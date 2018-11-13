package src.fr.triedge.sekai.common.model;

public class Charact {

	public float x,y;
	public int level = 1, id;
	private String name;
	private boolean online;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("[id:");
		builder.append(id);
		builder.append("]");
		builder.append("[lvl:");
		builder.append(level);
		builder.append("]");
		return builder.toString();
	}
	
	
}
