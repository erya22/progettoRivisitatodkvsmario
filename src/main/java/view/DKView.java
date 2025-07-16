package view;

import model.DonkeyKong;

/**
 * Classe responsabile della rappresentazione grafica di dk nel gioco. Estende {@link EntityView}.
 */
public class DKView extends EntityView {
	private DonkeyKong dk;
	
	/**
     * Costruttore.
     * 
     * @param dk l'istanza da visualizzare
     */
	public DKView(DonkeyKong dk) {
		super(dk);
	}
	
	
}
