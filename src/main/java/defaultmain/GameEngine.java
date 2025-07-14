package defaultmain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.PlayerController;
import model.PlayerState;
import model.World;
import view.GamePanel;

public class GameEngine implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(GameEngine.class);
    private boolean running = true;
    private World world;
    private GamePanel panel;
    private PlayerController controller;

    public GameEngine(World world, GamePanel panel, PlayerController controller) {
        this.world = world;
        this.panel = panel;
        this.controller = controller;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
        	if (world.getPlayer().getPlayerState() == PlayerState.WINNER) {
        		//TODO: ANIMAZIONE VITTORIA
        		log.debug("HAI VINTO!");
        		stop();
        	}
            world.update(world.getBeams());
            if (world.getDk().canThrowBarrel()) {
                world.addItem(world.getDk().throwBarrel());
            }
            controller.updateMovement();
            panel.repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                log.debug("sleep failed", e);
                running = false;
            }
        }
    }
}

