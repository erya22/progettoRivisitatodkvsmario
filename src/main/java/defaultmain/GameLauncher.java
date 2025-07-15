package defaultmain;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dkserver.Client;
import view.GamePanel;
import view.SideMenuView;

public class GameLauncher {
	
    public static void main(String[] args) {
    	
        GameSetter setter = new GameSetter();
        setter.setupGame(); // crea tutto
        GamePanel panel = setter.getPanel();
        SideMenuView sideMenu = setter.getSideMenu();

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

        // Game loop
        GameEngine engine = setter.getEngine();
        Thread gameThread = new Thread(engine);
        gameThread.start();
    }
}
