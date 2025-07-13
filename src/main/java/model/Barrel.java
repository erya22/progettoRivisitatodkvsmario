package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public Barrel(int x, int y, int radius, HashMap<Direction, BufferedImage[]> sprites,
			ActionState currentActionState, Direction currentDirection, Terrain currentTerrain, int currentFrameIndex,
			int frameCounter, int frameDelay, int spriteNumber, int velocityX, int velocityY, BarrelState barrelState) {
		super(x, y, radius, radius, sprites, currentActionState, currentDirection, currentTerrain, currentFrameIndex,
				frameCounter, frameDelay, spriteNumber);
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.barrelState = barrelState;
	}

	public void roll() {
		if (getCurrentTerrain() == Terrain.BEAM) {
			if (getCurrentDirection() == Direction.RIGHT) {
				setX(getX() + getVelocityX());
			} else {
				setX(getX() - getVelocityY());
			}
		} else {
			setCurrentActionState(ActionState.FALLING);
			setCurrentDirection(Direction.DOWN);
			setY(getY() + 1);
		}
		
	}
	
	public boolean isFalling(ArrayList<Collision> beams) {
	
		for (Collision beam : beams) {
			if (beam.getBounds().intersects(this.getBounds())) {
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
//	
//	@Override
//	public void updatePhysics(ArrayList<Collision> beams) {
//	    // Se sei in aria o in caduta, applichi gravità
//	    if (getCurrentTerrain() == Terrain.AIR || getCurrentActionState() == ActionState.FALLING) {
//	        setVelocityY(getVelocityY() + 1); // gravità
//	        setY(getY() + getVelocityY());
//
//	        for (Collision beam : beams) {
//	            if (getFeetBounds().intersects(beam.getBounds())) {
//	                int beamY = beam.getY();
//	                int diffY = Math.abs(beamY - getY());
//
//	                // Se cadi da troppo in alto, cambia direzione
//	                if (previousBeamY != -1 && Math.abs(beamY - previousBeamY) > maxStepUp) {
//	                    toggleDirection();
//	                }
//
//	                setY(beamY - getHeight());
//	                setVelocityY(0);
//	                setCurrentTerrain(Terrain.BEAM);
//	                setCurrentActionState(ActionState.ROLLING);
//	                previousBeamY = beamY;
//	                return;
//	            }
//	        }
//
//	        // Non hai toccato nulla
//	        setCurrentTerrain(Terrain.AIR);
//	        setCurrentActionState(ActionState.FALLING);
//	        return;
//	    }
//
//	    // Se sei su una trave e stai rotolando
//	    if (getCurrentTerrain() == Terrain.BEAM && getCurrentActionState() == ActionState.ROLLING) {
//	        roll();
//	    }
//	}
	
	@Override
	public void updatePhysics(ArrayList<Collision> beams) {
	    if (getCurrentActionState() == ActionState.FALLING) {
	        applyGravity();

	        for (Collision beam : beams) {
	        	if (getFeetBounds().intersects(beam.getBounds())) {
	        		
	        		int beamY = beam.getY();
	        		int currentY = getY();
	        		int diffY = Math.abs(currentY - beamY);
	        		
	        		if (previousBeamY != -1 && diffY > maxStepUp) {
	        			toggleDirection(); // ← cambia direzione se salto alto
	        		}
	        		
	        		// Allineamento verticale alla nuova trave
	        		setY(beamY - getHeight());
	        		setVelocityY(0);
	        		setCurrentTerrain(Terrain.BEAM);
	        		setCurrentActionState(ActionState.ROLLING);
	        		previousBeamY = beamY;
	        		
	        		return; // esce dopo aver toccato la beam
	        	}
	        }
	    }

	    //se non interseca alcuna beam
	    setCurrentTerrain(Terrain.AIR);
	    setCurrentActionState(ActionState.FALLING);
	}
	


	public void toggleDirection() {
	    if (getCurrentDirection() == Direction.RIGHT) {
	        setCurrentDirection(Direction.LEFT);
	    } else {
	        setCurrentDirection(Direction.RIGHT);
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
	    return new Rectangle(getX(), getY() + getHeight() - 4, getWidth(), 4); // parte bassa
	}

	public Direction getPreviousDirection() {
		return previousDirection;
	}

	public void setPreviousDirection(Direction previousDirection) {
		this.previousDirection = previousDirection;
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
	
	

}
