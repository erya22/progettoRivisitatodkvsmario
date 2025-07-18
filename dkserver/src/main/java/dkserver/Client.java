package dkserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Rappresenta un client che si connette al server di gioco per inviare
 * lo stato del giocatore e ricevere lo stato aggiornato di tutti i giocatori.
 */
public class Client {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    /**
     * Costruttore.
     * @param serverName indirizzo IP o hostname del server
     * @throws UnknownHostException se il server non è raggiungibile
     * @throws IOException se si verifica un errore nella creazione del socket o degli stream
     */
    public Client(String serverName) throws UnknownHostException, IOException {
        socket = new Socket(serverName, 21212);

        // ATTENZIONE: output prima di input per evitare blocchi
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Chiude il socket e gli stream di input/output del client.
     */
    public void close() throws IOException {
        try {
            if (oos != null) oos.close();
            if (ois != null) ois.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del client: " + e.getMessage());
        }
    }

    /**
     * Invia lo stato del giocatore al server e riceve la lista aggiornata di tutti gli stati dei giocatori connessi.
     * @param ps lo stato del giocatore da inviare
     * @return una lista aggiornata degli stati di tutti i giocatori
     */
    public ArrayList<PlayerStatus> read(PlayerStatus ps) throws IOException, ClassNotFoundException {
        try {
            oos.reset();
            oos.writeObject(ps);
            oos.flush();

            System.out.printf("Inviato: %s | Punteggio: %d | Vite: %d | %s\n",
                ps.nickname, ps.score, ps.vite, ps.alive ? "Alive" : "Dead");

            Object obj = ois.readObject();
            if (obj instanceof PlayerStatus[] playerArray) {
                ArrayList<PlayerStatus> playerList = new ArrayList<>();
                for (PlayerStatus item : playerArray) {
                    playerList.add(item);
                }
                return playerList;
            } else {
                throw new IOException("Oggetto ricevuto non è una lista di PlayerStatus");
            }
        } catch (java.net.SocketException se) {
            throw se; 
        }
    }



    /**
     * Invia un comando speciale al server per azzerare la lista dei giocatori.
     * Usato, ad esempio, quando il giocatore sceglie di rigiocare.
     * @throws IOException se si verifica un errore nell'invio del comando
     */
    public void sendResetCommand() throws IOException {
        PlayerStatus reset = new PlayerStatus("__RESET__", 0, 0, false);
        oos.reset();
        oos.writeObject(reset);
        oos.flush();
    }
    
    /**
     * Invia il comando di spegnimento al server.
     */
    public void sendShutdownCommand() throws IOException {
        PlayerStatus shutdown = new PlayerStatus("__SHUTDOWN__", 0, 0, false);
        oos.reset();
        oos.writeObject(shutdown);
        oos.flush();
    }
}
