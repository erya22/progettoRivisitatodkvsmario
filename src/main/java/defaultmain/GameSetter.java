// GameSetter.java
package defaultmain;

import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import controller.PlayerController;
import model.ActionState;
import model.Direction;
import model.DonkeyKong;
import model.Peach;
import model.Player;
import utils.Constants;
import view.GamePanel;
import view.MapView;

public class GameSetter {
    private Player player;
    private DonkeyKong dk;
    private Peach peach;
    
    private PlayerController pcontroller;
    
    private MapView mapView;
    private GamePanel panel;

    public void setupGame() {
        // Crea sprite mappe
        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFramesPlayer = new HashMap<>();
        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFramesDK = new HashMap<>();
        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFramesPeach = new HashMap<>();
        // Crea Player
        player = new Player(0, 0, 4, 0, Constants.TILE_SIZE, Constants.TILE_SIZE, spriteFramesPlayer, "Mario", 0, 0, 10, 1, 12);

        // Crea Donkey Kong
        dk = new DonkeyKong(0, (Constants.TILE_SIZE * 8) + (Constants.TILE_SIZE / 2) + (Constants.TILE_SIZE / 4), 0, 0, Constants.TILE_SIZE * 2, Constants.TILE_SIZE * 2, spriteFramesDK, "Donkey Kong", 0, 0, 0, 0);

        //Crea Peach
        peach = new Peach(12 * Constants.TILE_SIZE, 5 * Constants.TILE_SIZE, 0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE * 2, spriteFramesPeach, "Peach", 0, 0, 0, 0);
        
        // Controller, mappa e panel
        pcontroller = new PlayerController(player);
        mapView = new MapView();
        panel = new GamePanel(player, dk, peach, pcontroller, mapView);
    }

    public GamePanel getPanel() {
        return panel;
    }

    public PlayerController getController() {
        return pcontroller;
    }

    public Player getPlayer() {
        return player;
    }

    public DonkeyKong getDonkeyKong() {
        return dk;
    }
}
