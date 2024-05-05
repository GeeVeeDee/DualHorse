package geeveedee.dualhorse.HorseMount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.*;
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

        if(!main.looksLikeAHorse(e.getRightClicked())) {
            return;
        }

        if(e.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        Entity horse = e.getRightClicked();

        if (horse.getPassengers().isEmpty()) {
            return;
        }

        if (main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        // Only check horse inventory as only they can have armor
        if (e.getRightClicked() instanceof Horse) {
            Horse rightClickedHorse = (Horse) e.getRightClicked();
            if(rightClickedHorse.getInventory().getArmor() != null && main.getConfig().getBoolean("block-when-horse-has-armor")) {
                return;
            }
        }

        Player player = (Player) e.getPlayer();
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(
                horse.getLocation().add(
                        main.getOffSetX(horse),
                        main.GetArmorstandHeight(e.getRightClicked()),
                        main.getOffSetZ(horse)
                ),
                EntityType.ARMOR_STAND
        );
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setVisible(false);
        armorStand.addPassenger(player);

        main.AddKnownHorse(horse.getUniqueId(), armorStand.getUniqueId());
    }
}
