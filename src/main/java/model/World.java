package model;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.CollisionManager;

public class World {
	private static final Logger log = LoggerFactory.getLogger(World.class);

	private TileMap map;
	private Player player;
	private ArrayList<Entity> entities;
	
	public World(TileMap map, Player player) {
		this.map = map;
		this.player = player;
		this.entities = new ArrayList<>();
	}
	
	public boolean isStandingOnBeam(Entity entity, ArrayList<Collision> beams) {
		Rectangle feet = entity.getFeetBounds();
		
		for (Collision beam : beams) {
			Rectangle beamBounds = beam.getBounds();
			
			// Controlla se i piedi toccano la trave e sono appena sopra
			if (feet.intersects(beamBounds)) {
				log.debug("Mario è su una trave!");
				return true;
			}
		}
		log.debug("Mario NON è su una trave!");
		return false;
	}
	
	public void update() {
		updatePhysics();
		updateEntities();
	}
	
	//TODO: DA GESTIRE TUTTE LE ENTITA'. Per ora gestisco solo Mario
	private void updatePhysics() {
		ArrayList<Collision> collisions = CollisionManager.loadSampleCollisions();
		player.updatePhysics(collisions);
	}
	
	private void updateEntities() {
		
	}
}
