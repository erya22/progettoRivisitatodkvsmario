package view;

import java.awt.Graphics;

import model.Entity;

/**
 * Classe astratta che rappresenta la vista grafica di un'entità di gioco.
 */
public abstract class EntityView {
    private Entity entity;
    
    /**
     * Costruttore.
     * @param e l'entità da visualizzare
     */
    public EntityView(Entity e) {
        this.entity = e;
    }
    
    /**
     * Metodo per disegnare l'entità sul contesto grafico fornito.
     * @param entity l'entità da renderizzare
     * @param g il contesto grafico su cui disegnare
     */
    public void render(Entity entity, Graphics g) {
        // g.drawImage(entity.getCurrentFrame(), entity.getX(), entity.getY(), null);
    }
}
