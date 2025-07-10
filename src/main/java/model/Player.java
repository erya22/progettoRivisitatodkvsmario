package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * TODO: movimento, salto con fisica, 
 * scale, collisione con barile
 */
public class Player extends Entity {

	public Player(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name, int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay, spriteNumber);
		
	}
	
	public void walk() {
		
	}
	
	public void climb() {
		
	}
	
	public void jump() {
		
	}

	private void loadPlayerSprites() {
	    // Carica immagini e riempi spriteFrames
		HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames = this.getSpriteFrames();
        try {
        	//IDLE
        	BufferedImage[] idleR = new BufferedImage[1];
        	BufferedImage[] idleL = new BufferedImage[1];
        	spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.IDLE, Direction.RIGHT), idleR);
        	spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.IDLE, Direction.LEFT), idleL);
            
            // UP/DOWN
            BufferedImage[] up = new BufferedImage[7];
            for (int i = 0; i < 7; i++) {
                up[i] = ImageIO.read(getClass().getResourceAsStream("/PLAYER/b" + (i+1) + ".png"));
            }
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.CLIMBING, Direction.UP), up);
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.CLIMBING, Direction.DOWN), up);

            // RIGHT
            BufferedImage[] right = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                right[i] = ImageIO.read(getClass().getResourceAsStream("/PLAYER/a" + (i + 1) + ".png"));
            }
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.WALKING, Direction.RIGHT), right);

            // LEFT
            BufferedImage[] left = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                left[i] = ImageIO.read(getClass().getResourceAsStream("/PLAYER/m" + (i + 1) + ".png"));
            }
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.WALKING, Direction.LEFT), left);
            
            // JUMP AND FALL ANIMATIONS
            BufferedImage[] jumpR = new BufferedImage[1];
            jumpR[0] = ImageIO.read(getClass().getResourceAsStream("/PLAYER/c3.png"));
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.JUMPING, Direction.RIGHT), jumpR);
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.FALLING, Direction.RIGHT), jumpR);
            
            BufferedImage[] jumpL = new BufferedImage[1];
            jumpL[0] = ImageIO.read(getClass().getResourceAsStream("/PLAYER/m3.png"));
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.JUMPING, Direction.LEFT), jumpL);
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.FALLING, Direction.LEFT), jumpL);
            
            BufferedImage[] hitFrames = new BufferedImage[5];
            // DEATH AND HIT ANIMATION
            for (int i = 0; i < 5; i++) {
                hitFrames[i] = ImageIO.read(getClass().getResourceAsStream("/PLAYER/e" + (i + 1) + ".png"));
            }
            
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.HIT, Direction.RIGHT), hitFrames);
            //TODO: When used, reverse order of the array
            spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.HIT, Direction.LEFT), hitFrames);
            


        } catch (IOException e) {
            e.printStackTrace();
        }

	}

	//TODO: aggiorna i dati per la view
	@Override
	public void update() {
		
	}
	
	//Aggiorna i dati per l'animazione
	@Override
	public void updateAnimation() {
		
	}
	
}
