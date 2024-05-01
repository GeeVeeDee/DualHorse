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
        if (!(e.getEntity() instanceof Player) || !e.getEntity().isInsideVehicle()) {
            return;
        }

        if (!(e.getDamager() instanceof Player) || !e.getDamager().isInsideVehicle()) {
            return;
        }

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

        Horse horse = (Horse) horsePlayer.getVehicle();

        if (!main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        if (main.GetKnownArmorstandFromHorseUUID(horse.getUniqueId()) != armorPlayer.getVehicle().getUniqueId()) {
            return;
        }

        e.setCancelled(true);
    }
}