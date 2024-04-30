package geeveedee.dualhorse.HorseMount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
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

        if (horse.getPassengers().isEmpty()) {
            return;
        }

        if (main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        if(horse.getInventory().getArmor() != null && main.getConfig().getBoolean("block-when-horse-has-armor")) {
            return;
        }

        Player player = (Player) e.getPlayer();
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
        // TODO: put to false after development
        armorStand.setVisible(true);
        armorStand.addPassenger(player);

        main.AddKnownHorse(horse.getUniqueId(), armorStand.getUniqueId());
    }
}
