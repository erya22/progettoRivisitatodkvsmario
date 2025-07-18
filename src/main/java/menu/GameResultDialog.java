package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Window;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.slf4j.LoggerFactory;

import audio.AudioManager;
import defaultmain.ClientManager;
import dkserver.PlayerStatus;
import model.Player;
import model.PlayerState;

public class GameResultDialog extends JDialog {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameResultDialog.class);
    private Font retroFont;
    
    
    /**
     * Costruttore
     * @param window finestra di dialogo
     * @param p1 player locale
     * @param elenco elenco dei players 
     * @param winner vincitore 
     */
    public GameResultDialog(Window window, Player p1, ArrayList<PlayerStatus> elenco, String winner) {
        super(window, "Game Over", ModalityType.APPLICATION_MODAL);
        
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                Window owner = getOwner();
                if (owner != null) {
                    owner.dispose();
                }
                System.exit(0);
            }
        });

        AudioManager.pauseBackgroundMusic();
        AudioManager.playOneShotMusic("/audio/win1.wav"); 
        if(elenco.size() == 1) {
        	initSinglePlayerUI(p1.getScore(), p1.getPlayerState());
        } else {
        	ArrayList<String> playerNames = new ArrayList<>();
            ArrayList<Long> playerScores = new ArrayList<>();

            for (PlayerStatus ps : elenco) {
                playerNames.add(ps.getNickname());
                playerScores.add(ps.getScore());
            }

            initMultiPlayerUI(playerNames, playerScores, winner);
        }
    }

    /**
     * Interfaccia utente jdialog se sono in singleplayer
     * @param score punteggio del giocatore locale
     */
    private void initSinglePlayerUI(int score, PlayerState state) {
        try {
            retroFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fontstexts/PressStart2P-Regular.ttf"))
                    .deriveFont(Font.PLAIN, 16f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(retroFont);
        } catch (Exception e) {
            System.err.println("Errore nel caricamento del font retro");
            retroFont = new Font("Monospaced", Font.BOLD, 16); // fallback
            e.printStackTrace();
        }

        setSize(400, 250);
        setLocationRelativeTo(getOwner());

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titolo principale
        JLabel titleLabel = new JLabel(state == PlayerState.SAVED_PEACH ? "YOU WIN!" : "GAME OVER", SwingConstants.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(retroFont);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Pannello centrale con score e messaggio
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setBackground(Color.BLACK);

        Font smallerFont = retroFont.deriveFont(10f);

        if (state == PlayerState.SAVED_PEACH) {
            JLabel savedPrincess = new JLabel("Hai salvato la principessa", SwingConstants.CENTER);
            savedPrincess.setForeground(Color.PINK);
            savedPrincess.setFont(smallerFont);
            centerPanel.add(savedPrincess);
        }

        JLabel scoreLabel = new JLabel("YOUR SCORE: " + score, SwingConstants.CENTER);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setFont(smallerFont);
        centerPanel.add(scoreLabel);

        JLabel thanksLabel = new JLabel("THANKS FOR PLAYING!", SwingConstants.CENTER);
        thanksLabel.setForeground(Color.GREEN);
        thanksLabel.setFont(smallerFont);
        centerPanel.add(thanksLabel);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(createBottomButtonsPanel(getOwner()), BorderLayout.SOUTH);

        setContentPane(panel);
        setResizable(false);
    }


    /**
     *  Interfaccia utente jdialog se sono in multiplayer
     * @param playerNames nick dei giocatori
     * @param playerScores punteggi dei giocatori
     * @param winnerName nick del vicitore
     */
    private void initMultiPlayerUI(ArrayList<String> playerNames, ArrayList<Long> playerScores, String winnerName) {
        try {
            retroFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fontstexts/PressStart2P-Regular.ttf"))
                    .deriveFont(Font.PLAIN, 16f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(retroFont);
        } catch (Exception e) {
            System.err.println("Errore nel caricamento del font retro");
            retroFont = new Font("Monospaced", Font.BOLD, 16); // fallback
            e.printStackTrace();
        }

        setSize(500, 300 + playerNames.size() * 25); // aumenta l’altezza dinamicamente
        setLocationRelativeTo(getOwner());

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(retroFont);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel scoresPanel = new JPanel(new GridLayout(playerNames.size() + 1, 1, 5, 2)); 
        scoresPanel.setBackground(Color.BLACK);

        Font smallerFont = retroFont.deriveFont(10f);

        for (int i = 0; i < playerNames.size(); i++) {
            JLabel playerLabel = new JLabel(playerNames.get(i) + " score: " + playerScores.get(i), SwingConstants.CENTER);
            playerLabel.setForeground(Color.YELLOW);
            playerLabel.setFont(smallerFont);
            scoresPanel.add(playerLabel);
        }

        JLabel winnerLabel = new JLabel(winnerName == null ? "DRAW" : winnerName + " WINS!", SwingConstants.CENTER);
        winnerLabel.setForeground(Color.GREEN);
        winnerLabel.setFont(retroFont);
        scoresPanel.add(winnerLabel);

        panel.add(scoresPanel, BorderLayout.CENTER);
        panel.add(createBottomButtonsPanel(getOwner()), BorderLayout.SOUTH);

        setContentPane(panel);
        setResizable(false);
    }
    
    /**
     * Gestisce schermata e buttons del play again
     * @param toDispose la finestra principale da chiudere
     * @return un {@link JPanel} contenente il messaggio e i bottoni YES/NO
     */
    private JPanel createBottomButtonsPanel(Window toDispose) {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);

        JLabel replayLabel = new JLabel("PLAY AGAIN?", SwingConstants.CENTER);
        replayLabel.setForeground(Color.WHITE);
        replayLabel.setFont(retroFont);
        bottomPanel.add(replayLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(Color.BLACK);

        JButton yesButton = new JButton("YES");
        yesButton.setFont(retroFont.deriveFont(10f));
        yesButton.setBackground(Color.DARK_GRAY);
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.addActionListener(e -> {
            AudioManager.stopMusic();
            dispose();
            if (toDispose != null) toDispose.dispose();
            try {
                ClientManager.instance().getClient().sendResetCommand(); //Svuota l'elenco in caso di nuova partita
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new Thread(() -> MenuStart.main(new String[]{})).start();
        });

        JButton noButton = new JButton("NO");
        noButton.setFont(retroFont.deriveFont(10f));
        noButton.setBackground(Color.DARK_GRAY);
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.addActionListener(e -> {
            dispose();
            if (toDispose != null) toDispose.dispose();

            try {
                PlayerStatus shutdown = new PlayerStatus("__SHUTDOWN__", 0, 0, false);
                ClientManager.instance().getClient().sendShutdownCommand();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.exit(0);
        });

        buttonsPanel.add(yesButton);
        buttonsPanel.add(noButton);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }
}