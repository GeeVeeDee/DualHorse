package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class HorseDismountHandler implements Listener {
    DualHorse main;

    public HorseDismountHandler(DualHorse main) {
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

        if (main.knownHorse(horse) == null) {
            return;
        }

        // TODO: rewrite to only use one list
        ArmorStand armorStand = main.knownHorse(horse);
        assert armorStand != null;
        armorStand.remove();

        main.kh.remove(horse);
        main.hm.remove(horse);
    }
}
