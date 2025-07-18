package dkserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server per la gestione dello stato dei giocatori. Mantiene una lista concorrente di {@link PlayerStatus} aggiornati dai client,
 * e invia periodicamente la lista aggiornata di tutti i giocatori.
 */
public class Server implements Runnable {
    private final ConcurrentHashMap<String, PlayerStatus> elenco;
    private volatile long resetCounter = 0;
    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public Server() {
        this.elenco = new ConcurrentHashMap<>();
    }

    public static void main(String[] args) {
        Server server = new Server();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Chiusura del server");
            server.shutdown();
            
        }));

        Thread serverThread = new Thread(server);
        serverThread.start();

        while (server.running) {
            try {
                serverThread.join(10_000);
            } catch (InterruptedException e) {
                // Ignora interruzione
            }

            for (PlayerStatus status : server.elenco.values()) {
                System.out.format("%s [SCORE: %d] [VITE RIMANENTI: %d] [STATUS: %s] \n", 
                    status.getNickname(), 
                    status.getScore(), 
                    status.getVite(), 
                    status.isAlive() ? "vivo" : "morto"
                ); 
            }
        }
    }

    public void shutdown() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura del server: " + e.getMessage());
        }
    }

    /**
     * Metodo run del server che apre una ServerSocket sulla porta 21212, accetta connessioni client e crea un thread per 
     * gestire ciascuna di esse.
     */
    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(21212)) {
            this.serverSocket = ss;
            System.out.println("Server avviato");

            while (running) {
                try {
                    Socket client = ss.accept();
                    System.out.println("Client accettato");

                    Thread t = new Thread(() -> {
                        final long threadResetVersion = resetCounter;
                        String currentNickname = null;

                        try (
                            ObjectOutputStream o = new ObjectOutputStream(client.getOutputStream());
                            ObjectInputStream i = new ObjectInputStream(client.getInputStream())
                        ) {
                            while (running) {
                                if (resetCounter > threadResetVersion) {
                                    System.out.println("Thread client ignorato perché precedente al RESET");
                                    break;
                                }

                                Object obj = i.readObject();

                                if (obj instanceof PlayerStatus status) {
                                    String nick = status.getNickname();

                                    if ("__RESET__".equals(nick)) {
                                        elenco.clear();
                                        resetCounter++;
                                        System.out.println("RESET ricevuto: elenco dei player svuotato!");
                                    } else if ("__SHUTDOWN__".equals(nick)) {
                                        System.out.println("SHUTDOWN ricevuto: arresto server...");
                                        shutdown();
                                        break;
                                    } else {
                                        currentNickname = nick;
                                        elenco.put(currentNickname, status);
                                    }

                                    Collection<PlayerStatus> el = elenco.values();
                                    PlayerStatus[] lista = el.toArray(new PlayerStatus[0]);

                                    o.writeObject(lista);
                                    o.flush();
                                } else {
                                    System.out.println("Ricevuto oggetto di tipo sconosciuto: " + obj.getClass());
                                }
                            }
                        } catch (IOException | ClassNotFoundException e) {
                            if (e instanceof java.net.SocketException) {
                                System.out.printf("Client '%s' disconnesso (SocketException): %s\n",
                                    currentNickname != null ? currentNickname : "sconosciuto",
                                    e.getMessage()
                                );
                            } else {
                                System.out.printf("Client '%s' errore: %s\n",
                                    currentNickname != null ? currentNickname : "sconosciuto",
                                    e.getMessage()
                                );
                                e.printStackTrace(); 
                            }
                        } finally {
                            if (currentNickname != null) {
                                elenco.remove(currentNickname);
                                System.out.println("Giocatore rimosso dalla lista: " + currentNickname);
                            }
                            try {
                                client.close();
                            } catch (IOException e) {
                            }
                        }
                    });

                    t.start();

                } catch (IOException e) {
                    if (running) {
                        System.err.println("Errore durante l'accettazione di una connessione: " + e.getMessage());
                    } else {
                        System.out.println("Socket chiuso, server in arresto.");
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
