package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import model.TileMap;
import utils.Constants;
import utils.TileMapLoader;
import utils.TileUtils;

public class MapView extends JComponent {
	private BufferedImage[] tiles;
    private BufferedImage tileset;
    private int tileWidth;
    private int tileHeight;
    private int tileSize;
    private TileMap map;
    
    public MapView() {
    	this.map = TileMapLoader.loadMap();
        this.tileset = TileMapLoader.loadTileset();
        this.tileWidth = map.getTilewidth();
        this.tileHeight = map.getTileheight();
        this.tileSize = Constants.TILE_SIZE;
        this.tiles = TileUtils.loadTiles(tileset, tileWidth, tileHeight, tileSize);
    }
    
    public void setTileSize(int newTileSize) {
        if (newTileSize != this.tileSize) {
            this.tileSize = newTileSize;
            this.tiles = TileUtils.loadTiles(tileset, tileWidth, tileHeight, tileSize);
        }
    }
    
    public void render(Graphics2D g) {
    	
        for (model.Layer layer : map.getLayers()) {
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render((Graphics2D) g);
    }
}
