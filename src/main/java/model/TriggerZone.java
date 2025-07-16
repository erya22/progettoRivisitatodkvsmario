package model;

import java.awt.Rectangle;

/**
 * Rappresenta una zona della mappa che forza il personaggio a cambiare direzione
 * quando vi entra. Viene utilizzata per guidare automaticamente il movimento del player in certi punti.
 */
public class TriggerZone {
	private Rectangle bounds; // rettangolo della zona
    private Direction forcedDirection; // direzione da impostare

    /**
     * Costruttore.
     *
     * @param x               coordinata x dell'angolo superiore sinistro della zona
     * @param y               coordinata y dell'angolo superiore sinistro della zona
     * @param width           larghezza della zona
     * @param height          altezza della zona
     * @param forcedDirection direzione da imporre quando il player entra nella zona
     */
    public TriggerZone(int x, int y, int width, int height, Direction forcedDirection) {
        this.bounds = new Rectangle(x, y, width, height);
        this.forcedDirection = forcedDirection;
    }

    /**
     * Restituisce una rappresentazione testuale della trigger zone.
     */
    @Override
    public String toString() {
        return "TriggerZone [bounds=" + bounds + ", forcedDirection=" + forcedDirection + "]";
    }
    
    //----GETTERS AND SETTERS----
    public Rectangle getBounds() {
        return bounds;
    }

    public Direction getForcedDirection() {
        return forcedDirection;
    }
}
