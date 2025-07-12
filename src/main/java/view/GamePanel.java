package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.PlayerController;
import model.DonkeyKong;
import model.Peach;
import model.Player;

/**
 * Pannello centrale con il rendering.
 */
public class GamePanel extends JPanel {
	private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
	private Player player;
	private PlayerController controller;
	private DonkeyKong dk;
	private Peach peach;
	private MapView mapView;
	
	 public GamePanel(Player player, DonkeyKong dk, Peach peach, PlayerController controller, MapView mapView) {
        this.player = player;
        this.dk = dk;
        this.peach = peach;
        this.controller = controller;
        this.mapView = mapView;
        
        
        setBackground(Color.BLACK);
        
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
	        
	        g.setColor(Color.BLUE);
	        g.fillRect(dk.getX(), dk.getY(), dk.getWidth(), dk.getHeight());
	        
	        g.setColor(Color.MAGENTA);
	        g.fillRect(peach.getX(), peach.getY(), peach.getWidth(), peach.getHeight());
	        
	    }
	
}
