package fr.triedge.sekai.client.ui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CombatEntity;
import de.gurkenlabs.litiengine.environment.Environment;
import de.gurkenlabs.litiengine.graphics.emitters.xml.CustomEmitter;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.input.Gamepad;
import de.gurkenlabs.litiengine.input.Input;

public class TestScreen {

	public static void main(String[] args) {
		Game.getInfo().setName("LITIENGINE Demo");
	    Game.getInfo().setVersion("v0.1");
	    //Game.getInfo().setDefaultRenderScale(4);
	    Game.getRenderEngine().setBaseRenderScale(4);

	    // optional configuration stuff (needs to be registered before init)
	    
	    Game.getConfiguration().graphics().setFullscreen(true);
	    Game.getConfiguration().sound().setMusicVolume(0.2f);
	    Game.getConfiguration().input().setGamepadSupport(true);
	    Game.getConfiguration().client().setUpdaterate(60);
	    Game.getConfiguration().client().setMaxFps(60);
	    Game.getConfiguration().save();

	    Game.init();

	    Game.getScreenManager().addScreen(new GameScreen());

	    // init controllable entity and lock camera on it
	    {
	      Game.loadEnvironment(new Environment("maps/test.tmx"));

	      // add test emitter to the environment
	      Game.getEnvironment().add(new CustomEmitter(200, 200, "test.xml"));
	      final CombatEntity player = new CombatEntity();
	      
	      /*
	      final Spritesheet playerSprite = Spritesheet.load(GameDirectories.SPRITES + "rat-invoker.png", 64, 64);

	      Game.getEntityControllerManager().addController(player, new AnimationController(new Animation(playerSprite, true)));
	      Game.getEntityControllerManager().addController(player, new KeyboardEntityController<>(player));
	      Game.getEnvironment().add(player);
	      Game.setCamera(new PositionLockCamera(player));

	      Input.keyboard().onKeyTyped(KeyEvent.VK_L, (key) -> System.out.println("Key 'L' hit!"));
	      Input.keyboard().onKeyTyped(KeyEvent.VK_ESCAPE, (key) -> Game.get);
	      */
	      
	      Input.getGamepad().onPressed(Gamepad.Buttons.A, (key) -> System.out.println(key));
	      Input.getGamepad().onPoll(Gamepad.Axis.X, (axis) -> System.out.println(axis));
	    }

	    Game.start();
	}

}
