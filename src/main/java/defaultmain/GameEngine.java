package defaultmain;

import controller.PlayerController;
import model.World;
import view.GamePanel;

public class GameEngine implements Runnable {
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
            world.update(world.getBeams());
            if (world.getDk().canThrowBarrel()) {
                world.addItem(world.getDk().throwBarrel());
            }
            controller.updateMovement();
            panel.repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}

