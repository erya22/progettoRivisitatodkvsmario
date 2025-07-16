package defaultmain;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Player;
import view.ElencoView;
import view.GamePanel;
import view.SideMenuView;

/**
 * Classe principale per l'avvio dell'applicazione.
 * Inizializza la GUI, crea il mondo di gioco e avvia il gameloop chiamando GameEngine.
 */
public class GameLauncher {
	
    public static void main(String[] args) {
    	if (args.length < 2) {
    		System.err.println("usage: dkvsmario nickname server");
    		System.exit(0);
    	}
    	
    	ClientManager manager = new ClientManager(args[0], args[1]);
    	
    	GameSetter setter = new GameSetter();
    	setter.setupGame(); // crea tutto
    	
    	GamePanel panel = setter.getPanel();
    	GameEngine engine = setter.getEngine();
    	Player player = setter.getWorld().getPlayer();
    	
    	ElencoView elencoView = new ElencoView();
    	SideMenuView sideMenu = new SideMenuView(player, engine, elencoView);
    	elencoView.setSideMenu(sideMenu);
    	
    	setter.setSideMenu(sideMenu);
        

        // Pannello principale con layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(sideMenu, BorderLayout.EAST);
        mainPanel.add(panel, BorderLayout.CENTER);
        
        // JFrame
        JFrame frame = new JFrame("Donkey Kong VS Mario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        // Game Loop
        Thread gameThread = new Thread(engine);
        gameThread.start();
    }
}
