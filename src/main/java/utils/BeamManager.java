package utils;
import java.util.ArrayList;

import model.Beam;

/**
 * La classe fornisce metodi per gestire le collisioni nel gioco. Permette il caricamento di un insieme predefinito di oggetti {@link Beam}
 * rappresentanti piattaforme o ostacoli su cui il giocatore può interagire.
 * 
 * Tutte le collisioni sono costruite usando coordinate basate sulla costante Constants.TILE_SIZE,
 * rendendo la loro posizione e dimensione facilmente adattabile alla risoluzione della mappa.
 */
public class BeamManager {
    private static ArrayList<Beam> collisions = new ArrayList<>();

    /**
     * Carica un insieme predefinito di oggetti {@link Beam} rappresentanti le piattaforme 
     * e gli ostacoli del livello di gioco.
     * 
     * @return una lista di oggetti {@code Collision} con coordinate e dimensioni predefinite
     */
    public static ArrayList<Beam> loadSampleCollisions() {
        collisions.add(new Beam(2, 0 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(4, 4 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(5, 8 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(6, 12 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(7, 16 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(8, 20 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(9, 24 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 12, Constants.TILE_SIZE * 4 , Constants.TILE_SIZE, true));
        collisions.add(new Beam(10, 20 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(11, 16 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE + 2, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(12, 12 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(13, 8 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(14, 4 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(15, 0 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(16, 4 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(17, 8 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-2, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(18, 12 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(19, 16 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-6, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(20, 20 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-8, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(21, 24 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(22, 0 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(23, 20 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(24, 16 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(25, 12 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(26, 8 * Constants.TILE_SIZE,  19 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(27, 4 * Constants.TILE_SIZE, 19 *Constants.TILE_SIZE - 8, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(28, 4 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(29, 8 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(30, 12 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(31, 16 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(33, 20 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(34, 24 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(35, 10 * Constants.TILE_SIZE, 7 * Constants.TILE_SIZE, Constants.TILE_SIZE*7, Constants.TILE_SIZE, true));
        collisions.add(new Beam(36, 0 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 12, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(37, 4 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(38, 8 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(39, 12 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Beam(40, 16 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE*8, Constants.TILE_SIZE, true));
        return collisions;
    }

}
