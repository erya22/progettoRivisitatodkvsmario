package view;

import model.Barrel;

/**
 * Classe responsabile della rappresentazione grafica di un oggetto Barrel nel gioco. Estende {@link GameItemView}.
 */
public class BarrelView extends GameItemView {
    private Barrel barrel;
    
    /**
     * Costruttore.
     * 
     * @param barrel l'istanza del barrel da visualizzare
     */
    public BarrelView(Barrel barrel) {
        super(barrel);
        this.barrel = barrel;
    }
}
