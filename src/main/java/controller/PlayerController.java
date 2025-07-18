package controller;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import defaultmain.ClientManager;
import model.ActionState;
import model.Beam;
import model.Direction;
import model.Ladder;
import model.Player;
import model.PlayerState;
import model.Terrain;
import utils.BeamManager;

/**
 * Controller per la gestione dell'input tastiera del player. Gestisce il movimento orizzontale, verticale, il salto,
 * le interazioni con scale e travi, e l'uscita dal gioco.
 */
public class PlayerController implements KeyListener {
	private Player player;
	private final Set<Integer> keysPressed = new HashSet<>();
	private ArrayList<Beam> beams = BeamManager.loadSampleCollisions();
	
	//Variabili di uscita al programma
	private long escPressedTime = -1;
	private static final long ESC_HOLD_DURATION_MS = 1000;
	
	/**
     * Costruttore
     * @param player il player da controllare
     */
	public PlayerController(Player player) {
		this.player = player;
	}
	
	/**
     * Metodo principale di aggiornamento del movimento.
     */
	public void updateMovement() {
	    boolean movingHorizontally = false;
	    if (player.getPlayerState() == PlayerState.HIT_BY_BARREL || player.getCurrentActionState() == ActionState.HIT) {
	        keysPressed.clear();
	        return; // Blocca input se colpito
	    }

	    // ---- GESTIONE USCITA ----
	    if (keysPressed.contains(KeyEvent.VK_ESCAPE)) {
	        if (escPressedTime < 0) {
	            escPressedTime = System.currentTimeMillis();
	        } else if (System.currentTimeMillis() - escPressedTime >= ESC_HOLD_DURATION_MS) {
	            try {
					ClientManager.instance().getClient().sendShutdownCommand();
				} catch (IOException e) {
					e.printStackTrace();
				}
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



	/**
     * Gestisce la pressione di un tasto.
     * @param e l'evento KeyEvent corrispondente
     */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (player.getPlayerState()) {
		case HIT_BY_BARREL:
		case DEAD: 
		case SAVED_PEACH: return;
		}
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

	/**
     * Gestisce il rilascio di un tasto.
     * @param e l'evento KeyEvent corrispondente
     */
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
	
	/**
	 * Non utilizzato, necessario per l'implementazione dell'interfaccia.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}