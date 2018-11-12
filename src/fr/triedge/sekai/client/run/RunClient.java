
package src.fr.triedge.sekai.client.run;

import src.fr.triedge.sekai.client.controller.Controller;

public class RunClient {

	public static void main(String[] args) {
		Controller client = new Controller();
		client.init();
		//client.startLoop();
	}

}
