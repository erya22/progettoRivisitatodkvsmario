package dkserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements Runnable {
	private ConcurrentHashMap<String, PlayerStatus> elenco;
	
	public Server() {
		this.elenco = new ConcurrentHashMap<String, PlayerStatus>();
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		Thread serverThread = new Thread(server);
		serverThread.start();
		while (true) {
			
			try {
				serverThread.join(10_000);
			} catch (InterruptedException e) {
				
			}
			System.out.println("la pizza è arrivata!");
			for (PlayerStatus status : server.elenco.values()) {
				System.out.format("%s %d %d %s \n", status.getNickname(), status.getScore(), status.getVite(), status.isAlive() ? "vivo" : "morto"); 
			}
		}
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(21212)) {
			while (true) {
				Socket client = serverSocket.accept();
				System.out.println("client accettato");
				
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
					    try (
					    		ObjectInputStream i = new ObjectInputStream(client.getInputStream());
					    		ObjectOutputStream o = new ObjectOutputStream(client.getOutputStream());
					    	) {
					        
					        while (true) {
					            // Leggi un oggetto
					            Object obj = i.readObject();

					            if (obj instanceof PlayerStatus status) {
						            System.out.println("obj " + status);
					                elenco.put(status.getNickname(), status);
					                System.out.println("Ricevuto in Server (Concurr) el: " + elenco);
					                Collection<PlayerStatus> el = elenco.values();
					                PlayerStatus[] lista = el.toArray(new PlayerStatus[el.size()]);
					                
			                        try {
			                        	o.writeObject(lista);
			                        	o.flush();
			                        } catch (IOException ex) {
			                            System.err.println("Errore nel mandare i dati a un client, rimuovo stream");
			                        }
					            } else {
					                System.out.println("Ricevuto un oggetto di tipo sconosciuto: " + obj.getClass());
					            }
					        }
					    } catch (IOException | ClassNotFoundException e) {
					        e.printStackTrace();
					    }
					}

					
				});
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
