package model;
import java.util.List;

public class TileMap {
    private int width;
    private int height;
    private int tilewidth;
    private int tileheight;
    private boolean infinite;
    private int compressionlevel;
    private List<Layer> layers;
    
    public TileMap() {
    	
    }
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getTilewidth() {
		return tilewidth;
	}
	public void setTilewidth(int tilewidth) {
		this.tilewidth = tilewidth;
	}
	public int getTileheight() {
		return tileheight;
	}
	public void setTileheight(int tileheight) {
		this.tileheight = tileheight;
	}
	public boolean isInfinite() {
		return infinite;
	}
	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
	public int getCompressionlevel() {
		return compressionlevel;
	}
	public void setCompressionlevel(int compressionlevel) {
		this.compressionlevel = compressionlevel;
	}
	public List<Layer> getLayers() {
		return layers;
	}
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}
    
    
}
