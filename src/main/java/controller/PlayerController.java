package controller;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.ActionState;
import model.Collision;
import model.Direction;
import model.Ladder;
import model.Player;
import model.PlayerState;
import model.Terrain;
import utils.CollisionManager;

public class PlayerController implements KeyListener {
	private static final Logger log = LoggerFactory.getLogger(PlayerController.class);
	//TODO: manca la view
	private Player player;
	private final Set<Integer> keysPressed = new HashSet<>();
	private ArrayList<Collision> beams = CollisionManager.loadSampleCollisions();
	
	//Variabili di uscita al programma
	private long escPressedTime = -1;
	private static final long ESC_HOLD_DURATION_MS = 1000;
	
	public PlayerController(Player player) {
		this.player = player;
	}
	
	public void updateMovement() {
	    boolean movingHorizontally = false;
	    if (player.getPlayerState() == PlayerState.HIT_BY_BARREL) {
	    	keysPressed.clear();
	        return; // Blocca input
	    }


	    // ---- GESTIONE USCITA ----
	    if (keysPressed.contains(KeyEvent.VK_ESCAPE)) {
	        if (escPressedTime < 0) {
	            escPressedTime = System.currentTimeMillis();
	        } else if (System.currentTimeMillis() - escPressedTime >= ESC_HOLD_DURATION_MS) {
	            System.exit(0);
	        }
	    } else {
	        escPressedTime = -1; // reset se ESC non è premuto
	    }

	    // ---- GESTIONE MOVIMENTO ORIZZONTALE ----
	    if (keysPressed.contains(KeyEvent.VK_RIGHT) || keysPressed.contains(KeyEvent.VK_D) && player.getCurrentTerrain() == Terrain.BEAM) {
	        player.setCurrentDirection(Direction.RIGHT);
	        player.walk(Direction.RIGHT);
	        movingHorizontally = true;
	    } else if (keysPressed.contains(KeyEvent.VK_LEFT) || keysPressed.contains(KeyEvent.VK_A) && player.getCurrentTerrain() == Terrain.BEAM) {
	        player.setCurrentDirection(Direction.LEFT);
	        player.walk(Direction.LEFT);
	        movingHorizontally = true;
	    }

	    // ---- SE IN SCALA E MUOVI ORIZZONTALMENTE, VERIFICA SE ESCE DALLA SCALA ----
	    if (movingHorizontally && player.getCurrentTerrain() == Terrain.LADDER) {
	    	player.climb(player.getCurrentDirection());
	        boolean stillOnLadder = false;
	        Rectangle ladderBounds = player.getLadderBounds();

	        for (Ladder ladder : player.getLadders()) {
	            if (ladder.getBounds().intersects(ladderBounds)) {
	                stillOnLadder = true;
	                break;
	            }
	        }

	        if (!stillOnLadder) {
	            player.setCurrentTerrain(Terrain.AIR);
	            player.setCurrentActionState(ActionState.FALLING);
	        }
	    }

	    // ---- GESTIONE IDLE SE FERMO E A TERRA ----
	    if (!movingHorizontally &&
	        player.getCurrentActionState() != ActionState.JUMPING &&
	        player.getCurrentActionState() != ActionState.FALLING &&
	        player.getCurrentActionState() != ActionState.CLIMBING &&
	        player.getCurrentTerrain() == Terrain.BEAM) {
	        player.setCurrentActionState(ActionState.IDLE);
	    }

	    // ---- GESTIONE SALTO SOLO SE SU TRAVE ----
	    if (keysPressed.contains(KeyEvent.VK_SPACE) &&
	        player.getCurrentActionState() != ActionState.JUMPING &&
	        player.getCurrentTerrain() == Terrain.BEAM) {
	        player.jump(movingHorizontally);
	    }

	    // ---- AGGIORNA DATI E FISICA ----
	    player.update();
	    player.updatePhysics(beams);
	}




	@Override
	public void keyPressed(KeyEvent e) {
		switch (player.getPlayerState()) {
		case HIT_BY_BARREL:
		case DEAD: 
		case WINNER: return;
		}
//		log.debug("Tasto premuto {}", e.getKeyCode());
		keysPressed.add(e.getKeyCode());
		
		boolean leftPressed = keysPressed.contains(KeyEvent.VK_A) || keysPressed.contains(KeyEvent.VK_LEFT);
		boolean rightPressed = keysPressed.contains(KeyEvent.VK_D) || keysPressed.contains(KeyEvent.VK_RIGHT);
		boolean movingHorizontally = leftPressed || rightPressed;
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			player.walk(Direction.RIGHT);
			break;
			
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			player.walk(Direction.LEFT);
			break;
			
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			player.climb(Direction.UP); // da implementare
			break;
			
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			player.climb(Direction.DOWN); // da implementare
			break;
			
		case KeyEvent.VK_SPACE:
		case KeyEvent.VK_ENTER:
			player.jump(movingHorizontally); // da implementare
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed.remove(e.getKeyCode());
		
		// Definisci il set dei tasti di movimento e salto da controllare
		Set<Integer> movementKeys = Set.of(
		    KeyEvent.VK_D, KeyEvent.VK_RIGHT,
		    KeyEvent.VK_A, KeyEvent.VK_LEFT,
		    KeyEvent.VK_W, KeyEvent.VK_UP,
		    KeyEvent.VK_S, KeyEvent.VK_DOWN,
		    KeyEvent.VK_SPACE, KeyEvent.VK_ENTER
		);

		// Controlla se nessuno di questi tasti è premuto
		boolean noMovementKeyPressed = keysPressed.stream().noneMatch(movementKeys::contains);

		if (noMovementKeyPressed && player.getCurrentTerrain() != Terrain.AIR && player.getCurrentTerrain() != Terrain.LADDER) {
		    player.setCurrentActionState(ActionState.IDLE);
		    player.setCurrentFrameIndex(0);
		}

		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
