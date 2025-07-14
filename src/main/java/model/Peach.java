package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;

import javax.imageio.ImageIO;

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
		BufferedImage[] images = new BufferedImage[1];
		
		images[0] = Sprite.PEACH.img();
		
		spriteMap.put(new SimpleEntry<>(ActionState.IDLE, Direction.NONE), images);
		
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
		// TODO Auto-generated method stub
		
	}
	
	public boolean isCollidingWithMario(Player player) {
		if (getBounds().intersects(player.getBounds())) {
			return true;
		} else {
			return false;
		}
	}

}
