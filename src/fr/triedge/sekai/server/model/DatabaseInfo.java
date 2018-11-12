package src.fr.triedge.sekai.server.model;

public class DatabaseInfo {

	private String host, user, password, name;
	private int port;
	
	public DatabaseInfo() {
		super();
	}
	
	public DatabaseInfo(String host, String user, String password, String name, int port) {
		super();
		this.host = host;
		this.user = user;
		this.password = password;
		this.name = name;
		this.port = port;
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatabaseInfo [host=");
		builder.append(host);
		builder.append(", user=");
		builder.append(user);
		builder.append(", name=");
		builder.append(name);
		builder.append(", port=");
		builder.append(port);
		builder.append("]");
		return builder.toString();
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
