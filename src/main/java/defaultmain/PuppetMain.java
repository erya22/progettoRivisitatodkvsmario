package defaultmain;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import model.ActionState;
import model.Direction;
import model.Player;
import model.Terrain;

public class PuppetMain {
	public static void main(String[] args) {
		
        // Simulazione dei dati base per test
        int x = 100;
        int y = 300;
        int velocityX = 4;
        int velocityY = 0;
        int width = 32;
        int height = 32;
        int frameIndex = 0;
        int frameCounter = 0;
        int frameDelay = 10;
        int spriteNumber = 0;
        int jumpStrength = 12;

        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> dummySprites = new HashMap<>();

        Player player = new Player(
            x, y, velocityX, velocityY, width, height,
            dummySprites, "Mario", frameIndex, frameCounter, frameDelay, spriteNumber, jumpStrength
        );

        // Imposta stato iniziale
        player.setCurrentDirection(Direction.UP);
        player.setCurrentActionState(ActionState.CLIMBING);
        player.setCurrentTerrain(Terrain.LADDER);
        // 🔹 Test movimento a destra
        player.walk(Direction.RIGHT);
        player.update();
        player.updateAnimation();

        // 🔹 Test salto
        player.jump(true);
        for (int i = 0; i < 40; i++) {
            player.update(); // simula 20 tick del gioco
            player.updateAnimation();
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }

        // 🔹 Test arrampicata (simulata)
        player.setCurrentTerrain(model.Terrain.LADDER);
        player.climb(Direction.UP);
        player.update();
        player.updateAnimation();

        System.out.println("Test completato.");
    }
}
