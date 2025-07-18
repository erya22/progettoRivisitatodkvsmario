package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.SocketException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import audio.AudioManager;
import defaultmain.ClientManager;
import dkserver.PlayerStatus;
import menu.GameResultManager;
import utils.BeamManager;
import utils.Constants;
import utils.LadderManager;
import utils.Sprite;

/**
 * La classe Player rappresenta il giocatore (Mario), estende la classe {@link Entity} e gestisce le interazioni del giocatore con travi,
 * scale, barili e animazioni.
 * <p>
 * Questa classe include logiche per movimento, salto, arrampicata, collisioni, stato di vita,
 * punteggio e animazioni.
 * </p>
 */
public class Player extends Entity {
	private static final Logger log = LoggerFactory.getLogger(Player.class);
	
	private PlayerStatus ps = ClientManager.instance().playerStatus();
	
	private PlayerState state;
	private ArrayList<Beam> beams = BeamManager.loadSampleCollisions();
	private ArrayList<Ladder> ladders = LadderManager.loadSampleLadders();
	
	//BOUNDS PER LE SCALE
	private Rectangle ladderBounds;
	private ActionState previousActionState;
	
	//JUMP SETTINGS
	private int jumpStrength;
	private boolean isMovingHorizontallyWhileJumping = false;
	private int jumpX = 4;
	private int ladderY = 4;
	
	//GESTIONE VITA/SCORE PLAYER
	private int playerLives = 3;
	private PlayerListener listener;
	private int score = 0;
	
	private final int xStart;
	private final int yStart;
	
	private boolean isHitAnimationInitialized = false;
	private long animationTime = 0;
	private boolean isRecoveringFromHit = false;
	private long hitStartTime;
	private final long hitDuration = 500; // durata animazione in ms

	
	/**
     * Costruttore.
     *
     * @param x                posizione iniziale X
     * @param y                posizione iniziale Y
     * @param velocityX        velocità orizzontale
     * @param velocityY        velocità verticale
     * @param width            larghezza del player
     * @param height           altezza del player
     * @param spriteFrames     mappa di sprite per stato e direzione
     * @param name             nome del giocatore
     * @param currentFrameIndex indice frame corrente
     * @param frameCounter     contatore frame per l'animazione
     * @param frameDelay       ritardo tra frame
     * @param spriteNumber     numero di sprite totali
     * @param jumpStrength     forza del salto
     * @param ps               stato iniziale del giocatore
     */
	public Player(int x, int y, int velocityX, int velocityY, int width, int height,
			HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames, String name, int currentFrameIndex,
			int frameCounter, int frameDelay, int spriteNumber, int jumpStrength, PlayerState ps) {
		super(x, y, velocityX, velocityY, width, height, spriteFrames, name, currentFrameIndex, frameCounter, frameDelay, spriteNumber);
		this.jumpStrength = jumpStrength;
		this.state = ps;
		this.xStart = x;
		this.yStart = y;
		setSpriteFrames(loadPlayerSprites());
	}
	
	/**
     * Fa camminare il giocatore verso una direzione su una trave.
     * Supporta il salire leggermente per adeguarsi all'altezza delle travi.
     * @param direction direzione in cui camminare (LEFT o RIGHT)
     */
	public void walk(Direction direction) {
		if (getCurrentTerrain() == Terrain.BEAM) {
			if (getCurrentDirection() != direction) {
				setCurrentDirection(direction);
			}
			
			if (getCurrentActionState() != ActionState.WALKING)  {
				setCurrentActionState(ActionState.WALKING);
			}
			
			int nextX = getX() + (direction == Direction.RIGHT ? getVelocityX() : -getVelocityX());
	        int maxStepUp = 8; // massimo quanto Mario può "salire" automaticamente

	        // Crea rettangolo piedi davanti a Mario nella prossima posizione
	        for (int dy = 0; dy <= maxStepUp; dy++) {
	            Rectangle testFeet = new Rectangle(nextX, getY() + dy + getHeight(), getWidth(), 1);

	            for (Beam beam : beams) {
	                if (beam.getBounds().intersects(testFeet)) {
//	                	log.debug("Trave trovata!");
	                    // Trave trovata leggermente più in alto: sali
	                    setX(nextX);
	                    setY(beam.getBounds().y - getHeight()); // sali di poco
//	                    log.debug("Mario cammina verso {} con salita di {}px", direction, dy);
	                    return;
	                }
	            }
	        }
	        // Nessuna trave rilevata: movimento normale orizzontale
	        setX(nextX);
//			log.info("Mario cammina verso: {} a x({}) y({})", getCurrentDirection(), getX(), getY());
		}
		
		
	}
	
	/**
     * Permette al giocatore di arrampicarsi su o giù da una scala se presente.
     * @param direction direzione dell'arrampicata (UP, DOWN, LEFT, RIGHT)
     */
	public void climb(Direction direction) {
//	    log.debug("TI STAI ARRAMPICANDO VERSO: {}", direction);
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
	            Rectangle feetAfterStep = new Rectangle(getX(), getY() + getLadderY() + getHeight(), getWidth(), 1);
	            boolean beamBelow = false;

	            for (Beam beam : beams) {
	                if (beam.getBounds().intersects(feetAfterStep)) {
	                    beamBelow = true;
	                    break;
	                }
	            }

	            if (beamBelow) {
//	                log.debug("Mario non può scendere: c'è una trave sotto la scala");
	                setCurrentTerrain(Terrain.BEAM);
	                return;
	            }
	            setY(getY() + getLadderY());
	        } else if (direction == Direction.RIGHT) {
	        	setX(getX() + getVelocityX());
	        } else if (direction == Direction.LEFT) {
	        	setX(getX() - getVelocityX());
	        }
	    } else {
	        // Se non stava già scalando, può solo salire
	        if (direction == Direction.UP) {
	            setY(getY() - getLadderY());
	        }
	    }
	}
	
	/**
     * Fa saltare il giocatore, impostando la velocità verticale negativa.
     * @param movingHorizontally indica se il salto include movimento orizzontale
     */
	public void jump(boolean movingHorizontally) {
		if (getCurrentTerrain() == Terrain.BEAM) {
			this.setVelocityY(-getJumpStrength());
			this.setCurrentActionState(ActionState.JUMPING);
			AudioManager.playJumpSound();
			setMovingHorizontallyWhileJumping(movingHorizontally);
		}
	}
	
	/**
     * Verifica se il giocatore è attualmente in aria.
     * @return true se il giocatore è in aria, false altrimenti
     */
	public boolean isInAir() {
	    return getCurrentTerrain() == Terrain.AIR;
	}
	
	/**
	 * Collisione con barili, in caso toglie una vita al player.
	 */
	public void hitByBarrell() {
		playerLives--;
		ClientManager.instance().playerStatus().setVite(playerLives);
		listener.sideMenuRefresh();
		animateHitInit();
		setPlayerState(PlayerState.HIT_BY_BARREL);
		AudioManager.playOneShotMusic("/audio/death.wav");
	}
	
	/**
     * Inizializza l'animazione dopo essere stato colpito.
     */
	public void animateHitInit() {
		setCurrentActionState(ActionState.HIT);
		isRecoveringFromHit = true;
		hitStartTime = System.currentTimeMillis();

		if (getCurrentDirection() != Direction.LEFT) {
			setCurrentDirection(Direction.RIGHT);			
		}
		animationTime = System.currentTimeMillis() + 3000;
		setFrameCounter(0);
		setSpriteNumber(getCurrentAnimationFrames().length);
		setCurrentFrameIndex(0);
	}

	
	/**
     * Aggiorna i frame dell’animazione di colpo finché non termina.
     * @return true se l’animazione è ancora in corso, false se è finita
     */
	public boolean animateHit()	{
		long now = System.currentTimeMillis();
		if (now < animationTime) {
			updateAnimation();
			return true;
		}
		if (playerLives == 0) {
			setPlayerState(PlayerState.DEAD);
			checkIfAlive();
		} else {
			restart();
		}
		return false;
	}
	

	/**
	 * Ferma il gioco e mostra jdialog se player ha zero vite rimanenti.
	 */
	public void checkIfAlive() {
		if(getPlayerState() == PlayerState.DEAD) {
			ClientManager.instance().playerStatus().setAlive(false);
			GameResultManager.endGame(this, listener.getGamePanel());
		}
}
	
	/**
     * Reimposta il giocatore alla posizione iniziale e stato iniziale.
     */
	public void restart() {
		setX(this.xStart);
		setY(this.yStart);
		setCurrentDirection(Direction.RIGHT);
		setPlayerState(PlayerState.ALIVE);
		setCurrentActionState(ActionState.IDLE);
		setCurrentFrameIndex(0);
		setFrameCounter(0);
	}
	
	/**
     * Aggiunge punteggio al giocatore.
     * @param scoreAdded punteggio da aggiungere
     */
	public void addScore(int scoreAdded) {
		score += scoreAdded;
		ClientManager.instance().update(score, playerLives, getPlayerState() != PlayerState.DEAD);
	}

	/**
     * Carica tutti gli sprite associati ai vari stati e direzioni del giocatore.
     * @return una mappa degli sprite indicizzata per stato e direzione
     */
	private HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> loadPlayerSprites() {
		HashMap<SimpleEntry<ActionState, Direction>, BufferedImage[]> spriteFrames = new HashMap<AbstractMap.SimpleEntry<ActionState,Direction>, BufferedImage[]>();
		
		int targetWidth = Constants.TILE_SIZE;
		int targetHeight = Constants.TILE_SIZE;
		
		//IDLE
		BufferedImage[] idleR = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_WALK_R.img(), targetWidth, targetHeight)
		};
		BufferedImage[] idleL = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_WALK_L.img(), targetWidth, targetHeight)	
		};
		
		spriteFrames.put(new SimpleEntry<ActionState, Direction>(ActionState.IDLE, Direction.RIGHT), idleR);
		spriteFrames.put(new SimpleEntry<ActionState, Direction>(ActionState.IDLE, Direction.LEFT), idleL);

		// UP/DOWN
		BufferedImage[] up = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_IDLE_CLIMB.img(), targetWidth, targetHeight)
		};
		
		spriteFrames.put(new SimpleEntry<ActionState, Direction>(ActionState.CLIMBING, Direction.UP), up);
		spriteFrames.put(new SimpleEntry<ActionState, Direction>(ActionState.CLIMBING, Direction.DOWN), up);
		spriteFrames.put(new SimpleEntry<ActionState, Direction>(ActionState.CLIMBING, Direction.LEFT), up);
		spriteFrames.put(new SimpleEntry<ActionState, Direction>(ActionState.CLIMBING, Direction.RIGHT), up);
		
		// RIGHT
		BufferedImage[] right = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_WALK_R.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_WALK_R1.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_WALK_R2.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_WALK_R3.img(), targetWidth, targetHeight)
		};
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.WALKING, Direction.RIGHT), right);
		
		// LEFT
		BufferedImage[] left = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_WALK_L.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_WALK_L1.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_WALK_JUMP_L_L2.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_WALK_L3.img(), targetWidth, targetHeight)
		};
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.WALKING, Direction.LEFT), left);
		
		// JUMP AND FALL ANIMATIONS
		BufferedImage[] jumpR = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_JUMP_R.img(), targetWidth, targetHeight)
		};
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.JUMPING, Direction.RIGHT), jumpR);
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.FALLING, Direction.RIGHT), jumpR);
		
		BufferedImage[] jumpL = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_WALK_JUMP_L_L2.img(), targetWidth, targetHeight)
		};
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.JUMPING, Direction.LEFT), jumpL);
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.FALLING, Direction.LEFT), jumpL);
		
		BufferedImage[] hitFrames = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_HIT.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT1.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT2.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT3.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT4.img(), targetWidth, targetHeight)
		};
		
		BufferedImage[] revHitFrames = new BufferedImage[] {
				Sprite.resize(Sprite.MARIO_HIT4.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT3.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT2.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT1.img(), targetWidth, targetHeight),
				Sprite.resize(Sprite.MARIO_HIT.img(), targetWidth, targetHeight)
		};
		
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.HIT, Direction.RIGHT), hitFrames);
		spriteFrames.put(new AbstractMap.SimpleEntry<ActionState, Direction>(ActionState.HIT, Direction.LEFT), revHitFrames);
		
		for (ActionState state : ActionState.values()) {
		    for (Direction dir : Direction.values()) {
		        spriteFrames.putIfAbsent(
		            new SimpleEntry<>(state, dir),
		            idleR // fallback
		        );
		    }
		}
		return spriteFrames;
	}

	/**
	 * Salva lo stato attuale prima di aggiornare
	 */
	@Override
	public void update() {
//	    previousActionState = getCurrentActionState();
	}
	
	/**
     * Applica la fisica al giocatore come gravità e limiti mappa.
     * @param beams le travi contro cui gestire le collisioni
     */
	@Override
	public void updatePhysics(ArrayList<Beam> beams) {
		//LIMITI ORIZZONTALI MAPPA
		if (getX() < 0){
			setX(0);
		} else if (getX() + getWidth() > Constants.MAP_WIDTH){
			setX(Constants.MAP_WIDTH - getWidth());
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

	        for (Beam beam : beams) {
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
	        	
	        	for (Beam beam : beams) {
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

	        for (Beam beam : beams) {
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
	
	/**
     * Restituisce una rappresentazione testuale dello stato del player.
     */
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
	
	//----GETTERS AND SETTERS----
	
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

	public ArrayList<Ladder> getLadders() {
		return ladders;
	}

	public ActionState getPreviousActionState() {
		return previousActionState;
	}

	public void setPreviousActionState(ActionState previousActionState) {
		this.previousActionState = previousActionState;
	}

	public PlayerState getPlayerState() {
		return state;
	}

	public void setPlayerState(PlayerState state) {
		this.state = state;
		ClientManager.instance().update(score, playerLives, getPlayerState() != PlayerState.DEAD);

		try {
			ArrayList<PlayerStatus> ssss = ClientManager.instance().getClient().read(ps);
		} catch(SocketException se ) {
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayerLives() {
		return playerLives;
	}

	public void setPlayerLives(int playerLives) {
	    this.playerLives = playerLives;
	    ClientManager.instance().update(score, playerLives, getPlayerState() != PlayerState.DEAD);
	    try {
	        ClientManager.instance().getClient().read(ps);
	    } catch (SocketException se) {
	    } catch (ClassNotFoundException | IOException e) {
	        e.printStackTrace();
	    }
	}


	public PlayerListener getListener() {
		return listener;
	}
	
    public void setListener(PlayerListener listener) {
        this.listener = listener;
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		ClientManager.instance().update(score, playerLives, getPlayerState() != PlayerState.DEAD);
		try {
			ClientManager.instance().getClient().read(ps);
		} catch(SocketException se ) {
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isHitAnimationInitialized() {
		return isHitAnimationInitialized;
	}

	public void setHitAnimationInitialized(boolean isHitAnimationInitialized) {
		this.isHitAnimationInitialized = isHitAnimationInitialized;
	}
	
}
