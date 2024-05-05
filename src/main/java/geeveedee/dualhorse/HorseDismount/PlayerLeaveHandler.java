package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import geeveedee.dualhorse.Enums.UUIDEnumerator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveHandler implements Listener {
    DualHorse main;

    public PlayerLeaveHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (main.looksLikeAHorse(e.getPlayer().getVehicle())) {
            playerLeavesOnHorse(e);
        } else if (e.getPlayer().getVehicle() instanceof ArmorStand) {
            playerLeavesOnArmorStand(e);
        }
    }

    private void playerLeavesOnHorse(PlayerQuitEvent e) {
        Entity horse = e.getPlayer().getVehicle();

        if (!main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        main.RemoveKnownHorse(horse.getLocation(), horse.getUniqueId(), UUIDEnumerator.HORSE);
    }

    private void playerLeavesOnArmorStand(PlayerQuitEvent e) {
        ArmorStand armorStand = (ArmorStand) e.getPlayer().getVehicle();

        if (!main.IsKnownArmorstand(armorStand.getUniqueId())) {
            return;
        }

        main.RemoveKnownHorse(armorStand.getLocation(), armorStand.getUniqueId(), UUIDEnumerator.ARMORSTAND);
    }
}
