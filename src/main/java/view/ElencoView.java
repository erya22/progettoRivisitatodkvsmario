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

public class ElencoView {
	private static final Logger log = LoggerFactory.getLogger(ElencoView.class);
	private ArrayList<PlayerStatus> elenco = new ArrayList<PlayerStatus>();
	SideMenuView sideMenu;
	
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					log.debug("sto eseguendo: {}", ClientManager.instance());
					if (sideMenu != null) {
					    SwingUtilities.invokeLater(() -> sideMenu.sideMenuRefresh());
					} else {
					    log.warn("sideMenu nullo: impossibile aggiornare SideMenuView");
					}

				}
			}, 2000, 10000);
	}
	
	public List<PlayerStatus> getElenco() {
		return this.elenco;
	}

	public void setSideMenu(SideMenuView sideMenu) {
		this.sideMenu = sideMenu;
	}
	
	
}
