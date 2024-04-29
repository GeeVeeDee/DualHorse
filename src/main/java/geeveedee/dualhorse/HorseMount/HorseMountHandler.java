package geeveedee.dualhorse.HorseMount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class HorseMountHandler implements Listener {

    DualHorse main;

    public HorseMountHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onHorseMount(VehicleEnterEvent e) {

        if(!(e.getEntered() instanceof Player)) {
            return;
        }

        if (!(e.getVehicle() instanceof Horse)) {
            return;
        }

        Horse horse = (Horse) e.getVehicle();

        if(!horse.isTamed()) {
            return;
        }

        Player player = (Player) e.getEntered();
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(
                horse.getLocation().add(
                        main.getOffSetX(horse),
                        main.HorseHeight,
                        main.getOffSetZ(horse)
                ),
                EntityType.ARMOR_STAND
        );
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);

        // TODO: rewrite to only use one list
        main.hm.put(horse, armorStand);
        main.kh.add(horse);
    }
}
