package geeveedee.dualhorse.MobilityLogic;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageHandler implements Listener {

    DualHorse main;

    public PlayerDamageHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player horsePlayer;
        Player armorPlayer;

        // TODO: rewrite to only use one list
        // TODO: actually completely rewrite this to be simpler, but using the new list

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