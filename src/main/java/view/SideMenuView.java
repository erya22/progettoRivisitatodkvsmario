package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Player;
import model.PlayerListener;

public class SideMenuView extends JPanel implements PlayerListener {
    private Font customFont;
    private BufferedImage arrowImg, wasdImg, viteImg;
    private Player player;

    
    public SideMenuView(Player player) {
    	this.player = player;
    	this.player.setListener(this);
    	
    	//CARICAMENTO IMMAGINI
    	try {
            arrowImg = ImageIO.read(getClass().getResourceAsStream("/SIDEMENU/FRECCE.png"));
            wasdImg = ImageIO.read(getClass().getResourceAsStream("/SIDEMENU/WASD.png"));
            viteImg = ImageIO.read(getClass().getResourceAsStream("/res/PLAYER/a1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	//CARICAMENTO FONT
        try {
            InputStream is = getClass().getResourceAsStream("/FONTS/PressStart2P-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.PLAIN, 14); // fallback
        }

        // Calcola metà larghezza schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int halfWidth = screenSize.width / 2;
        int fixedHeight = 600; // o qualsiasi altezza desiderata

        // Imposta dimensione preferita
        setPreferredSize(new Dimension(halfWidth, fixedHeight));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int x = 20, y = 30;

        // Titolo
        g2.setColor(Color.WHITE);
        g2.setFont(customFont.deriveFont(Font.BOLD, 18f));
        g2.drawString("---ISTRUZIONI---", x, y);

        // Muovi (WASD oppure Frecce direzionali)
        y += 40;
        if (wasdImg != null && arrowImg != null) {
            int imgW = 120, imgH = 75;
            int spacing = 20;
            int oppureSpacing = 15;

            // Calcolo posizione immagini e testo "oppure"
            int wasdX = x;
            int arrowX = wasdX + imgW + spacing + 100; // abbastanza spazio per "oppure"
            int oppureX = wasdX + imgW + spacing; // posizione "oppure"

            // Disegna WASD
            g2.drawImage(wasdImg, wasdX, y, imgW, imgH, null);

            // Disegna "oppure"
            g2.setFont(customFont.deriveFont(Font.PLAIN, 14f));
            g2.setColor(Color.WHITE);
            g2.drawString("oppure", oppureX, y + imgH / 2 + 5);

            // Disegna Frecce
            g2.drawImage(arrowImg, arrowX, y, imgW, imgH, null);

            // Scritta "Muovi" a destra
            g2.setFont(customFont.deriveFont(Font.PLAIN, 16f));
            int textX = arrowX + imgW + spacing;
            g2.drawString(": Muovi", textX, y + imgH / 2 + 5);
        }
        
        g2.setFont(customFont.deriveFont(Font.PLAIN, 14f));
        g2.setColor(Color.WHITE);
        
        // Salto 
        y += 120;
        g2.drawString("[ENTER oppure SPAZIO]      : Salta", x, y);
        
        //Esc
        y += 40;
        g2.drawString("[TIENI PREMUTO ESC]        : Esci", x, y);
        
        //Punteggio
        y += 120;
        g2.setColor(Color.WHITE);
        g2.setFont(customFont.deriveFont(Font.BOLD, 18f));
        g2.drawString("---PUNTEGGIO---", x, y);

        g2.setFont(customFont.deriveFont(Font.PLAIN, 14f));
        g2.setColor(Color.WHITE);
        
        y += 40;
        g2.drawString("Salta i barili per guadagnare punti!", x, y);
        y += 40;
        g2.drawString("Hai a disposizione tre vite", x, y);
        
        y += 40;
        g2.drawString("SCORE: " /*+ player.score*/ , x, y); // da implementare playerscore
        
        //Immagini vite rimanenti
        y += 40;
        g2.drawString("VITE RIMANENTI: ", x, y);
        if(viteImg != null) {
        	for(int i = 1; i <= player.getPlayerLives(); i++) // da implementare con le vite del player correnti
        		g2.drawImage(viteImg, x + 180 + (i * 40), y - 32, 32, 32, null);
        }
    }
    
    public void onPlayerDamaged() {
        repaint(); // o aggiorna la vita
    }
}
