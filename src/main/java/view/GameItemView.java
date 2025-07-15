package view;

import java.awt.Graphics;

import model.Entity;
import model.GameItem;

public class GameItemView {
	private GameItem item;
	
	public GameItemView(GameItem gi) {
		this.item = gi;
	}
	
	public void render(Entity entity, Graphics g) {
//        g.drawImage(entity.getCurrentFrame(), entity.getX(), entity.getY(), null);
    }

}
