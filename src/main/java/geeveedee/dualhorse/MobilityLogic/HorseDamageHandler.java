package geeveedee.dualhorse.MobilityLogic;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HorseDamageHandler implements Listener {

    DualHorse main;

    public HorseDamageHandler (DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onHorseDamage(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Horse)) {
            return;
        }

        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Horse horse = (Horse) e.getEntity();
        ArmorStand armorStand = main.knownHorse(horse);

        if(armorStand == null) {
            return;
        }

        //Check if uuid of passenger matches
        for(Entity ent : armorStand.getPassengers()) {

            if(!(ent instanceof Player)) {
                continue;
            }

            Player p = (Player) ent;

            if(p.getUniqueId() != e.getDamager().getUniqueId()) {
                continue;
            }

            e.setCancelled(true);
        }
    }
}
