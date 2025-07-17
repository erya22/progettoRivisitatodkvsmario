package menu;

import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import defaultmain.ClientManager;
import dkserver.PlayerStatus;
import model.Player;

/**
 * La classe {@code GameResultManager} gestisce la logica di fine partita
 * e la visualizzazione del risultato, sia in modalità singolo giocatore che multiplayer.
 */
public class GameResultManager {

    /**
     * Verifica se la partita è in modalità multiplayer.
     *
     * @param elenco la lista degli stati dei giocatori
     * @return true se ci sono più di un giocatore, false altrimenti
     */
    public static boolean isMultiplayer(ArrayList<PlayerStatus> elenco) {
        return elenco.size() > 1;
    }

    /**
     * Gestisce la fine della partita per un giocatore. Se in modalità multiplayer,
     * mostra una finestra di attesa fino a quando tutti i giocatori sono morti.
     * In seguito, mostra il risultato finale della partita.
     *
     * @param player           il giocatore locale
     * @param parentComponent  il componente padre usato per centrare le finestre di dialogo
     */
    public static void endGame(Player player, Component parentComponent) {
        if (isMultiplayer(ClientManager.instance().getElenco())) {
            // Mostra dialogo di attesa finché anche l'altro giocatore non termina
            JDialog waitingDialog = waitingDialog(parentComponent);

            new Thread(() -> {
                while (!(bothPlayersDead(ClientManager.instance().getElenco()))) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {}
                }

                SwingUtilities.invokeLater(() -> {
                    waitingDialog.dispose();
                    mostraGameResultDialog(player, parentComponent);
                });
            }).start();
        } else {
            // Modalità singolo giocatore: mostra subito il risultato
            mostraGameResultDialog(player, parentComponent);
        }
    }

    /**
     * Crea e mostra una finestra di dialogo che informa il giocatore
     * di attendere la fine della partita degli altri giocatori.
     *
     * @param parentComponent il componente genitore per la posizione del dialogo
     * @return il {@code JDialog} visualizzato
     */
    public static JDialog waitingDialog(Component parentComponent) {
        Window window = SwingUtilities.getWindowAncestor(parentComponent);
        JDialog waitingDialog = new JDialog(window, "In attesa");
        JLabel label = new JLabel("Attendi che l'altro giocatore finisca...", SwingConstants.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 14));
        waitingDialog.add(label);
        waitingDialog.setSize(300, 120);
        waitingDialog.setLocationRelativeTo(window);
        waitingDialog.setVisible(true);
        return waitingDialog;
    }

    /**
     * Controlla se tutti i giocatori nella lista sono morti.
     *
     * @param elenco la lista degli stati dei giocatori
     * @return true se tutti i giocatori sono morti, false altrimenti
     */
    public static boolean bothPlayersDead(ArrayList<PlayerStatus> elenco) {
        for (PlayerStatus ps : elenco) {
            if (ps.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mostra il dialogo di risultato della partita, visualizzando
     * il punteggio del giocatore locale e lo stato degli altri.
     *
     * @param player           il giocatore locale
     * @param parentComponent  il componente padre per posizionare la finestra
     */
    public static void mostraGameResultDialog(Player player, Component parentComponent) {
        int p1Score = player.getScore();
        String winner = "Player 1"; // Per ora hardcoded

        GameResultDialog dialog = new GameResultDialog(
            SwingUtilities.getWindowAncestor(parentComponent),
            p1Score,
            ClientManager.instance().getElenco(),
            winner
        );
        dialog.setVisible(true);
    }
}
