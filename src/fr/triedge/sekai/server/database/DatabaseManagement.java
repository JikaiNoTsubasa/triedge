package fr.triedge.sekai.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import fr.triedge.sekai.common.model.Charact;
import fr.triedge.sekai.common.model.Item;
import fr.triedge.sekai.common.model.Npc;
import fr.triedge.sekai.common.model.Account;


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
	public Account getUser(String user, String pwd) {
		try {
			ResultSet set = JDBC.query(getConnection(), "select * from sk_account left join sk_character on character_id=account_character where account_user="+user+" and account_password="+pwd);
			Account usr = new Account();
			while(set.next()) {
				Charact charact = new Charact();
				charact.setName("character_name");
				usr.setId(set.getInt("account_id"));
				usr.setName(set.getString("account_name"));
				usr.getCharacters().add(charact);
			}
			
			
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
		}
		return null;
	}
	
	public void createCharact(Account user_obj, String username) {
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
	
	public ArrayList<Account> getAllUsers(){
		ArrayList<Account> accounts = new ArrayList<>();
		String sql = "select * from sk_account";
		
		try {
			ResultSet set = JDBC.query(getConnection(), sql);
			while(set.next()) {
				Account account = new Account();
				account.setId(set.getInt("account_id"));
				account.setName(set.getString("account_name"));
				account.setPassword(set.getString("account_password"));
				accounts.add(account);
				log.debug("# Loaded Account: "+account);
				
				String sqlChar = "select * from sk_character where character_account="+account.getId();
				ResultSet setChar = JDBC.query(getConnection(), sqlChar);
				while(setChar.next()) {
					Charact ch = new Charact();
					ch.setName(setChar.getString("character_name"));
					ch.id = setChar.getInt("character_id");
					ch.level = setChar.getInt("character_level");
					ch.x = setChar.getFloat("character_x");
					ch.y = setChar.getFloat("character_y");
					ch.speed = setChar.getFloat("character_speed");
					account.getCharacters().add(ch);
					log.debug("# -> Loaded Character: "+ch);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
			return null;
		}
		
		return accounts;
	}
	
	public ArrayList<Item> getAllItems(){
		ArrayList<Item> items = new ArrayList<>();
		String sql = "select * from sk_item";
		try {
			ResultSet set = JDBC.query(getConnection(), sql);
			while (set.next()) {
				Item item = new Item();
				item.setId(set.getInt("item_id"));
				item.setName(set.getString("item_name"));
				item.setDesc(set.getString("item_desc"));
				item.setGrade(set.getString("item_grade"));
				item.setAtk(set.getInt("item_atk"));
				item.setMatk(set.getInt("item_matk"));
				item.setDef(set.getInt("item_def"));
				item.setMdef(set.getInt("item_mdef"));
				items.add(item);
				log.debug("# Loaded Item: "+item);
			}
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
			return null;
		}
		return items;
	}

	public ArrayList<Npc> getAllNPC() {
		ArrayList<Npc> npcs = new ArrayList<>();
		String sql = "select * from sk_npc left join sk_type on npc_type=type_id";
		try {
			ResultSet set = JDBC.query(getConnection(), sql);
			while (set.next()) {
				Npc npc = new Npc();
				npc.id = set.getInt("npc_id");
				npc.speed = set.getFloat("npc_speed");
				npc.setName(set.getString("npc_name"));
				npc.level = set.getInt("npc_level");
				npc.setType(set.getString("type_name"));
				npcs.add(npc);
				log.debug("# Loaded Item: "+npc);
			}
		} catch (SQLException e) {
			log.error("Cannot execute SQL", e);
			return null;
		}
		return npcs;
	}
}
