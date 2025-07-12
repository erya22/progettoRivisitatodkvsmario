package model;

import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Lancia barili ogni tot tempo
 */
public class DonkeyKong extends Entity {

	
	
	public DonkeyKong(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name,
			int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay,
				spriteNumber);
		
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
