package fr.triedge.sekai.server.controller;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class Database {
	
	protected ObjectContainer db;

	public void open(String path){
		db = Db4oEmbedded.openFile(path);
	}
	
	public void close(){
		db.close();
	}
}
