package utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enum che rappresenta i vari sprite grafici usati nel gioco.
 */
public enum Sprite {
    
    PEACH("/npcsprites/peach.png"),
    DK_LANCIA_BARILE_R("/npcsprites/h3.png"),
    DK_L("/npcsprites/h1.png"),
    DK_REST("/npcsprites/h4.png"),
    DK_ROAR("/npcsprites/h5.png"),
    DK_ROAR2("/npcsprites/h2.png"),
    MARIO_IDLE_CLIMB("/playersprites/b1.png"),
    MARIO_WALK_R("/playersprites/a1.png"),
    MARIO_WALK_R1("/playersprites/a2.png"),
    MARIO_WALK_R2("/playersprites/a3.png"),
    MARIO_WALK_R3("/playersprites/a4.png"),
    MARIO_WALK_L("/playersprites/m1.png"),
    MARIO_WALK_L1("/playersprites/m2.png"),
    MARIO_WALK_JUMP_L_L2("/playersprites/m3.png"),
    MARIO_WALK_L3("/playersprites/m4.png"),
    MARIO_JUMP_R("/playersprites/c3.png"),
    MARIO_HIT("/playersprites/e1.png"),
    MARIO_HIT1("/playersprites/e2.png"),
    MARIO_HIT2("/playersprites/e3.png"),
    MARIO_HIT3("/playersprites/e4.png"),
    MARIO_HIT4("/playersprites/e5.png"),
    BARREL1("/barrelhammer/barrel1.png"),
    BARREL2("/barrelhammer/barrel2.png"),
    BARREL3("/barrelhammer/barrel3.png"),
    BARREL4("/barrelhammer/barrel4.png"),
    BARREL5("/barrelhammer/barrel5.png"),
    BARREL_PILE("/barrelhammer/barili.png"),
	MENU("/menuview/Title.png"),
	SIDEMENU_WASD("/sidemenuview/WASD.png"),
	SIDEMENU_FRECCE("/sidemenuview/FRECCE.png");
    
    private final Logger log = LoggerFactory.getLogger(Sprite.class);
    
    BufferedImage img;
    
    /**
     * Costruttore.
     * 
     * @param path il percorso relativo alla risorsa dell'immagine
     * @throws RuntimeException se non è possibile leggere l'immagine dal percorso
     */
    Sprite(String path) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            img = ImageIO.read(in);
        } catch (IOException e) {
            log.debug("cannot read path {}", path, e);
            throw new RuntimeException("Cannot load sprite image from path: " + path, e);
        }
    }
    
    /**
     * Restituisce l'immagine associata a questo sprite.
     * 
     * @return l'immagine caricata
     */
    public BufferedImage img() {
        return img;
    }
    
    /**
     * Ridimensiona un'immagine ad una nuova larghezza e altezza specificata.
     * 
     * @param originalImage l'immagine originale da ridimensionare
     * @param targetWidth la nuova larghezza desiderata
     * @param targetHeight la nuova altezza desiderata
     * @return una nuova BufferedImage ridimensionata
     */
    public static BufferedImage resize(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }
}
