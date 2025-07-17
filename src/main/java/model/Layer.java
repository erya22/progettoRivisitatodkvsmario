package model;

/**
 * Rappresenta un layer della mappa di gioco, plain object, serve solo per caricare il JSON.
 */
public class Layer {
    public String name;
    public String type;
    public int[] data; // flat array che rappresenta la mappa
    public int width;
    public int height;
    
    /**
     * Costruttore vuoto
     */
    public Layer() {
    	
    }
    
    //----GETTERS AND SETTERS----
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
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
    
    
}
