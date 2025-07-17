package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import audio.AudioManager;
import defaultmain.GameLauncher;

/**
 * Classe Per startare il menu, che poi si occuperà di lanciare il gioco.
 * IMPORTANTE: runnare questa classe dopo aver startato il server
 */
public class MenuStart {
    public static void main(String[] args) {
        Font retroFont;
        try (InputStream fontStream = MenuStart.class.getResourceAsStream("/fontstexts/PressStart2P-Regular.ttf")) {
            retroFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, 10f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(retroFont);
        } catch (Exception e) {
            retroFont = new Font("Monospaced", Font.BOLD, 12);
            System.err.println("Errore nel caricamento del font.");
            e.printStackTrace();
        }

        UIManager.put("OptionPane.messageFont", retroFont);
        UIManager.put("OptionPane.buttonFont", retroFont);
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.background", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);

        ImageIcon icon = new ImageIcon(MenuStart.class.getResource("/playersprites/a1.png"));

        // Input nickname
        String nickname = (String) JOptionPane.showInputDialog(
            null,
            "INSERISCI IL TUO NICKNAME:",
            "DK VS MARIO",
            JOptionPane.PLAIN_MESSAGE,
            icon,
            null,
            null
        );
        if (nickname == null || nickname.trim().isEmpty()) {
            System.exit(0);
        }

        // Input server
        String server = (String) JOptionPane.showInputDialog(
            null,
            "INSERISCI L'INDIRIZZO DEL SERVER:",
            "DK VS MARIO",
            JOptionPane.PLAIN_MESSAGE,
            icon,
            null,
            null
        );
        if (server == null || server.trim().isEmpty()) {
            System.exit(0);
        }

        SwingUtilities.invokeLater(() -> {
        	SwingUtilities.invokeLater(() -> {
                new GameMenu(() -> {
                    // Al click nel menu, lancia GameLauncher
                    GameLauncher.main(new String[]{nickname, server});
                });
            });
            AudioManager.playBackgroundMusic("/audio/bacmusic.wav");
        });
    }
}