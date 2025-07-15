package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;

import javax.imageio.ImageIO;

import utils.Constants;
import utils.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class Peach extends Entity {

	public Peach(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name,
			int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay,
				spriteNumber);
		setSpriteFrames(loadSpriteFrames());
	}
	
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
		setFrameCounter(getFrameCounter() + 1);
		if (getFrameCounter() >= getFrameDelay()) {
			setFrameCounter(0);
			setCurrentFrameIndex((getCurrentFrameIndex() + 1) % getSpriteNumber());
		}
	}
	
	public boolean isCollidingWithMario(Player player) {
		if (getBounds().intersects(player.getBounds())) {
			return true;
		} else {
			return false;
		}
	}

}
