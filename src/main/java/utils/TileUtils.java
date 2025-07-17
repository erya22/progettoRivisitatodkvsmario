package utils;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

/**
 * Classe di utilità dei tiles.
 */
public class TileUtils {
	
	/**
     * Estrae e ridimensiona i singoli tile da un'immagine tileset.
     * @param tileset l'immagine contenente tutti i tile
     * @param tileWidth larghezza originale di un tile nel tileset
     * @param tileHeight altezza originale di un tile nel tileset
     * @param scaledTileSize dimensione (larghezza e altezza) desiderata per i tile ridimensionati
     * @return array di BufferedImage contenente tutti i tile ridimensionati
     */
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
