package utils;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.imageio.ImageIO;
import com.google.gson.Gson;
import model.TileMap;

/**
 * Classe di utilità per caricare una mappa e il relativo tileset da file risorse.
 */
public class TileMapLoader {

	/**
     * Carica la mappa JSON dalla risorsa e la deserializza in un oggetto TileMap.
     * @return oggetto TileMap caricato dal file JSON, o null in caso di errore
     */
    public static TileMap loadMap() {
        try (InputStream jsonStream = TileMapLoader.class.getResourceAsStream("/mappadimension32/JSON/mappa25m_colladder.json")) {
            if (jsonStream == null) throw new FileNotFoundException("File JSON non trovato!");
            Reader reader = new InputStreamReader(jsonStream);
            return new Gson().fromJson(reader, TileMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Carica l'immagine del tileset dalla risorsa.
     * @return immagine BufferedImage del tileset, o null in caso di errore
     */
    public static BufferedImage loadTileset() {
        try (InputStream imageStream = TileMapLoader.class.getResourceAsStream("/mappadimension32/TILESET/TraviScale.png")) {
            if (imageStream == null) throw new FileNotFoundException("Tileset non trovato!");
            return ImageIO.read(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
