package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import model.TileMap;
import model.Layer;
import utils.Constants;
import utils.TileMapLoader;
import utils.TileUtils;

/**
 * Componente grafico che disegna la mappa di gioco. Carica la mappa, il tileset e disegna i layer tile-based.
 */
public class MapView extends JComponent {
	private BufferedImage[] tiles;
    private BufferedImage tileset;
    private int tileWidth;
    private int tileHeight;
    private int tileSize;
    private TileMap map;
    
    /**
     * Crea una nuova {@code MapView} caricando la mappa e il tileset.
     * Inizializza le tile pronte per essere disegnate.
     */
    public MapView() {
    	this.map = TileMapLoader.loadMap();
        this.tileset = TileMapLoader.loadTileset();
        this.tileWidth = map.getTilewidth();
        this.tileHeight = map.getTileheight();
        this.tileSize = Constants.TILE_SIZE;
        this.tiles = TileUtils.loadTiles(tileset, tileWidth, tileHeight, tileSize);
    }
    
    /**
     * Imposta una nuova dimensione per le tile e ricarica le immagini scalate.
     * @param newTileSize la nuova dimensione in pixel per le tile
     */
    public void setTileSize(int newTileSize) {
        if (newTileSize != this.tileSize) {
            this.tileSize = newTileSize;
            this.tiles = TileUtils.loadTiles(tileset, tileWidth, tileHeight, tileSize);
        }
    }
    
    /**
     * Esegue il rendering della mappa disegnando ogni layer tile-based.
     * @param g il contesto grafico su cui disegnare
     */
    public void render(Graphics2D g) {
    	
        for (Layer layer : map.getLayers()) {
            if (!"tilelayer".equals(layer.getType())) continue;

            int[] data = layer.getData();
            int width = map.getWidth();
            int height = map.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int tileId = data[y * width + x];
                    if (tileId > 0) {
                    	BufferedImage tileImage = tiles[tileId - 1]; // Tiled uses 1-based index
                    	g.drawImage(tileImage, x * tileSize, y * tileSize, null);
                    }
                }
            }
        }
    }
    
    /**
     * Metodo standard per disegnare la mappa.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render((Graphics2D) g);
    }
}
