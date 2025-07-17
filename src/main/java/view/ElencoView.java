package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import defaultmain.ClientManager;
import dkserver.PlayerStatus;

/**
 * Classe che gestisce l'elenco aggiornato dei giocatori
 * e aggiorna periodicamente la vista laterale (SideMenuView).
 */
public class ElencoView {

    private static final Logger log = LoggerFactory.getLogger(ElencoView.class);

    private ArrayList<PlayerStatus> elenco = new ArrayList<>();

    private SideMenuView sideMenu;

    /**
     * Crea una nuova istanza di {@code ElencoView} e avvia un timer
     * per aggiornare l'elenco dei giocatori ogni 10 secondi.
     */
    public ElencoView() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                PlayerStatus ps = ClientManager.instance().playerStatus();
                log.debug("ps: {}", ps);
                try {
                    elenco = ClientManager.instance().getClient().read(ps);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                log.debug("sto eseguendo: {}", ClientManager.instance());

                if (sideMenu != null) {
                    SwingUtilities.invokeLater(() -> sideMenu.sideMenuRefresh());
                } else {
                    log.warn("sideMenu nullo: impossibile aggiornare SideMenuView");
                }
            }
        }, 2000, 10000); // prima esecuzione dopo 2s, poi ogni 10s
    }

    //----GETTERS AND SETTERS----
    
    public List<PlayerStatus> getElenco() {
        return this.elenco;
    } 
    
    public void setSideMenu(SideMenuView sideMenu) {
        this.sideMenu = sideMenu;
    }
}
