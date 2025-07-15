package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import utils.Constants;
import utils.Sprite;

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
	        
	        Graphics2D g2d = (Graphics2D) g;
	        
	        Player player = world.getPlayer();
	        Peach peach = world.getPeach();
	        DonkeyKong dk = world.getDk();
	        ArrayList<GameItem> item = world.getItems();
	        BufferedImage barrelPile = Sprite.resize(Sprite.BARREL_PILE.img(), Constants.TILE_SIZE * 3, Constants.TILE_SIZE * 3);

	        //Disegna la mappa
	        mapView.render((Graphics2D) g);
	        
	        g2d.drawImage(barrelPile, 0, 8 * Constants.TILE_SIZE - 10, null);
	        
	        // Disegna temporaneamente Mario come un rettangolo rosso
	        g2d.drawImage(player.getCurrentFrame(), player.getX(), player.getY(), null);
	        
	        g2d.drawImage(dk.getCurrentFrame(), dk.getX(), dk.getY(), null);
	        
	        g2d.drawImage(peach.getCurrentFrame(), peach.getX(), peach.getY(), null);
	        
	        for (GameItem barrel : world.getItems()) {
	        	g.drawImage(barrel.getCurrentFrame(), barrel.getX(), barrel.getY(), null);
	        }
	        
	        
	    }
	
}
