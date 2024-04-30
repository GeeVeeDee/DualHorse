package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import geeveedee.dualhorse.Enums.UUIDEnumerator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.HorseInventory;

import java.util.Locale;
import java.util.UUID;

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

        if (!(e.getCurrentItem().getType().toString().toLowerCase().contains("armor")) && !(e.getCursor().getType().toString().toLowerCase().contains("armor"))) {
            return;
        }

        Horse horse = (Horse) e.getClickedInventory().getHolder();

        if (!main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        main.RemoveKnownHorse(horse.getLocation(), horse.getUniqueId(), UUIDEnumerator.HORSE);
    }
}
