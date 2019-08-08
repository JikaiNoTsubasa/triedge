package fr.triedge.sekai.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.triedge.sekai.server.model.DatabaseInfo;

public class JDBC{

	public static Connection connect(DatabaseInfo info) throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://"+info.getHost()+":"+info.getPort()+"/"+info.getName()+"?user="+info.getUser()+"&password="+info.getPassword()+"&serverTimezone=UTC");
	}
	
	public static ResultSet query(Connection db, String sql) throws SQLException {
		return db.createStatement().executeQuery(sql);
	}
	
	/**
	 * Insert and return the inserted id
	 * @param db
	 * @param sql
	 * @return inserted ID
	 * @throws SQLException
	 */
	public static int insert(Connection db, String sql) throws SQLException {
		int risultato = -1;
		Statement stmt = db.createStatement();
		stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            risultato = rs.getInt(1);
        }
        rs.close();
        stmt.close();
        return risultato;
	}
	
	public static void disconnect(Connection db) throws SQLException {
		db.close();
	}

}
