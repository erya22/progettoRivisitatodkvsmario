package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Sprite {
	
	
	PEACH("/NPCS/27peach.png"),
	DK_LANCIA_BARILE_R("/NPCS/h6.png"),
	DK_L("/NPCS/h4.png"),
	DK_REST("/NPCS/h1.png"),
	DK_ROAR("/NPCS/h3.png"),
	MARIO_IDLE_CLIMB("/PLAYER/b1.png"),
	MARIO_WALK_R("/PLAYER/a1.png"),
	MARIO_WALK_R1("/PLAYER/a2.png"),
	MARIO_WALK_R2("/PLAYER/a3.png"),
	MARIO_WALK_R3("/PLAYER/a4.png"),
	MARIO_WALK_L("/PLAYER/m1.png"),
	MARIO_WALK_L1("/PLAYER/m2.png"),
	MARIO_WALK_JUMP_L_L2("/PLAYER/m3.png"),
	MARIO_WALK_L3("/PLAYER/m4.png"),
	MARIO_JUMP_R("/PLAYER/c3.png"),
	MARIO_HIT("/PLAYER/e1.png"),
	MARIO_HIT1("/PLAYER/e2.png"),
	MARIO_HIT2("/PLAYER/e3.png"),
	MARIO_HIT3("/PLAYER/e4.png"),
	MARIO_HIT4("/PLAYER/e5.png"),
	BARREL1("/OBJECTS/barrel1.png"),
	BARREL2("/OBJECTS/barrel2.png"),
	BARREL3("/OBJECTS/barrel3.png"),
	BARREL4("/OBJECTS/barrel4.png"),
	BARREL5("/OBJECTS/barrel5.png");
	
	private final Logger log = LoggerFactory.getLogger(Sprite.class);
	
	
	
	
	BufferedImage img;
	
	public BufferedImage img() {
		return img;
	}
	
	Sprite(String path) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug("cannot read path {}", path, e);
			throw new RuntimeException(path, e);
		}
	}

}
