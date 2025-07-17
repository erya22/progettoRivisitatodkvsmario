package model;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import menu.GameResultManager;
import utils.CollisionManager;
import utils.Constants;
import utils.LadderManager;
import utils.TriggerZoneManager;

/**
 * Rappresenta lo stato globale del mondo di gioco, contenente la mappa, i personaggi principali, entità secondarie, oggetti interattivi
 * e logica di aggiornamento di gioco.
 *
 * Gestisce il ciclo di vita di entità e oggetti, le collisioni, le interazioni con scale, travi e trigger zone, 
 * e aggiorna le condizioni di vittoria/sconfitta.
 */
public class World {
	private static final Logger log = LoggerFactory.getLogger(World.class);

	private boolean isPaused = false;
	
	private TileMap map;
	private Player player;
	private DonkeyKong dk;
	private Peach peach;
	private ArrayList<Entity> entities;
	private ArrayList<GameItem> items;
	private ArrayList<Collision> beams = CollisionManager.loadSampleCollisions();
	private ArrayList<Ladder> ladders = LadderManager.loadSampleLadders();
	private ArrayList<TriggerZone> triggerZones = TriggerZoneManager.loadSampleTriggerZone();
	
	/**
     * Costruttore.
     *
     * @param map    la mappa del gioco
     * @param player il giocatore controllato dall'utente
     * @param dk     Donkey Kong, il nemico principale
     * @param peach  Peach, l'obiettivo da salvare
     */
	public World(TileMap map, Player player, DonkeyKong dk, Peach peach) {
		this.map = map;
		this.player = player;
		this.dk = dk;
		this.peach = peach;
		this.entities = new ArrayList<>();
		this.items = new ArrayList<>();
		entities.add(player);
		entities.add(peach);
		entities.add(dk);
	}
	
	/**
     * Verifica se l'entità è attualmente sopra una trave.
     *
     * @param entity l'entità da controllare
     * @param beams  lista delle travi nel mondo
     * @return true se l'entità è sopra una trave, false altrimenti 
     */
	public boolean isStandingOnBeam(Entity entity, ArrayList<Collision> beams) {
		Rectangle feet = entity.getFeetBounds();
		
		for (Collision beam : beams) {
			Rectangle beamBounds = beam.getBounds();
			
			// Controlla se i piedi toccano la trave e sono appena sopra
			if (feet.intersects(beamBounds)) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Aggiorna lo stato del mondo, delle entità e degli oggetti, se non in pausa.
     * @param beams lista delle travi
     */
	public void update(ArrayList<Collision> beams) {
		if (isPaused) return;
		floatingScores.removeIf(FloatingScore::isExpired);

		updateAllEntities(beams);
		updateAllItems(beams);
	}
	
	/**
     * Aggiorna tutte le entità del gioco, gestisce l'eventuale vittoria del giocatore
     * e aggiorna fisica e logica di movimento.
     * @param beams lista delle travi
     */
	private void updateAllEntities(ArrayList<Collision> beams) {
		if (peach.isCollidingWithMario(player)) {
			log.debug("Mario vincitore!");
			player.setPlayerState(PlayerState.SAVED_PEACH);
			player.setScore(player.getScore() + 1000); // Punteggio aggiuntivo se salvi peach
			peach.setCurrentActionState(ActionState.VICTORY);
			player.getListener().sideMenuRefresh();
			GameResultManager.endGame(player, player.getListener().getGamePanel());
			
			return;
		}
		for (Entity entity : entities) {

			entity.update();
			entity.updatePhysics(beams);
		}
	}
	
	/**
     * Aggiorna tutti gli oggetti di gioco, come barili, rimuovendo quelli scaduti
     * e gestendo le collisioni.
     * @param beams lista delle travi
     */
	private void updateAllItems(ArrayList<Collision> beams) {
		boolean isBarrelColliding = false;
		ArrayList<Barrel> expiredBarrels = new ArrayList<Barrel>();
		
		for (GameItem item : items) {
		    item.update();

		    if(item instanceof Barrel) {
		    	
				Barrel barrel = (Barrel) item;
				if (checkBarrelCollision(barrel)) {
					isBarrelColliding = true;
					break;
				}
			    checkJumpOverBarrels(barrel);
                player.getListener().sideMenuRefresh(); // Per eventuale modifica score
                
                if ((barrel.getX() < 0) || (barrel.getY() >= Constants.MAP_HEIGHT + Constants.TILE_SIZE)) {
                	expiredBarrels.add(barrel);
                }
		    }
		}
		
		if (isBarrelColliding) {
			ArrayList<GameItem> newItems = new ArrayList<GameItem>();
			for (GameItem i : items) {
				if (i instanceof Barrel) {
					newItems.add(i);
				}
			}
			items.removeAll(newItems);
		} else if (!expiredBarrels.isEmpty()){
			log.debug("barile rimosso");
			items.removeAll(expiredBarrels);
		}
	}
	
	/**
     * Controlla se un barile ha colpito il player. Se il colpo arriva dall'alto, il punteggio non viene contato.
     * @param barrel il barile da verificare
     * @return true se c'è stata una collisione col player, false altrimenti 
     */
	public boolean checkBarrelCollision(Barrel barrel) {
        barrel.roll(beams, triggerZones);
        barrel.updatePhysics(beams, triggerZones);

        if (barrel.isCollidingWithMario(player)) {
        	boolean hitFromAbove = player.getY() + player.getHeight() <= barrel.getY() + 10;
        	            
            player.hitByBarrell();
            if (hitFromAbove) {
                // Mario è atterrato sopra il barile, non conto i 100 punti di score
            	if(player.getScore() > 0)
            			player.addScore(-100);
            }
            player.checkIfAlive();
            return true;
	    }
        return false;
	}
	
	private ArrayList<FloatingScore> floatingScores = new ArrayList<>();

	public void addFloatingScore(int x, int y, int score) {
	    floatingScores.add(new FloatingScore(x, y, score));
	}

	public ArrayList<FloatingScore> getFloatingScores() {
	    return floatingScores;
	}

	
	/**
     * Verifica se il player ha saltato con successo sopra un barile, assegnandogli uno score di 100.
     * @param barrel il barile da verificare
     */
	private void checkJumpOverBarrels(Barrel barrel) {
		// Zona virtuale sopra il barile
        Rectangle barrelTopZone = new Rectangle(
            barrel.getX(),
            barrel.getY() - 50,  // 50 pixel sopra
            barrel.getWidth(),
            20                   // altezza 20 pixel
        );
        
        if (player.getBounds().intersects(barrelTopZone) && player.isInAir() && !barrel.isJumpedOver()) {
            player.addScore(100);
            barrel.setJumpedOver(true); /*NOTA: non viene piu resettata a false per ogni barile, quindi se il player salta piu volte  
            								 lo stesso barile non avrà score aggiuntivo, ma è abbastanza insolito che ciò avvenga*/
            addFloatingScore(player.getX() + 10, barrel.getY() - 30, 100);
        }
	}

	//----GETTERS AND SETTERS----
	
	public void addItem(GameItem item) {
		items.add(item);
	}
	public void removeItem(GameItem item) {
		items.remove(item);
	}
	public ArrayList<GameItem> getItems() {
		return items;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public TileMap getMap() {
		return map;
	}

	public void setMap(TileMap map) {
		this.map = map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public DonkeyKong getDk() {
		return dk;
	}

	public void setDk(DonkeyKong dk) {
		this.dk = dk;
	}

	public Peach getPeach() {
		return peach;
	}

	public void setPeach(Peach peach) {
		this.peach = peach;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public void setItems(ArrayList<GameItem> items) {
		this.items = items;
	}

	public ArrayList<Collision> getBeams() {
		return beams;
	}

	public ArrayList<Ladder> getLadders() {
		return ladders;
	}
	
	public boolean isPaused() { return isPaused; }
	public void setPaused(boolean p) { isPaused = p; }
	
	
	
	
}
