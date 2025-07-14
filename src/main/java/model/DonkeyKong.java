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

		HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteMap = new HashMap<SimpleEntry<ActionState,Direction>, BufferedImage[]>();
		
		// dx/sx prendi barili
		BufferedImage[] dx = new BufferedImage[1];
		dx[0] = Sprite.DK_LANCIA_BARILE_R.img();
		spriteMap.put(new SimpleEntry<>(ActionState.THROWING, Direction.RIGHT), dx);
		BufferedImage[] sx = new BufferedImage[1];
		sx[0] = Sprite.DK_L.img();
		spriteMap.put(new SimpleEntry<>(ActionState.STATIC, Direction.LEFT), dx);
		
		// urlo selvaggio
		BufferedImage[] rest = new BufferedImage[1];
		rest[0] = Sprite.DK_REST.img();
		spriteMap.put(new SimpleEntry<>(ActionState.STATIC, Direction.NONE), dx);
		BufferedImage[] urlo = new BufferedImage[1];
		urlo[0] = Sprite.DK_ROAR.img();
		spriteMap.put(new SimpleEntry<>(ActionState.ROARING, Direction.NONE), dx);
		
		return spriteMap;
		
	}
	
	public boolean canThrowBarrel() {
		long currentTime = System.currentTimeMillis();
		return (currentTime - lastThrowTime) >= throwCooldown;
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

	@Override
	public void updateAnimation() {
		// TODO Auto-generated method stub
		
	}

}
