package utils;

import java.awt.Rectangle;
import java.awt.Toolkit;

public class Constants {
	
	//SCREEN SETTINGS
	public static int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

	//GLOBAL VARIABLES THAT HELP SET THE MAP
	public static final int U_TILE_COLS = 28;
	public static final int  U_TILE_ROWS = 32;
	public static final int U_TILE_SIZE = 32;
	public static int TILE_SIZE = SCREEN_HEIGHT / U_TILE_ROWS;
	
	//MAP
    public static final int MAP_WIDTH = U_TILE_COLS * TILE_SIZE; // Numero di colonne * dimensione tile
    public static final int MAP_HEIGHT = U_TILE_ROWS  * TILE_SIZE;
	public static final Rectangle MAP_BOUNDS = new Rectangle(0, 0, MAP_WIDTH, MAP_HEIGHT);
    
    public static final int MAP_OFFSET_X = (SCREEN_WIDTH - MAP_WIDTH) / 2;
    public static final int MAP_OFFSET_Y = (SCREEN_HEIGHT - MAP_HEIGHT) / 2;
    
    
	
}
