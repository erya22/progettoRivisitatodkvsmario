package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import controller.PlayerController;
import model.Player;

/**
 * Pannello centrale con il rendering.
 */
public class GamePanel extends JPanel {

	private Player player;
	private PlayerController controller;
	private MapView mapView;
	
	 public GamePanel(Player player, PlayerController controller, MapView mapView) {
        this.player = player;
        this.controller = controller;
        this.mapView = mapView;
        
        

        setFocusable(true);
        requestFocusInWindow(); // necessario per ricevere input
        addKeyListener(controller);
	 }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        //Disegna la mappa
	        mapView.render((Graphics2D) g);
	        
	        // Disegna temporaneamente Mario come un rettangolo rosso
	        g.setColor(Color.RED);
	        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
	    }
	
}
