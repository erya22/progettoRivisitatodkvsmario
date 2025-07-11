package defaultmain;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import javax.swing.JFrame;

import controller.PlayerController;
import model.ActionState;
import model.Direction;
import model.Player;
import view.GamePanel;
import view.MapView;

public class GameLauncher {
	public static void main(String[] args) {
		
		 HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames = new HashMap<>(); // temporaneo, poi riempirai
		 
		 Player player = new Player(
				 0, 0,         		// x, y
				 4, 0,             // velocityX, velocityY
				 27, 27,           // width, height
				 spriteFrames,
				 "Mario",
				 0, 0,             // currentFrameIndex, frameCounter
				 10,               // frameDelay
				 1,                // spriteNumber
				 15                // jumpStrength
				 );
		
		PlayerController pcontroller = new PlayerController(player);
		MapView mapView = new MapView();
		
		// JFrame
        JFrame frame = new JFrame("Donkey Kong VS Mario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        
        GamePanel panel = new GamePanel(player, pcontroller, mapView);
        frame.add(panel);
        frame.setVisible(true);
		
		// Simula un "loop" di aggiornamento (console/log only)
        while (true) {
            pcontroller.updateMovement();
            panel.repaint();
            try {
                Thread.sleep(16); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
