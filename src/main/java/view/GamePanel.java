package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import controller.PlayerController;
import model.Player;

/**
 * Pannello centrale con il rendering
 */
public class GamePanel extends JPanel {

	private Player player;
	private PlayerController controller;
	
	 public GamePanel(Player player, PlayerController controller) {
	        this.player = player;
	        this.controller = controller;

	        setFocusable(true);
	        requestFocusInWindow(); // necessario per ricevere input
	        addKeyListener(controller);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        // Disegna temporaneamente Mario come un rettangolo rosso
	        g.setColor(Color.RED);
	        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
	    }
	
}
