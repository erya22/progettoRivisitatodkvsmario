package utils;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

public class TileUtils {
    public static BufferedImage[] loadTiles(BufferedImage tileset, int tileWidth, int tileHeight, int scaledTileSize) {
        int tilePerRow = tileset.getWidth() / tileWidth;
        int tilePerCol = tileset.getHeight() / tileHeight;
        BufferedImage[] tiles = new BufferedImage[tilePerRow * tilePerCol];

        for (int y = 0; y < tilePerCol; y++) {
            for (int x = 0; x < tilePerRow; x++) {
                int index = y * tilePerRow + x;
                BufferedImage tile = tileset.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                tiles[index] = Scalr.resize(tile, scaledTileSize);
            }
        }
        return tiles;
    }
}
