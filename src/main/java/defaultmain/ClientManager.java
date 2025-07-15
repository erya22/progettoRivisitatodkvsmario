package defaultmain;

import java.io.IOException;
import java.util.ArrayList;

import dkserver.Client;
import dkserver.PlayerStatus;

public class ClientManager {
	
	private static ClientManager instance;
	private ArrayList<PlayerStatus> elenco;
	private PlayerStatus myStatus;
	private Client client;
	
	public ClientManager(String nickname, String serverName) {
		this.myStatus = new PlayerStatus(nickname, 0, 3, true);
		this.elenco = new ArrayList<PlayerStatus>();
		this.elenco.add(myStatus);
		try {
			this.client = new Client(serverName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			
		});
		
		t.start();
		
	}
	
	public synchronized void update(long score, int vite, boolean alive) {
		myStatus.setScore(score);
		myStatus.setVite(vite);
		myStatus.setAlive(alive);
	}
	
	public ClientManager instance() {
		return instance;
	}

}
