package model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;
import utils.Sprite;

/**
 * Classe che rappresenta un barile nel gioco, oggetto che può rotolare su travi,
 * cadere e interagire con Mario.
 * Estende {@link GameItem}.
 */
public class Barrel extends GameItem {
	private static final Logger log = LoggerFactory.getLogger(GameItem.class);
	
	private BarrelState barrelState;
	private int velocityX;
	private int velocityY;
	
	private Direction previousDirection;
	private boolean jumpedOver = false;
	
	/**
     * Costruttore.
     *
     * @param x                 Coordinata X iniziale
     * @param y                 Coordinata Y iniziale
     * @param radius            Raggio (usato per larghezza e altezza)
     * @param sprites           Mappa delle direzioni e degli sprite corrispondenti
     * @param currentActionState Stato attuale (es. ROLLING, FALLING)
     * @param currentDirection  Direzione attuale del barile
     * @param currentTerrain    Tipo di terreno su cui si trova
     * @param currentFrameIndex Indice del frame corrente
     * @param frameCounter      Contatore dei frame
     * @param frameDelay        Ritardo tra i frame dell’animazione
     * @param spriteNumber      Numero di sprite per l’animazione
     * @param velocityX         Velocità orizzontale
     * @param velocityY         Velocità verticale
     * @param barrelState       Stato logico del barile (es. normale, distrutto)
     */
	public Barrel(int x, int y, int radius, HashMap<Direction, BufferedImage[]> sprites,
			ActionState currentActionState, Direction currentDirection, Terrain currentTerrain, int currentFrameIndex,
			int frameCounter, int frameDelay, int spriteNumber, int velocityX, int velocityY, BarrelState barrelState) {
		super(x, y, radius, radius, sprites, currentActionState, currentDirection, currentTerrain, currentFrameIndex,
				frameCounter, frameDelay, spriteNumber);
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.barrelState = barrelState;
		setSpriteFrames(loadSprites());
	}
	
	/**
     * Carica gli sprite ridimensionati per le varie direzioni.
     * @return Mappa delle direzioni con i rispettivi array di sprite.
     */
	private HashMap<Direction, BufferedImage[]> loadSprites() {
	    int targetWidth = Constants.TILE_SIZE;
	    int targetHeight = Constants.TILE_SIZE;

	    HashMap<Direction, BufferedImage[]> spriteFrames = new HashMap<>();

	    BufferedImage[] images = new BufferedImage[] {
	        Sprite.resize(Sprite.BARREL1.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL2.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL3.img(), targetWidth, targetHeight),
	        Sprite.resize(Sprite.BARREL4.img(), targetWidth, targetHeight)
	    };

	    BufferedImage[] revImages = new BufferedImage[] {
    		Sprite.resize(Sprite.BARREL4.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL3.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL2.img(), targetWidth, targetHeight),
    		Sprite.resize(Sprite.BARREL1.img(), targetWidth, targetHeight)
	    };

	    spriteFrames.put(Direction.RIGHT, images);
	    spriteFrames.put(Direction.LEFT, revImages);
	    spriteFrames.put(Direction.DOWN, images);

	    return spriteFrames;
	}

	/**
     * Gestisce il movimento del barile quando rotola su una trave.
     * @param beams Lista delle travi 
     * @param triggerZones Lista delle zone che forzano un cambio direzione
     */
	public void roll(ArrayList<Beam> beams, ArrayList<TriggerZone> triggerZones) {
		if (isFalling(beams)) {
			setCurrentActionState(ActionState.FALLING);
			setCurrentDirection(Direction.DOWN);
			applyGravity();
			return;
		} 
		
		setCurrentActionState(ActionState.ROLLING);
		if (getCurrentDirection() == Direction.RIGHT) {
			setX(getX() + getVelocityX());
		} else if (getCurrentDirection() == Direction.LEFT){
			setX(getX() - getVelocityX());
		} else {
			log.debug("direzione sbagliata: {}", getCurrentDirection());
			barrelDirection(triggerZones);
		}
		
	}
	
	/**
     * Imposta la direzione del barile in base alla trigger zone.
     * @param triggerZones Lista delle zone che forzano un cambio direzione
     */
	public void barrelDirection(ArrayList<TriggerZone> triggerZones) {
		for (TriggerZone tz : triggerZones) {
			if (tz.getBounds().intersects(getBounds())) {
				setCurrentDirection(tz.getForcedDirection());
				return;
			}
		}
		log.debug("non ho trovato la triggerZone: x{} y{} ", getX(), getY());
	}
	
	/**
	 * Metodo che verifica la caduta di un barile.
	 * @param beams Lista delle travi 
	 * @return True se il barile sta cadendo, false viceversa
	 */
	public boolean isFalling(ArrayList<Beam> beams) {
	
		for (Beam beam : beams) {
			if (beam.getBounds().intersects(getFeetBounds())) {
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * Metodo che controlla la collisione con una trave.
	 * @param beam Lista delle travi 
	 * @return True se collide
	 */
	public boolean isCollidingWithBeam(Beam beam) {
		return this.getBounds().intersects(beam.getBounds());
	}
	
	/**
	 * Metodo che controlla la collisione con il player.
	 * @param beam Lista delle travi 
	 * @return True se collide
	 */
	public boolean isCollidingWithMario(Player player) {
		return this.getBounds().intersects(player.getBounds());
	}
	
	/**
     * Aggiorna lo stato fisico del barile.
     * @param beams Lista delle travi
     * @param triggerZones Lista delle zone di cambio direzione
     */
	public void updatePhysics(ArrayList<Beam> beams, ArrayList<TriggerZone> triggerZones) {
	    boolean onBeam = false;

	    // Controlla se è sopra una beam
	    for (Beam b : beams) {
	    	if (b.getBounds().intersects(getFeetBounds())) {
//	    	    log.debug("🎯 COLLISIONE RILEVATA con beam a y={}", b.getY());
	    	    // Allinea il barile sopra la beam
	    	    setY(b.getY() - getHeight());
	    	    setVelocityY(0);
	    	    setCurrentActionState(ActionState.ROLLING);
	    	    setCurrentTerrain(Terrain.BEAM);
	    	    onBeam = true;
	    	    break;
	    	}

	    }

	    if (onBeam) {
	        // Barile sulla beam, passa a ROLLING
	        if (getCurrentActionState() != ActionState.ROLLING) {
	            log.debug("Barile atterra su BEAM → stato ROLLING");
	        }
	        setCurrentActionState(ActionState.ROLLING);
	        setCurrentTerrain(Terrain.BEAM);
	        setVelocityY(0); // blocca la caduta

	        roll(beams, triggerZones); // continua a rotolare
	    } else {
	        // Nessuna beam sotto, cade
	        if (getCurrentActionState() != ActionState.FALLING) {
	            log.debug("Barile inizia a cadere → stato FALLING");
	        }
	        setCurrentActionState(ActionState.FALLING);
	        setCurrentTerrain(Terrain.AIR);
	    }
	}

	/**
	 * Gestisce la gravità della caduta dei barili.
	 */
	public void applyGravity() {
//		log.debug("Gravità applicata");
	    setY(getY() + getVelocityY());
	    setVelocityY(getVelocityY() + 1);
	}

	/**
     * Restituisce una rappresentazione testuale dello stato del barile.
     */
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
	
	//----GETTERS AND SETTERS----
	
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
	    return new Rectangle(getX(), getY() + getHeight() - 2, getWidth(), 10); // aumentato da 4 a 8 pixel
	}

	public Direction getPreviousDirection() {
		return previousDirection;
	}

	public void setPreviousDirection(Direction previousDirection) {
		this.previousDirection = previousDirection;
	}

	public boolean isJumpedOver() {
		return jumpedOver;
	}

	public void setJumpedOver(boolean jumpedOver) {
		this.jumpedOver = jumpedOver;
	}

	//----UNIMPLEMENTED METHODS----
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
