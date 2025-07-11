package utils;
import java.util.ArrayList;

import model.Collision;

public class CollisionManager {
    private static ArrayList<Collision> collisions = new ArrayList<>();

    public static ArrayList<Collision> loadSampleCollisions() {
        collisions.add(new Collision(2, 0 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(4, 4 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(5, 8 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(6, 12 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(7, 16 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(8, 20 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(9, 24 * Constants.TILE_SIZE, 31 * Constants.TILE_SIZE - 12, Constants.TILE_SIZE * 4 , Constants.TILE_SIZE, true));
        collisions.add(new Collision(10, 20 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(11, 16 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE + 2, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(12, 12 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(13, 8 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(14, 4 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(15, 0 * Constants.TILE_SIZE, 27 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(16, 4 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(17, 8 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-2, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(18, 12 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(19, 16 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-6, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(20, 20 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-8, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(21, 24 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE-10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(22, 0 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(23, 20 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(24, 16 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(25, 12 * Constants.TILE_SIZE, 19 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(26, 8 * Constants.TILE_SIZE,  19 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(27, 4 * Constants.TILE_SIZE, 19 *Constants.TILE_SIZE - 8, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(28, 4 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(29, 8 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(30, 12 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(31, 16 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 6, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(33, 20 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(34, 24 * Constants.TILE_SIZE, 15 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE * 4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(35, 10 * Constants.TILE_SIZE, 7 * Constants.TILE_SIZE, Constants.TILE_SIZE*7, Constants.TILE_SIZE, true));
        collisions.add(new Collision(36, 0 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 12, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(37, 4 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 10, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(38, 8 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 8, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(39, 12 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 4, Constants.TILE_SIZE*4, Constants.TILE_SIZE, true));
        collisions.add(new Collision(40, 16 * Constants.TILE_SIZE, 11 * Constants.TILE_SIZE - 2, Constants.TILE_SIZE*8, Constants.TILE_SIZE, true));
        return collisions;
    }

}
