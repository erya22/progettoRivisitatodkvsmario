package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.CollisionManager;
import utils.LadderManager;
import utils.TriggerZoneManager;

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
	
	public boolean isStandingOnBeam(Entity entity, ArrayList<Collision> beams) {
		Rectangle feet = entity.getFeetBounds();
		
		for (Collision beam : beams) {
			Rectangle beamBounds = beam.getBounds();
			
			// Controlla se i piedi toccano la trave e sono appena sopra
			if (feet.intersects(beamBounds)) {
				log.debug("{} è su una trave!", entity.getName());
				return true;
			}
		}
		log.debug("{} NON è su una trave!", entity.getName());
		return false;
	}
	
	public void update(ArrayList<Collision> beams) {
//		updatePhysics();
		if (isPaused) return;
		updateAllEntities(beams);
		updateAllItems(beams);
	}
	
	//TODO: UPDATE ANIMATION
	private void updateAllEntities(ArrayList<Collision> beams) {
		if (peach.isCollidingWithMario(player)) {
			log.debug("Mario vincitore!");
			player.setPlayerState(PlayerState.WINNER);
			player.setScore(1000); // Provvisorio, punteggio aggiuntivo se salvi peach
			peach.setCurrentActionState(ActionState.VICTORY);
			player.getListener().sideMenuRefresh();
			player.getListener().stopGameLoop(); // fermo il gioco
			
			//TODO: monta animazioni
			return;
		}
		for (Entity entity : entities) {

			entity.update();
			entity.updatePhysics(beams);
		}
	}
	
	//TODO: UPDATE ANIMATION
	private void updateAllItems(ArrayList<Collision> beams) {
		Iterator<GameItem> iterator = items.iterator();
		while (iterator.hasNext()) {
		    GameItem item = iterator.next();
		    item.update();

		    if(item instanceof Barrel) {
				Barrel barrel = (Barrel) item;
			    checkBarrelCollision(barrel, iterator);
			    checkJumpOverBarrels(barrel);
                player.getListener().sideMenuRefresh(); // Per eventuale modifica score
		    }
		}

	}
	
	public void checkBarrelCollision(Barrel barrel, Iterator<GameItem> iterator) {
        barrel.roll(beams, triggerZones);
        barrel.updatePhysics(beams, triggerZones);

        if (barrel.isCollidingWithMario(player)) {
        	boolean hitFromAbove = player.getY() + player.getHeight() <= barrel.getY() + 10;
        	
		    barrel.setBarrelState(BarrelState.HIT_PLAYER);
            iterator.remove();
            
            player.hitByBarrell();
            player.checkIfAlive();
            
            if (hitFromAbove) {
                // Mario è atterrato sopra il barile, non conto i 100 punti di score
                player.addScore(-100);
            }
	    }
	}
	
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
            								 lo stesso barile non avrà score aggiuntivo, ma è abbastanza insolto che ciò avvenga*/
        }
	}

	
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
