package src.fr.triedge.sekai.server.controller;

import org.apache.log4j.Logger;

import src.fr.triedge.sekai.common.model.Charact;
import src.fr.triedge.sekai.common.model.Config;

public class Engine implements Runnable{
	
	static final Logger log = Logger.getLogger(Engine.class);
	private boolean running = true;
	private Controller controller;
	
	public Engine(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void run() {
		long sleep = Long.valueOf(Config.param.getProperty("engine.sleep", "100"));
		while(isRunning()) {
			update(System.currentTimeMillis());
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				log.error("Thread interrupted", e);
			}
		}
	}

	private void update(long currentTimeMillis) {
		// Process inputs
		getController().getDelegates().forEach((del) ->{
			if (del.inputs != null) {
				if (del.inputs.xAxis != 0 || del.inputs.yAxis != 0) {
					moveEntity(del);
				}
			}
		});
		
		// Process NPC
		
	}

	private void moveEntity(Charact ch, float xn, float yn) {
		//TODO - check map collision and TP
		
		//TODO - check entity collision
	}

	private void moveEntity(ServerDelegate del) {
		moveEntity(del.getCharacter(), del.inputs.xAxis, del.inputs.yAxis);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
