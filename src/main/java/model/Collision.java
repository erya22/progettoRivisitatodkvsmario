package model;

import java.awt.Rectangle;

/**
 * Rappresenta un oggetto di collisione nel gioco, usato per definire
 * travi o ostacoli con cui possono interagire altri oggetti o entità.
 */
public class Collision {
    private int id;
    private int x, y, width, height;
    private boolean visible;

    /**
     * Costruttore.
     *
     * @param id      Identificatore unico dell'oggetto di collisione
     * @param x       Coordinata X dell'angolo superiore sinistro
     * @param y       Coordinata Y dell'angolo superiore sinistro
     * @param width   Larghezza del rettangolo di collisione
     * @param height  Altezza del rettangolo di collisione
     * @param visible Indica se l'oggetto è visibile a schermo
     */
    public Collision(int id, int x, int y, int width, int height, boolean visible) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
    }

    //----GETTERS AND SETTERS----
    
    /**
     * Restituisce un oggetto {@link Rectangle} che rappresenta l'area di collisione.
     * @return Rettangolo di collisione
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
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
}
