package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.HorseInventory;

public class HorseInventoryUpdateHandler implements Listener {
    DualHorse main;

    public HorseInventoryUpdateHandler(DualHorse main) {
        this.main = main;
    }

    // Dismount player if armor is being put on the horse
    @EventHandler
    public void horseInventoryChange(InventoryClickEvent e) {

        if(!main.getConfig().getBoolean("block-when-horse-has-armor")) {
            return;
        }

        if (!(e.getClickedInventory() instanceof HorseInventory)) {
            return;
        }

        Horse horse = (Horse) e.getClickedInventory().getHolder();
        ArmorStand armorStand = main.knownHorse(horse);

        if (armorStand == null) {
            return;
        }

        if (!(e.getCurrentItem().getType().toString().toLowerCase().contains("armor")) && !(e.getCursor().getType().toString().toLowerCase().contains("armor"))) {
            return;
        }

        for(Entity ent : armorStand.getPassengers()) {
            armorStand.removePassenger(ent);
        }
    }
}
