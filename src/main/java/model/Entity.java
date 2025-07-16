package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parent class per Player, DK, Peach.
 */
public abstract class Entity {
	private static final Logger log = LoggerFactory.getLogger(Entity.class);
	
	//VARIABILI LOGICA ENTITA'
	private int x, y;
	private int velocityX, velocityY;
	private int width, height;
	private String name;

	//ANIMAZIONE E SUO STATO
	private HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames;
	private ActionState currentActionState;
	private Direction currentDirection;
	private Terrain currentTerrain;
	
	private int currentFrameIndex;
	private int frameCounter;
	private int frameDelay;
	private int spriteNumber;
	
	/**
     * Costruttore.
     * 
     * @param x Posizione orizzontale iniziale
     * @param y Posizione verticale iniziale
     * @param velocityX Velocità orizzontale iniziale
     * @param velocityY Velocità verticale iniziale
     * @param width Larghezza entità
     * @param height Altezza entità
     * @param spriteFrames Mappa degli sprite di animazione
     * @param name Nome dell'entità
     * @param currentFrameIndex Indice frame animazione iniziale
     * @param frameCounter Contatore frame iniziale
     * @param frameDelay Ritardo frame animazione
     * @param spriteNumber Numero frame animazione
     */
	public Entity(int x, int y, int velocityX, int velocityY, int width, int height,
            HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames,
            String name, int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber) {
	
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.width = width;
		this.height = height;
		this.spriteFrames = spriteFrames;
		this.name = name;
		
		this.currentActionState = ActionState.IDLE;
		this.currentDirection = Direction.RIGHT;
		this.currentTerrain = Terrain.BEAM;
		
		this.currentFrameIndex = currentFrameIndex;
		this.frameCounter = frameCounter;
		this.frameDelay = frameDelay;
		this.spriteNumber = spriteNumber;
	}
	
	/**
	 * Metodo astratto per aggiornare la logica di ogni entità a ogni frame.
	 */
    public abstract void update();

    /**
     * Metodo astratto per aggiornare la fisica dell'entità, ad esempio per gestire collisioni.
     * Lista di collisioni con travi o altre entità
     */
    public abstract void updatePhysics(ArrayList<Collision> beams);
	
    /**
     * Aggiorna l'animazione incrementando il frame corrente in base al ritardo impostato.
     */
    public void updateAnimation() {
    	
    	setSpriteNumber(getCurrentAnimationFrames().length);
		setFrameCounter(getFrameCounter() + 1);
		if (getFrameCounter() >= getFrameDelay()) {
			setFrameCounter(0);
			setCurrentFrameIndex((getCurrentFrameIndex() + 1) % getSpriteNumber());
		}
	}
    
    /**
     *  Metodo per recuperare il frame attuale da disegnare, utile per la view.
     * @return Array di BufferedImage per l'animazione corrente
     */
    public BufferedImage[] getCurrentAnimationFrames() {
        return spriteFrames.get(new SimpleEntry<>(currentActionState, currentDirection));
    }
    
    /**
     * Restituisce il frame corrente di animazione da disegnare. Aggiorna l'animazione.
     * @return BufferedImage del frame corrente
     */
    public BufferedImage getCurrentFrame() {
    	updateAnimation();
        BufferedImage[] sprites = spriteFrames.get(new SimpleEntry<>(getCurrentActionState(), getCurrentDirection()));
        if (sprites == null) {
            System.err.println("MISSING SPRITES for " + getCurrentActionState() + " " + getCurrentDirection());
            // Fallback a IDLE_RIGHT
            sprites = spriteFrames.get(new SimpleEntry<>(ActionState.IDLE, Direction.RIGHT));
        }
//        log.debug("cfi: {} di {}", currentFrameIndex, name);
        return sprites[currentFrameIndex % sprites.length];
    }


    //----GETTERS AND SETTERS----
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> getSpriteFrames() {
		return spriteFrames;
	}

	public void setSpriteFrames(HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames) {
		this.spriteFrames = spriteFrames;
	}

	public ActionState getCurrentActionState() {
		return currentActionState;
	}

	public void setCurrentActionState(ActionState currentActionState) {
		this.currentActionState = currentActionState;
	}

	public Direction getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(Direction currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Terrain getCurrentTerrain() {
		return currentTerrain;
	}

	public void setCurrentTerrain(Terrain currentTerrain) {
		this.currentTerrain = currentTerrain;
	}

	public int getCurrentFrameIndex() {
		return currentFrameIndex;
	}

	public void setCurrentFrameIndex(int currentFrameIndex) {
		this.currentFrameIndex = currentFrameIndex;
	}

	public int getFrameCounter() {
		return frameCounter;
	}

	public void setFrameCounter(int frameCounter) {
		this.frameCounter = frameCounter;
	}

	public int getFrameDelay() {
		return frameDelay;
	}

	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
	}

	public int getSpriteNumber() {
		return spriteNumber;
	}

	public void setSpriteNumber(int spriteNumber) {
		this.spriteNumber = spriteNumber;
	}
	
	public Rectangle getFeetBounds() {
	    return new Rectangle(x + 4, y + height - 5, width - 8, 5);
	}
	
	public Rectangle getHeadBounds() {
	    return new Rectangle(x + 4, y, width - 8, 5);
	}
	
	public Rectangle getLeftBounds() {
	    return new Rectangle(x, y + 5, 5, height - 10);
	}

	public Rectangle getRightBounds() {
	    return new Rectangle(x + width - 5, y + 5, 5, height - 10);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
     * Restituisce una rappresentazione testuale dello stato dell'entità.
     */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entity [x=").append(x).append(", y=").append(y).append(", velocityX=").append(velocityX)
				.append(", velocityY=").append(velocityY).append(", width=").append(width).append(", height=")
				.append(height).append(", name=").append(name).append(", spriteFrames=").append(spriteFrames)
				.append(", currentActionState=").append(currentActionState).append(", currentDirection=")
				.append(currentDirection).append(", currentTerrain=").append(currentTerrain)
				.append(", currentFrameIndex=").append(currentFrameIndex).append(", frameCounter=").append(frameCounter)
				.append(", frameDelay=").append(frameDelay).append(", spriteNumber=").append(spriteNumber).append("]");
		return builder.toString();
	}
	
	
}
