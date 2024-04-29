package geeveedee.dualhorse.HorseMount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HorseRightClickHandler implements Listener {
    DualHorse main;

    public HorseRightClickHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onHorseRightClick(PlayerInteractEntityEvent e) {

        if(!(e.getRightClicked() instanceof Horse)) {
            return;
        }

        if(e.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        Horse horse = (Horse) e.getRightClicked();

        if(horse.getInventory().getArmor() != null && main.getConfig().getBoolean("block-when-horse-has-armor")) {
            return;
        }

        ArmorStand as = main.knownHorse(horse);

        // TODO: rewrite to only use one list
        // TODO: rewrite to user proper return statement
        if(main.knownHorse(horse) != null && main.kh.contains(horse)) {

            if (as.getPassengers().size() == 0) {
                as.addPassenger(e.getPlayer());
            }
        }
    }
}
