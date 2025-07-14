package defaultmain;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Barrel;
import model.World;
import view.GamePanel;
import view.SideMenuView;

public class GameLauncher {
    public static void main(String[] args) {
        GameSetter setter = new GameSetter();
        setter.setupGame(); // crea tutto
        World world = setter.getWorld();
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
        while (true) {
        	world.update(world.getBeams());
        	if (world.getDk().canThrowBarrel()) {
        	    Barrel b = world.getDk().throwBarrel();
        	    world.addItem(b);
        	}

            setter.getPcontroller().updateMovement(); // aggiornamento movimento
            panel.repaint(); // disegna
            try {
                Thread.sleep(16); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
