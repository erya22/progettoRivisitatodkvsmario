package dkserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client {
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public Client(String serverName) throws UnknownHostException, IOException{
		socket = new Socket(serverName, 21212);
		
		// ATTENZIONE: output prima di input per evitare blocchi
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	public void close() throws IOException {
		try {
            if (oos != null) oos.close();
            if (ois != null) ois.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del client: " + e.getMessage());
        }
	}
	
	public ArrayList<PlayerStatus> read(PlayerStatus ps) throws IOException, ClassNotFoundException {
		oos.reset();
		oos.writeObject(ps);
		
		// Debug log
        System.out.printf("Inviato: %s | Punteggio: %d | Vite: %d | %s\n", ps.nickname, ps.score, ps.vite, ps.alive ? "Alive":"Dead");

        // Lettura della risposta dal server
        Object obj = ois.readObject();
        if (obj instanceof PlayerStatus[] playerArray) {
            try {
                ArrayList<PlayerStatus> playerList = new ArrayList<PlayerStatus>();
                for (PlayerStatus item : playerArray) {
                	playerList.add(item);
                }
                return playerList;
            } catch (ClassCastException e) {
                throw new IOException("Errore nel cast della risposta del server", e);
            }
        } else {
            throw new IOException("Oggetto ricevuto non è una lista di PlayerStatus");
        }
    
	}
	
    
}
