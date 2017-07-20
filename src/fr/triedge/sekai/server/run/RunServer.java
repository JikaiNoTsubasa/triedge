package fr.triedge.sekai.server.run;

import fr.triedge.sekai.server.controller.Controller;

public class RunServer {

	public static void main(String[] args) {
		Controller server = new Controller();
		server.init();
	}

}
