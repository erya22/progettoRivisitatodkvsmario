// GameSetter.java
package defaultmain;

import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import controller.PlayerController;
import model.ActionState;
import model.Direction;
import model.DonkeyKong;
import model.Entity;
import model.GameItem;
import model.Peach;
import model.Player;
import model.PlayerState;
import model.TileMap;
import model.World;
import utils.Constants;
import utils.TileMapLoader;
import view.GamePanel;
import view.MapView;
import view.SideMenuView;

public class GameSetter {
	
	private World world;
	
    private Player player;
    private DonkeyKong dk;
    private Peach peach;
    private ArrayList<GameItem> items;
    private ArrayList<Entity> entities;
    
    private PlayerController pcontroller;
    
    private MapView mapView;
    private GamePanel panel;
    private SideMenuView sideMenu;
    private GameEngine engine;

	public void setupGame() {
        //LISTE
        items = new ArrayList<>();
        entities = new ArrayList<>();
        
        // Crea sprite mappe, entità e oggetti
        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFramesPlayer = new HashMap<>();
        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFramesDK = new HashMap<>();
        HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFramesPeach = new HashMap<>();
        
        
        //MAPPA
        TileMap map = TileMapLoader.loadMap();
        mapView = new MapView();
        
        //PLAYER
        player = new Player(0,
        		Constants.MAP_HEIGHT - (Constants.TILE_SIZE*2),
        		4,
        		0,
        		Constants.TILE_SIZE,
        		Constants.TILE_SIZE,
        		spriteFramesPlayer,
        		"Mario",
        		0,
        		0,
        		10,
        		10,
        		12,
        		PlayerState.ALIVE);
        pcontroller = new PlayerController(player);
        
        //DK
        dk = new DonkeyKong(Constants.TILE_SIZE*2, //x
        		(Constants.TILE_SIZE * 8) + (Constants.TILE_SIZE / 2) + (Constants.TILE_SIZE / 4), //y
        		0, //vx
        		0, //vy
        		Constants.TILE_SIZE * 2, //width
        		Constants.TILE_SIZE * 2, //height
        		spriteFramesDK, //sprites
        		"Donkey Kong", //name
        		0, //currentFrameIndex
        		0, //currentFrameCounter
        		50, //frameDelay
        		0); //spriteNumber
        //TODO: METODO LOADSPRITES!
        
        //PEACH
        peach = new Peach(12 * Constants.TILE_SIZE, 5 * Constants.TILE_SIZE, 0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE * 2, spriteFramesPeach, "Peach", 0, 0, 10, 1);        
        
        //MONDO
        world = new World(map, player, dk, peach);
        world.setItems(items);
        world.setEntities(entities);
        
        //PANNELLO DI GIOCO
        panel = new GamePanel(world, pcontroller, mapView); 
        
        //MENU
        engine = new GameEngine(world, panel, pcontroller);
        sideMenu = new SideMenuView(player, engine);
    }

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public GamePanel getPanel() {
		return panel;
	}

	public void setPanel(GamePanel panel) {
		this.panel = panel;
	}

	public PlayerController getPcontroller() {
		return pcontroller;
	}
    
	public SideMenuView getSideMenu() {
		return sideMenu;
	}
    
    public GameEngine getEngine() {
		return engine;
	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}
}
