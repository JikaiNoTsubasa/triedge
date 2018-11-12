package src.fr.triedge.sekai.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import src.fr.triedge.sekai.common.model.Charact;
import src.fr.triedge.sekai.common.model.User;


public class DatabaseManagement {

	private Connection connection;
	static final Logger log = Logger.getLogger(DatabaseManagement.class);
	

	public DatabaseManagement(Connection connection) {
		super();
		this.setConnection(connection);
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void disconnect() throws SQLException {
		getConnection().close();
	}

	public boolean userExists(String user, String pwd) {
		try {
			ResultSet set = JDBC.query(getConnection(), "select * from sk_account where account_user="+user+" and account_password="+pwd);
			if (set.next())
				return true;
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
		}
		return false;
	}

	public User getUser(String user, String pwd) {
		try {
			ResultSet set = JDBC.query(getConnection(), "select * from sk_account left join sk_character on character_id=account_character where account_user="+user+" and account_password="+pwd);
			User usr = new User();
			while(set.next()) {
				Charact charact = new Charact();
				charact.setName("character_name");
				usr.setId(set.getInt("account_id"));
				usr.setName(set.getString("account_name"));
				usr.setCharact(charact);
			}
			
			
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
		}
		return null;
	}

	public void createCharact(User user_obj, String username) {
		String sql = "insert into sk_character(character_name, character_account) values ('"+username+"',"+user_obj.getId()+")";
		
		try {
			JDBC.insert(getConnection(), sql);
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
		}
	}

	public boolean createUser(String user, String pwd) {
		String sql = "insert into sk_account(account_name, account_password) values ('"+user+"',"+pwd+")";
		try {
			int id = JDBC.insert(getConnection(), sql);
			if (id > 0)
				return true;
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
		}
		return false;
	}
	
	
}
