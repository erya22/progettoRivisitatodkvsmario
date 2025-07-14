package utils;

import java.util.ArrayList;

import model.Direction;
import model.TriggerZone;

public class TriggerZoneManager {
	private static ArrayList<TriggerZone> triggerZones;
	
	public static ArrayList<TriggerZone> loadSampleTriggerZone() {
		triggerZones = new ArrayList<>();
		triggerZones.add(new TriggerZone(0, 8 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE * 2, Direction.RIGHT));
		triggerZones.add(new TriggerZone(24 * Constants.TILE_SIZE, 14 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE * 2, Direction.LEFT));
		triggerZones.add(new TriggerZone(0, 18 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE * 2, Direction.RIGHT));
		triggerZones.add(new TriggerZone(24 * Constants.TILE_SIZE, 22 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE * 2, Direction.LEFT));
		triggerZones.add(new TriggerZone(0, 26 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE * 2, Direction.RIGHT));
		triggerZones.add(new TriggerZone(24 * Constants.TILE_SIZE, 30 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE * 2, Direction.LEFT));
		
		return triggerZones;
	}

}
