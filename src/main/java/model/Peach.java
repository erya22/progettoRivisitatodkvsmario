package model;

import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;

import utils.Constants;
import utils.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Rappresenta il personaggio Peach nel gioco, estendendo la classe {@link Entity}
 */
public class Peach extends Entity {

	/**
     * Costruttore.
     * 
     * @param x posizione orizzontale iniziale
     * @param y posizione verticale iniziale
     * @param velocityX velocità orizzontale iniziale
     * @param velocityY velocità verticale iniziale
     * @param width larghezza del personaggio
     * @param height altezza del personaggio
     * @param spriteFrames mappa delle animazioni basata su stato e direzione
     * @param name nome del personaggio
     * @param currentFrameIndex indice del frame corrente nell'animazione
     * @param frameCounter contatore dei frame trascorsi
     * @param frameDelay ritardo fra frame animazione
     * @param spriteNumber numero di sprite nell'animazione corrente
     */
	public Peach(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name,
			int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay,
				spriteNumber);
		setSpriteFrames(loadSpriteFrames());
	}
	
	/**
     * Carica la mappa degli sprite per Peach, associando per ogni coppia (ActionState, Direction) una serie di immagini.
     * @return Mappa degli sprite per le animazioni di Peach
     */
	private HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> loadSpriteFrames() {
		HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteMap = new HashMap<SimpleEntry<ActionState,Direction>, BufferedImage[]>();
		
		int targetWidth = Constants.TILE_SIZE;
		int targetHeight = Constants.TILE_SIZE*2;
		
		BufferedImage[] images = new BufferedImage[] {
				Sprite.resize(Sprite.PEACH.img(), targetWidth, targetHeight)
		};
		
		spriteMap.put(new SimpleEntry<>(ActionState.IDLE, Direction.NONE), images);
		
		for (ActionState state : ActionState.values()) {
		    for (Direction dir : Direction.values()) {
		        spriteMap.putIfAbsent(
		            new SimpleEntry<>(state, dir),
		            images // fallback
		        );
		    }
		}
		
		return spriteMap;
	}

	/**
	 * Verifica collisione con il layer.
	 * @param player Player con cui può collidere
	 * @return true se collide, false viceversa
	 */
	public boolean isCollidingWithMario(Player player) {
		if (getBounds().intersects(player.getBounds())) {
			return true;
		} else {
			return false;
		}
	}
	
	//----UNIMPLEMENTED METHODS----
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePhysics(ArrayList<Beam> beams) {
		// TODO Auto-generated method stub
		
	}

}
