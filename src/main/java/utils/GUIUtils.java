package utils;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Metodi di utilità per la GUI.
 */
public class GUIUtils {
	
	/**
	 * Setta immagine icona
	 * @param frame frame a cui applicare l'icona
	 */
    public static void applyIcon(JFrame frame) {
        try {
            BufferedImage icon = Sprite.MARIO_WALK_R.img();
            frame.setIconImage(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}