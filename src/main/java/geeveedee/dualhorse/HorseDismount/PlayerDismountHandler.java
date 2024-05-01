package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import geeveedee.dualhorse.Enums.UUIDEnumerator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class PlayerDismountHandler implements Listener {
    DualHorse main;

    public PlayerDismountHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onHorseDismount(VehicleExitEvent e) {
        if(!(e.getExited() instanceof Player)) {
            return;
        }

        if (!(e.getVehicle() instanceof Horse)) {
            return;
        }

        Horse horse = (Horse) e.getVehicle();

        if(!horse.isTamed()) {
            return;
        }

        if (!main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        main.RemoveKnownHorse(horse.getLocation(), horse.getUniqueId(), UUIDEnumerator.HORSE);
    }
}
