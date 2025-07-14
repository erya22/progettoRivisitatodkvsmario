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
		
		updateAllEntities(beams);
		updateAllItems(beams);
	}
	
	//TODO: UPDATE ANIMATION
	private void updateAllEntities(ArrayList<Collision> beams) {
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

		    if (item instanceof Barrel barrel) {
		        barrel.roll(beams, triggerZones);
		        barrel.updatePhysics(beams, triggerZones);

		        if (barrel.isCollidingWithMario(player)) {
		            iterator.remove(); // ✅ rimozione sicura
		        }
		    }
		}

	}
	
	public boolean checkBarrelCollision() {
		for (GameItem item : items) {
			if (item instanceof Barrel) {
				Barrel barrel = (Barrel) item;
				if (barrel.getBounds().intersects(player.getBounds())) {
					items.remove(item);
				    barrel.setBarrelState(BarrelState.HIT_PLAYER);
				     // TODO: o cambia stato mario takes damage
				    return true;
				}
			}
		}
		return false;
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
	
	
	
	
}
