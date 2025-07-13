package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.PlayerController;
import model.DonkeyKong;
import model.GameItem;
import model.Peach;
import model.Player;
import model.World;

/**
 * Pannello centrale con il rendering.
 */
public class GamePanel extends JPanel {
	private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
	private World world;
	private PlayerController controller;
	private MapView mapView;
	
	 public GamePanel(World world, PlayerController controller, MapView mapView) {
        this.world = world;
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
	        
	        Player player = world.getPlayer();
	        Peach peach = world.getPeach();
	        DonkeyKong dk = world.getDk();
	        ArrayList<GameItem> item = world.getItems();

	        //Disegna la mappa
	        mapView.render((Graphics2D) g);
	        
	        // Disegna temporaneamente Mario come un rettangolo rosso
	        g.setColor(Color.RED);
	        g.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
	        
	        g.setColor(Color.BLUE);
	        g.fillRect(dk.getX(), dk.getY(), dk.getWidth(), dk.getHeight());
	        
	        g.setColor(Color.MAGENTA);
	        g.fillRect(peach.getX(), peach.getY(), peach.getWidth(), peach.getHeight());
	        
	        for (GameItem barrel : world.getItems()) {
	        	g.setColor(Color.GREEN);
		        g.fillOval(barrel.getX(), barrel.getY(), barrel.getWidth(), barrel.getHeight());
	        }
	        
	        
	    }
	
}
