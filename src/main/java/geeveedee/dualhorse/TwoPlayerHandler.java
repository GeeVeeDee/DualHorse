package geeveedee.dualhorse;

import com.sun.tools.javac.comp.Check;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TwoPlayerHandler implements Listener {

    DualHorse main;

    public TwoPlayerHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {

        //Check if both entities are players and are riding the correct vehicles
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

            Player horsePlayer;
            Player armorPlayer;

            // Get player on horse
            if (e.getEntity().getVehicle() instanceof Horse) {
                horsePlayer = (Player) e.getEntity();
            } else if (e.getDamager().getVehicle() instanceof Horse) {
                horsePlayer = (Player) e.getDamager();
            } else {
                return;
            }

            // Get player on Armor Stand
            if (e.getEntity().getVehicle() instanceof ArmorStand) {
                armorPlayer = (Player) e.getEntity();
            } else if (e.getDamager().getVehicle() instanceof ArmorStand) {
                armorPlayer = (Player) e.getDamager();
            } else {
                return;
            }

            //Cast entities
            Horse h = (Horse) horsePlayer.getVehicle();
            ArmorStand as = main.knownHorse(h);

            Boolean flag = false;

            //Check if uuid of passenger matches
            for(Entity ent : as.getPassengers()) {
                if(ent instanceof Player) {
                    Player p = (Player) ent;
                    if(p.getUniqueId() == armorPlayer.getUniqueId()) { flag = true; }
                }
            }

            //cancel event accordingly
            e.setCancelled(flag);
        }

    }

    @EventHandler
    public void onHorseDamage(EntityDamageByEntityEvent e) {
        //Check if Player is hitting horse
        if (e.getEntity() instanceof Horse && e.getDamager() instanceof Player) {

            Horse h = (Horse) e.getEntity();
            ArmorStand as = main.knownHorse(h);

            // Check if known horse
            if(as == null) {
                return;
            }

            //Check if uuid of passenger matches
            for(Entity ent : as.getPassengers()) {
                if(ent instanceof Player) {
                    Player p = (Player) ent;

                    if(p.getUniqueId() == e.getDamager().getUniqueId()) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
