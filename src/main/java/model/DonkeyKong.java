package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;
import utils.Sprite;

/**
 * Lancia barili ogni tot tempo
 */
public class DonkeyKong extends Entity {
	private static final Logger log = LoggerFactory.getLogger(DonkeyKong.class);
	private long lastThrowTime;
	private long throwCooldown = 3000; //3 s
	
	
	
	public DonkeyKong(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name,
			int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay,
				spriteNumber);
		this.lastThrowTime = System.currentTimeMillis();
		setSpriteFrames(loadSpriteFrames());
	}

	public HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> loadSpriteFrames() {

	    HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteMap = new HashMap<>();

	    // Target size you want, e.g. 32x32 or Constants.TILE_SIZE
	    int targetWidth = Constants.TILE_SIZE * 4;  
	    int targetHeight = Constants.TILE_SIZE * 2;

	    BufferedImage imgRight = Sprite.resize(Sprite.DK_LANCIA_BARILE_R.img(), targetWidth, targetHeight);
	    BufferedImage imgLeft = Sprite.resize(Sprite.DK_L.img(), targetWidth, targetHeight);
	    BufferedImage imgRest = Sprite.resize(Sprite.DK_REST.img(), targetWidth, targetHeight);
	    BufferedImage imgRoar = Sprite.resize(Sprite.DK_ROAR.img(), targetWidth, targetHeight);
	    BufferedImage imgRoar2 = Sprite.resize(Sprite.DK_ROAR2.img(), targetWidth, targetHeight);
	    BufferedImage[] allSprites = new BufferedImage[] { 
	    		imgLeft,
	    		imgRest,
	    		imgRight,
	    		imgLeft,
	    		imgRoar,
	    		imgRoar2,
	    		imgRight
	    };
	    spriteMap.put(new SimpleEntry<>(ActionState.STATIC, Direction.NONE), allSprites);

	   
	  
	    // Fallback for missing states/directions
	    for (ActionState state : ActionState.values()) {
	        for (Direction dir : Direction.values()) {
	            spriteMap.putIfAbsent(
	                new SimpleEntry<>(state, dir),
	                allSprites // fallback
	            );
	        }
	    }

	    return spriteMap;
	}

	
	public boolean canThrowBarrel() {
		long currentTime = System.currentTimeMillis();
		return ((currentTime - lastThrowTime) >= throwCooldown) && (getCurrentFrameIndex() == 6);
	}
	
	public Barrel throwBarrel() {
		lastThrowTime = System.currentTimeMillis();
				
		int barrelX = this.getX() + this.getWidth();
		int barrelY = this.getY() + (this.getHeight() / 2);
		
		log.debug("BARILE LANCIATO!");
		
		return new Barrel(barrelX, barrelY, Constants.TILE_SIZE,
				null, ActionState.FALLING, Direction.DOWN,
				Terrain.AIR, 0, 0, 10, 1, 2, 0, BarrelState.ACTIVE);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePhysics(ArrayList<Collision> beams) {
		// TODO Auto-generated method stub
		
	}

}
