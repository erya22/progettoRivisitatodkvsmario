package view;

import java.awt.Graphics;

import model.Entity;

public abstract class EntityView {
	private Entity entity;
	public EntityView(Entity e) {
		this.entity = e;
	}
	public void render(Entity entity, Graphics g) {
//        g.drawImage(entity.getCurrentFrame(), entity.getX(), entity.getY(), null);
    }
}
