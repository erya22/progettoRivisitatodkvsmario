package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Classe astratta base per tutti gli oggetti di gioco.
 */
public abstract class GameItem {

    private int x, y;
    private int width, height;

    //ANIMAZIONE
    private HashMap<Direction, BufferedImage[]> spriteFrames;
	private ActionState currentActionState;
	private Direction currentDirection;
	private Terrain currentTerrain;
    
    //ANIMAZIONE COUNTERS
    private int currentFrameIndex;
	private int frameCounter;
	private int frameDelay;
	private int spriteNumber;
    
	/**
     * Costruttore per creare un oggetto di gioco con parametri specifici.
     * 
     * @param x Posizione orizzontale iniziale
     * @param y Posizione verticale iniziale
     * @param width Larghezza dell'oggetto
     * @param height Altezza dell'oggetto
     * @param sprites Mappa delle animazioni per direzione
     * @param currentActionState Stato d'azione iniziale
     * @param currentDirection Direzione iniziale
     * @param currentTerrain Terreno iniziale
     * @param currentFrameIndex Indice del frame iniziale dell'animazione
     * @param frameCounter Contatore frame iniziale
     * @param frameDelay Ritardo fra frame
     * @param spriteNumber Numero totale di frame dell'animazione
     */
    public GameItem(int x, int y, int width, int height, HashMap<Direction, BufferedImage[]> sprites,
    		ActionState currentActionState, Direction currentDirection, Terrain currentTerrain, int currentFrameIndex, int frameCounter, int frameDelay,
    		int spriteNumber) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteFrames = sprites;
        this.currentActionState = currentActionState;
        this.currentDirection = currentDirection;
        this.currentFrameIndex = currentFrameIndex;
        this.currentTerrain = currentTerrain;
        this.frameCounter = currentFrameIndex;
        this.frameDelay = frameDelay;
        this.spriteNumber = spriteNumber;
    }

	/**
	 *  Metodo astratto per l'aggiornamento logico
	 */
	public abstract void update();
	
	/**
     * Aggiorna l'animazione incrementando il frame corrente in base al ritardo.
     */
	public void updateAnimation() {
		setSpriteNumber(getCurrentAnimationFrames().length);
		setFrameCounter(getFrameCounter() + 1);
		if (getFrameCounter() >= getFrameDelay()) {
			setFrameCounter(0);
			setCurrentFrameIndex((getCurrentFrameIndex() + 1) % getSpriteNumber());
		}
	}

    //----GETTERS AND SETTERS----
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public BufferedImage[] getCurrentAnimationFrames() {
	    return spriteFrames.get(currentDirection);
	}
	
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

	public HashMap<Direction, BufferedImage[]> getSpriteFrames() {
		return spriteFrames;
	}

	public void setSpriteFrames(HashMap<Direction, BufferedImage[]> spriteFrames) {
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

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public BufferedImage getCurrentFrame() {
		updateAnimation();
	    BufferedImage[] sprites = spriteFrames.get(getCurrentDirection());
	    return sprites[getCurrentFrameIndex()];
	}
	
	

    
    
}