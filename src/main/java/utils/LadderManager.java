package utils;

import java.util.ArrayList;

import model.Ladder;

/**
 * Si occupa della gestione delle scale presenti nel gioco.
 */
public class LadderManager {
	private static ArrayList<Ladder> ladders = new ArrayList<>();
	
	/**
     * Carica un set di scale predefinite e le aggiunge alla lista.
     * @return un ArrayList contenente le scale del livello.
     */
	public static ArrayList<Ladder> loadSampleLadders() {
		ladders.add(new Ladder(2, 12 * Constants.TILE_SIZE, 30 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE - 4, true));
		ladders.add(new Ladder(2, 14 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE, 4 * Constants.TILE_SIZE + 2, true));
		ladders.add(new Ladder(2, 12 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 2 + 2, true));
		ladders.add(new Ladder(2, 23 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 8, true));
		ladders.add(new Ladder(2, 14 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE - 1, Constants.TILE_SIZE, Constants.TILE_SIZE - 2, true));
		ladders.add(new Ladder(2, 4 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE - 1, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 8, true));
		ladders.add(new Ladder(2, 10 * Constants.TILE_SIZE, 22 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, true));
		ladders.add(new Ladder(2, 16 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 3, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 2, true));
		ladders.add(new Ladder(2, 23 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 8, true));
		ladders.add(new Ladder(2, 22 * Constants.TILE_SIZE, 18 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, true));
		ladders.add(new Ladder(2, 22 * Constants.TILE_SIZE, 16 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE, Constants.TILE_SIZE / 4, true));
		ladders.add(new Ladder(2, 10 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE, Constants.TILE_SIZE / 4, true));
		ladders.add(new Ladder(2, 12 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 2, true));
		ladders.add(new Ladder(2, 4 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 8, true));
		ladders.add(new Ladder(2, 10 * Constants.TILE_SIZE, 14 * Constants.TILE_SIZE + 10, Constants.TILE_SIZE, Constants.TILE_SIZE - 10, true));
		ladders.add(new Ladder(2, 10 * Constants.TILE_SIZE, 12 * Constants.TILE_SIZE - 9, Constants.TILE_SIZE, Constants.TILE_SIZE + 8, true));
		ladders.add(new Ladder(2, 23 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 4 - 8, true));
		ladders.add(new Ladder(2, 16 * Constants.TILE_SIZE, 7 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 4, true));
		ladders.add(new Ladder(2, 7 * Constants.TILE_SIZE,  4 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 7 - 7, true));
		ladders.add(new Ladder(2, 9 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE * 7 - 7, true));
		
		return ladders;
	}
	
}
