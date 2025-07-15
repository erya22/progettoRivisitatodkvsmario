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
                log.debug("HAI VINTO!");
                stop();
                break;
            }
        	
        	if (world.getPlayer().getPlayerState() == PlayerState.HIT_BY_BARREL) {
        		log.debug("hit by barrel");
        		world.setPaused(true);
        		if (world.getPlayer().animateHit()) {
        	        
        			panel.repaint();
        	        try {
        	            Thread.sleep(32);
        	        } catch (InterruptedException e) {
        	            running = false;
        	        }
        	        continue;
        	    } else {
        	    	world.setPaused(false);
        	        // Quando l'animazione è finita, blocca qui se il giocatore è morto
        	        if (world.getPlayer().getPlayerState() == PlayerState.DEAD) {
        	            log.debug("GAME OVER: Mario è morto");
        	            stop(); // Ferma il game loop
        	            break;
        	        }
        	    }
        		
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

