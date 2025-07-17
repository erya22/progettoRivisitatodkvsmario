package model;

import java.awt.Rectangle;

/**
 * Rappresenta una scala nel gioco, plain object, serve solo per caricare il JSON.
 */
public class Ladder {
	private int id;
    private int x, y, width, height;
    private boolean visible;

    /**
     * Costruttore.
     * 
     * @param id Identificativo univoco della scala
     * @param x Posizione orizzontale della scala
     * @param y Posizione verticale della scala
     * @param width Larghezza della scala
     * @param height Altezza della scala
     * @param visible Stato di visibilità iniziale
     */
    public Ladder(int id, int x, int y, int width, int height, boolean visible) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    //----GETTERS AND SETTERS----
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
