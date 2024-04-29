package geeveedee.dualhorse;

import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathHandler implements Listener {

    DualHorse main;

    public DeathHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Horse) {
            Horse h = (Horse) e.getEntity();
            if(main.knownHorse(h) != null) {main.hm.remove(h);}
            if(main.kh.contains(h)) {main.kh.remove(h);}
        }
    }

}
