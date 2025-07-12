package defaultmain;

import javax.swing.JFrame;

import view.GamePanel;

public class GameLauncher {
    public static void main(String[] args) {
        GameSetter setter = new GameSetter();
        setter.setupGame(); // crea tutto

        GamePanel panel = setter.getPanel();

        // JFrame
        JFrame frame = new JFrame("Donkey Kong VS Mario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(panel);
        frame.setVisible(true);

        // Game loop
        while (true) {
            setter.getController().updateMovement(); // aggiornamento movimento
            panel.repaint(); // disegna
            try {
                Thread.sleep(16); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
