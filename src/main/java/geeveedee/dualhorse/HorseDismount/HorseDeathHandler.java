package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class HorseDeathHandler implements Listener {

    DualHorse main;

    public HorseDeathHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if(!(e.getEntity() instanceof Horse)) {
            return;
        }

        Horse horse = (Horse) e.getEntity();

        // TODO: rewrite to only use one list
        if(main.knownHorse(horse) != null) {main.hm.remove(horse);}
        main.kh.remove(horse);
    }

}

