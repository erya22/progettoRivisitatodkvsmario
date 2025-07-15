package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;
import utils.Sprite;

/**
 * TODO: Caduta, rotolamento, collisione scale, 
 * collisione player.
 */
public class Barrel extends GameItem {
	private static final Logger log = LoggerFactory.getLogger(GameItem.class);
	
	private int previousBeamY = -1;
	private final int maxStepUp = 0;
	private final int maxStepDown = 10;
	
	private BarrelState barrelState;
	private int velocityX;
	private int velocityY;
	
	private Direction previousDirection;
	private boolean jumpedOver = false;
	
	public Barrel(int x, int y, int radius, HashMap<Direction, BufferedImage[]> sprites,
			ActionState currentActionState, Direction currentDirection, Terrain currentTerrain, int currentFrameIndex,
			int frameCounter, int frameDelay, int spriteNumber, int velocityX, int velocityY, BarrelState barrelState) {
		super(x, y, radius, radius, sprites, currentActionState, currentDirection, currentTerrain, currentFrameIndex,
				frameCounter, frameDelay, spriteNumber);
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.barrelState = barrelState;
		setSpriteFrames(loadSprites());
	}
	
	private HashMap<Direction, BufferedImage[]> loadSprites() {
	    int targetWidth = Constants.TILE_SIZE;
	    int targetHeight = Constants.TILE_SIZE;

	    HashMap<Direction, BufferedImage[]> spriteFrames = new HashMap<>();

	    BufferedImage[] images = new BufferedImage[] {
	        Sprite.resize(Sprite.BARREL1.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL2.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL3.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL4.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL5.img(), targetWidth, targetHeight)
	    };

	    BufferedImage[] revImages = new BufferedImage[] {
    		Sprite.resize(Sprite.BARREL5.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL4.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL3.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL2.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL1.img(), targetWidth, targetHeight)
	    };

	    spriteFrames.put(Direction.RIGHT, images);
	    spriteFrames.put(Direction.LEFT, revImages);
	    spriteFrames.put(Direction.DOWN, images);

	    return spriteFrames;
	}


	public void roll(ArrayList<Collision> beams, ArrayList<TriggerZone> triggerZones) {
		if (isFalling(beams)) {
			setCurrentActionState(ActionState.FALLING);
			setCurrentDirection(Direction.DOWN);
			applyGravity();
			return;
		} 
		
		setCurrentActionState(ActionState.ROLLING);
		if (getCurrentDirection() == Direction.RIGHT) {
			setX(getX() + getVelocityX());
		} else if (getCurrentDirection() == Direction.LEFT){
			setX(getX() - getVelocityX());
		} else {
			log.debug("direzione sbagliata: {}", getCurrentDirection());
			barrelDirection(triggerZones);
		}
		
	}
	
	public void barrelDirection(ArrayList<TriggerZone> triggerZones) {
		for (TriggerZone tz : triggerZones) {
			if (tz.getBounds().intersects(getBounds())) {
				setCurrentDirection(tz.getForcedDirection());
				return;
			}
		}
		log.debug("non ho trovato la triggerZone: x{} y{} ", getX(), getY());
	}
	
	public boolean isFalling(ArrayList<Collision> beams) {
	
		for (Collision beam : beams) {
			if (beam.getBounds().intersects(getFeetBounds())) {
				return false;
			}
		}
		return true;
		
	}
	
	public boolean isCollidingWithBeam(Collision beam) {
		return this.getBounds().intersects(beam.getBounds());
	}
	
	public boolean isCollidingWithMario(Player player) {
		return this.getBounds().intersects(player.getBounds());
	}
	
	

	@Override
	public void update() {
		log.debug("DATI AGGIORNATI: {}\n", this.toString());
	}
	
	
	public void updatePhysics(ArrayList<Collision> beams, ArrayList<TriggerZone> triggerZones) {
	    boolean onBeam = false;

	    // 🔄 Controlla se è sopra una beam
	    for (Collision b : beams) {
	    	if (b.getBounds().intersects(getFeetBounds())) {
	    	    log.debug("🎯 COLLISIONE RILEVATA con beam a y={}", b.getY());
	    	    // Allinea il barile sopra la beam
	    	    setY(b.getY() - getHeight());
	    	    setVelocityY(0);
	    	    setCurrentActionState(ActionState.ROLLING);
	    	    setCurrentTerrain(Terrain.BEAM);
	    	    onBeam = true;
	    	    break;
	    	}

	    }

	    if (onBeam) {
	        // ✅ Barile sulla beam → passa a ROLLING
	        if (getCurrentActionState() != ActionState.ROLLING) {
	            log.debug("Barile atterra su BEAM → stato ROLLING");
	        }
	        setCurrentActionState(ActionState.ROLLING);
	        setCurrentTerrain(Terrain.BEAM);
	        setVelocityY(0); // blocca la caduta

	        roll(beams, triggerZones); // continua a rotolare
	    } else {
	        // ❌ Nessuna beam sotto → cade
	        if (getCurrentActionState() != ActionState.FALLING) {
	            log.debug("Barile inizia a cadere → stato FALLING");
	        }
	        setCurrentActionState(ActionState.FALLING);
	        setCurrentTerrain(Terrain.AIR);
	    }
	}

	
	public void applyGravity() {
		log.debug("Gravità applicata");
	    setY(getY() + getVelocityY());
	    setVelocityY(getVelocityY() + 1);
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
	
	public BarrelState getBarrelState() {
		return barrelState;
	}

	public void setBarrelState(BarrelState barrelState) {
		this.barrelState = barrelState;
	}
	
	public Rectangle getFeetBounds() {
	    return new Rectangle(getX(), getY() + getHeight() - 2, getWidth(), 10); // aumentato da 4 a 8 pixel
	}

	public Direction getPreviousDirection() {
		return previousDirection;
	}

	public void setPreviousDirection(Direction previousDirection) {
		this.previousDirection = previousDirection;
	}

	public boolean isJumpedOver() {
		return jumpedOver;
	}

	public void setJumpedOver(boolean jumpedOver) {
		this.jumpedOver = jumpedOver;
	}

	@Override
	public void updateAnimation() {
		setFrameCounter(getFrameCounter() + 1);
		if (getFrameCounter() >= getFrameDelay()) {
			setFrameCounter(0);
			setCurrentFrameIndex((getCurrentFrameIndex() + 1) % getSpriteNumber());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Barrel [velocityX=").append(velocityX).append(", velocityY=").append(velocityY)
				.append(", getX()=").append(getX()).append(", getY()=").append(getY()).append(", getWidth()=")
				.append(getWidth()).append(", getHeight()=").append(getHeight()).append(", getBounds()=")
				.append(getBounds()).append(", getSpriteFrames()=").append(getSpriteFrames())
				.append(", getCurrentActionState()=").append(getCurrentActionState()).append(", getCurrentDirection()=")
				.append(getCurrentDirection()).append(", getCurrentTerrain()=").append(getCurrentTerrain())
				.append(", getCurrentFrameIndex()=").append(getCurrentFrameIndex()).append(", getFrameCounter()=")
				.append(getFrameCounter()).append(", getFrameDelay()=").append(getFrameDelay())
				.append(", getSpriteNumber()=").append(getSpriteNumber()).append(", getClass()=").append(getClass())
				.append(", hashCode()=").append(hashCode()).append(", toString()=").append(super.toString())
				.append("]");
		return builder.toString();
	}
	
	

//	@Override
//	public void updatePhysics(ArrayList<Collision> beams) {
//		// TODO Auto-generated method stub
//		
//	}
	
	

}
