package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.CollisionManager;
import utils.Constants;
import utils.LadderManager;

/**
 * TODO: movimento, salto con fisica, 
 * scale, collisione con barile
 */
public class Player extends Entity {
	private static final Logger log = LoggerFactory.getLogger(Player.class);
	//TODO: forse da spostare in Entity?
	private ArrayList<Collision> beams = CollisionManager.loadSampleCollisions();
	private ArrayList<Ladder> ladders = LadderManager.loadSampleLadders();
	
	//BOUNDS PER LE SCALE
	private Rectangle ladderBounds;
	private ActionState previousActionState;
	//JUMP SETTINGS
	private int jumpStrength;
	private boolean isMovingHorizontallyWhileJumping = false;
	private int jumpX = 4;
	private int ladderY = 4;
	
	public Player(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name, int currentFrameIndex, int frameCounter, int frameDelay, int spriteNumber, int jumpStrength) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay, spriteNumber);
		this.jumpStrength = jumpStrength;
	}
	
	public void walk(Direction direction) {
		if (getCurrentTerrain() == Terrain.BEAM) {
			if (getCurrentDirection() != direction) {
				setCurrentDirection(direction);
			}
			
			if (getCurrentActionState() != ActionState.WALKING)  {
				setCurrentActionState(ActionState.WALKING);
			}
			
			//CODICE AGGIUNTO ORA:
			int nextX = getX() + (direction == Direction.RIGHT ? getVelocityX() : -getVelocityX());
	        int maxStepUp = 8; // massimo quanto Mario può "salire" automaticamente

	        // Crea rettangolo piedi davanti a Mario nella prossima posizione
	        for (int dy = 0; dy <= maxStepUp; dy++) {
	            Rectangle testFeet = new Rectangle(nextX, getY() + dy + getHeight(), getWidth(), 1);

	            for (Collision beam : beams) { // usa dove hai la lista
	                if (beam.getBounds().intersects(testFeet)) {
	                	log.debug("Trave trovata!");
	                    // Trave trovata leggermente più in alto: sali
	                    setX(nextX);
	                    setY(beam.getBounds().y - getHeight()); // sali di poco
	                    log.debug("Mario cammina verso {} con salita di {}px", direction, dy);
	                    return;
	                }
	            }
	        }

	        // Nessuna trave rilevata: movimento normale orizzontale
	        setX(nextX);
	        //FINE CODICE AGGIUNTO ORA
			
			log.info("Mario cammina verso: {} a x({}) y({})", getCurrentDirection(), getX(), getY());
		}
		
		
	}
	
	public void climb(Direction direction) {
	    log.debug("TI STAI ARRAMPICANDO VERSO: {}", direction);

	    ladderBounds = getLadderBounds();
	    boolean onLadder = false;

	    for (Ladder ladder : ladders) {
	        if (ladder.getBounds().intersects(ladderBounds)) {
	            setCurrentTerrain(Terrain.LADDER);
	            onLadder = true;
	            break;
	        }
	    }

	    if (!onLadder) {
	        setCurrentTerrain(Terrain.AIR);
	        setCurrentActionState(ActionState.FALLING);
	        return;
	    }

	    if (getCurrentActionState() != ActionState.CLIMBING) {
	        setCurrentActionState(ActionState.CLIMBING);
	    }

	    // Se stava già scalando, può andare su o giù
	    if (previousActionState == ActionState.CLIMBING) {
	        if (direction == Direction.UP) {
	            setY(getY() - getLadderY());
	        } else if (direction == Direction.DOWN) {
	            // 🔴 BLOCCO CONTRO TRAVE
	            Rectangle feetAfterStep = new Rectangle(getX(), getY() + getLadderY() + getHeight(), getWidth(), 1);
	            boolean beamBelow = false;

	            for (Collision beam : beams) {
	                if (beam.getBounds().intersects(feetAfterStep)) {
	                    beamBelow = true;
	                    break;
	                }
	            }

	            if (beamBelow) {
	                log.debug("Mario non può scendere: c'è una trave sotto la scala");
	                setCurrentTerrain(Terrain.BEAM);
	                return;
	            }

	            setY(getY() + getLadderY());
	        }
	    } else {
	        // Se non stava già scalando, può solo salire
	        if (direction == Direction.UP) {
	            setY(getY() - getLadderY());
	        }
	    }
	}

	
	//TODO: CONTROLLARE TERRAIN
//	public void climb(Direction direction) {
//		log.debug("WAH TI STAI ARRAMPICANDO VERSO: {}", direction);
//		
//		ladderBounds = getLadderBounds();
//		boolean onLadder = false;
//		
//		for (Ladder ladder : ladders) {
//			if (ladder.getBounds().intersects(ladderBounds)) {
//				onLadder = true;
//				break;
//			}
//		}
//		
//		if (!onLadder) {
//	        setCurrentTerrain(Terrain.AIR);
//	        setCurrentActionState(ActionState.FALLING);
//	        return;
//	    }
//		
//		if (onLadder) {
//			setCurrentTerrain(Terrain.LADDER);
//			
//			if (direction == Direction.UP) {
//				setY(getY() - getLadderY());
//			} else if (direction == Direction.DOWN) {
//				setY(getY() + getLadderY());
//			}
//			
//			if (getCurrentActionState() != ActionState.CLIMBING) {
//				setCurrentActionState(ActionState.CLIMBING);
//			} else {
//				//Se Mario non è più sulla scala
//				setCurrentTerrain(Terrain.AIR);
//			}
//		}
//		
//	}
	
	public void jump(boolean movingHorizontally) {
		log.debug("is key pressed? {}", movingHorizontally);
		if (getCurrentTerrain() == Terrain.BEAM) {
			this.setVelocityY(-getJumpStrength());
			this.setCurrentActionState(ActionState.JUMPING);
			setMovingHorizontallyWhileJumping(movingHorizontally);
		}
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
		// Salva lo stato attuale prima di aggiornare
	    previousActionState = getCurrentActionState();
		log.debug("DATI AGGIORNATI: {}", this.toString());
	}
	
	//Aggiorna i dati per l'animazione
	@Override
	public void updateAnimation() {
		
	}
	
	@Override
	public void updatePhysics(ArrayList<Collision> beams) {
		//LIMITI ORIZZONTALI MAPPA
		if (getX() < 0){
			setX(0);
			log.debug("Mario ha colpito il bordo orizzontale sinistro della mappa");
		} else if (getX() + getWidth() > Constants.MAP_WIDTH){
			setX(Constants.MAP_WIDTH - getWidth());
			log.debug("Mario ha colpito il bordo orizzontale destro della mappa");
		}
			

		
		// Applica gravità solo se in salto o in caduta
	    if (getCurrentActionState() == ActionState.JUMPING || getCurrentActionState() == ActionState.FALLING) {
	        setCurrentTerrain(Terrain.AIR);

	        // Movimento verticale
	        setY(getY() + getVelocityY());
	        setVelocityY(getVelocityY() + 1); // forza di gravità

	        // Movimento orizzontale durante il salto
	        if (isMovingHorizontallyWhileJumping) {
	            if (getCurrentDirection() == Direction.RIGHT) {
	                setX(getX() + jumpX);
	            } else if (getCurrentDirection() == Direction.LEFT) {
	                setX(getX() - jumpX);
	            }
	        }

	        // Passaggio da salto a caduta
	        if (getVelocityY() > 0 && getCurrentActionState() == ActionState.JUMPING) {
	            setCurrentActionState(ActionState.FALLING);
	        }

	        // Previsione dei piedi nella prossima posizione
	        Rectangle feet = new Rectangle(getX(), getY() + getHeight(), getWidth(), 1);

	        for (Collision beam : beams) {
	            if (beam.getBounds().intersects(feet)) {
	                // Atterraggio sulla trave
	                setY(beam.getBounds().y - getHeight()); // Allinea i piedi
	                setVelocityY(0);
	                setCurrentTerrain(Terrain.BEAM);
	                setCurrentActionState(ActionState.IDLE); // oppure WALKING se premi un tasto
	                setMovingHorizontallyWhileJumping(false);
	                break;
	            }
	        }
	        
	        if (getVelocityY() < 0) {
	        	Rectangle head = getHeadBounds();
	        	
	        	for (Collision beam : beams) {
	        		if (beam.getBounds().intersects(head)) {
	        			// ECCEZIONE: se Mario è su scala, lasciamolo passare
	                    if (getCurrentTerrain() == Terrain.LADDER && getCurrentActionState() == ActionState.CLIMBING) {
	                        continue;
	                    }

	                    // Colpito il soffitto: blocca salto e inizia caduta
	                    setVelocityY(0);
	                    setCurrentActionState(ActionState.FALLING);
	                    return;
	        		}
	        	}
	        }
	    }

	    // Se Mario è a piedi (es. WALKING/IDLE) controlla se c'è beam sotto
	    if (getCurrentActionState() == ActionState.WALKING || getCurrentActionState() == ActionState.IDLE) {
	        Rectangle feet = new Rectangle(getX(), getY() + getHeight(), getWidth(), 1);
	        boolean onBeam = false;

	        for (Collision beam : beams) {
	            if (beam.getBounds().intersects(feet)) {
	                onBeam = true;
	                break;
	            }
	        }

	        if (!onBeam) {
	            setCurrentActionState(ActionState.FALLING);
	            setCurrentTerrain(Terrain.AIR);
	        } else {
	            setCurrentTerrain(Terrain.BEAM);
	        }
	    }
	}


	public int getJumpStrength() {
		return jumpStrength;
	}

	public void setJumpStrength(int jumpStrength) {
		this.jumpStrength = jumpStrength;
	}

	public boolean isMovingHorizontallyWhileJumping() {
		return isMovingHorizontallyWhileJumping;
	}

	public void setMovingHorizontallyWhileJumping(boolean isMovingHorizontallyWhileJumping) {
		this.isMovingHorizontallyWhileJumping = isMovingHorizontallyWhileJumping;
	}
	
	public int getJumpX() {
		return jumpX;
	}

	public void setJumpX(int jumpX) {
		this.jumpX = jumpX;
	}

	public int getLadderY() {
		return ladderY;
	}

	public void setLadderY(int ladderY) {
		this.ladderY = ladderY;
	}
	
	public Rectangle getLadderBounds() {
		Rectangle ladderCheck = new Rectangle(
			    getX() + getWidth() / 4,  // 25% da sinistra
			    getY(),                   // dalla testa
			    getWidth() / 2,           // 50% larghezza (centrato)
			    getHeight()               // tutta l'altezza
			);
		return ladderCheck;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [jumpStrength = ").append(jumpStrength).append("\n, getCurrentAnimationFrames() = ")
				.append(Arrays.toString(getCurrentAnimationFrames())).append(" \n, getX()=").append(getX())
				.append("\n, getY()=").append(getY()).append("\n, getVelocityX()=").append(getVelocityX())
				.append("\n, getVelocityY()=").append(getVelocityY()).append("\n, getWidth()=").append(getWidth())
				.append("\n, getHeight()=").append(getHeight()).append("\n, getName()=").append(getName())
				.append("\n, getSpriteFrames()=").append(getSpriteFrames()).append("\n, getCurrentActionState()=")
				.append(getCurrentActionState()).append(", getCurrentDirection()=").append(getCurrentDirection())
				.append("\n, getCurrentTerrain()=").append(getCurrentTerrain()).append("\n, getCurrentFrameIndex()=")
				.append(getCurrentFrameIndex()).append("\n, getFrameCounter()=").append(getFrameCounter())
				.append("\n, getFrameDelay()=").append(getFrameDelay()).append("\n, getSpriteNumber()=")
				.append(getSpriteNumber()).append("\n, getClass()=").append(getClass()).append("\n, hashCode()=")
				.append(hashCode()).append("\n, toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
	
	public ArrayList<Ladder> getLadders() {
		return ladders;
	}

	public ActionState getPreviousActionState() {
		return previousActionState;
	}

	public void setPreviousActionState(ActionState previousActionState) {
		this.previousActionState = previousActionState;
	}
	
	
	
	
	
}
