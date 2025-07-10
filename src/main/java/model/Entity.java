package model;

import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

/**
 * parent class per Player Barrel DK ecc.
 */
public abstract class Entity {
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
		
		this.currentActionState = ActionState.CLIMBING;
		this.currentDirection = Direction.UP;
		this.currentTerrain = Terrain.LADDER;
		
		this.currentFrameIndex = currentFrameIndex;
		this.frameCounter = frameCounter;
		this.frameDelay = frameDelay;
		this.spriteNumber = spriteNumber;
	}
	
	// Metodo astratto per aggiornare la logica di ogni entità a ogni frame
    public abstract void update();
	
    public abstract void updateAnimation();
    
    // Metodo per recuperare il frame attuale da disegnare, utile per la view
    public BufferedImage[] getCurrentAnimationFrames() {
        return spriteFrames.get(new SimpleEntry<>(currentActionState, currentDirection));
    }

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
    
    
}
