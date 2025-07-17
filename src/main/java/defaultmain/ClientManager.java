package defaultmain;

import java.io.IOException;
import java.util.ArrayList;

import dkserver.Client;
import dkserver.PlayerStatus;

/**
 * La classe gestisce la comunicazione tra il client locale e il server per sincronizzare lo stato dei giocatori in un gioco multiplayer.
 * È prevista una sola istanza per esecuzione.
 */
public class ClientManager {
	
	private static ClientManager instance;
	private ArrayList<PlayerStatus> elenco;
	private PlayerStatus myStatus;
	private Client client;
	
	/**
     * Costruttore.
     * @param nickname   il nome del giocatore locale
     * @param serverName il nome o indirizzo del server a cui connettersi
     */
	public ClientManager(String nickname, String serverName) {
		this.myStatus = new PlayerStatus(nickname, 0, 3, true);
		this.elenco = new ArrayList<PlayerStatus>();
		this.elenco.add(myStatus);
		try {
			this.client = new Client(serverName);
		} catch (IOException e) {
			client = null;
			e.printStackTrace();
		}
		instance = this;
		if (client == null) return;
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						elenco = client.read(myStatus);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			
		});
		t.start();
	}
	
	/**
     * Aggiorna lo stato del giocatore locale.
     * @param score il nuovo punteggio
     * @param vite  il numero di vite rimanenti
     * @param alive true se il giocatore è vivo, altrimenti false
     */
	public void update(long score, int vite, boolean alive) {
		myStatus.setScore(score);
		myStatus.setVite(vite);
		myStatus.setAlive(alive);
	}
	
	//----GETTERS AND SETTERS----
	
	/**
	 * Metodo che utilizziamo per prendere un'istanza del ClientManager, e accedere a variabili del server.
	 * @return l'istanza
	 */
	public static ClientManager instance() {
		return instance;
	}
	
	public PlayerStatus playerStatus() {
		return myStatus;
	}
	
	public Client getClient() {
		return client;
	}

	public ArrayList<PlayerStatus> getElenco() {
		return elenco;
	}

	public void setElenco(ArrayList<PlayerStatus> elenco) {
		this.elenco = elenco;
	}
}
