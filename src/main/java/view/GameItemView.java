package view;

import java.awt.Graphics;

import model.GameItem;

/**
 * Classe che rappresenta la vista grafica di un'entità di gioco.
 */
public class GameItemView {
	private GameItem item;
	
	/**
     * Costruttore.
     * @param gi l'item da visualizzare
     */
	public GameItemView(GameItem gi) {
		this.item = gi;
	}
	
	/**
     * Metodo per disegnare l'item sul contesto grafico fornito.
     * @param gi l'entità da renderizzare
     * @param g il contesto grafico su cui disegnare
     */
	public void render(GameItem gi, Graphics g) {
//        g.drawImage(entity.getCurrentFrame(), entity.getX(), entity.getY(), null);
    }

}
