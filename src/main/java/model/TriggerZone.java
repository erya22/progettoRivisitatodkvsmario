package model;

import java.awt.Rectangle;

public class TriggerZone {
	private Rectangle bounds; // rettangolo della zona
    private Direction forcedDirection; // direzione da impostare

    public TriggerZone(int x, int y, int width, int height, Direction forcedDirection) {
        this.bounds = new Rectangle(x, y, width, height);
        this.forcedDirection = forcedDirection;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Direction getForcedDirection() {
        return forcedDirection;
    }

    @Override
    public String toString() {
        return "TriggerZone [bounds=" + bounds + ", forcedDirection=" + forcedDirection + "]";
    }
}
