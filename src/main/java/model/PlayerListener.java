package model;

/**
 * Interfaccia che definisce i metodi di callback utilizzati dal {@link Player}
 * per comunicare con altri componenti del gioco, evitando dipendenze circolari.
 */
public interface PlayerListener {

	void sideMenuRefresh();
	
	void stopGameLoop();

}
