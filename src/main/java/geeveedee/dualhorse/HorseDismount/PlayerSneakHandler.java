package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import geeveedee.dualhorse.Enums.UUIDEnumerator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Horse;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakHandler implements Listener {

    DualHorse main;

    public PlayerSneakHandler( DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {

        main.getLogger().info("Someone sneaked");
        if (!e.getPlayer().isInsideVehicle()) {
            main.getLogger().info("Return 1");
            return;
        }

        if (!(e.getPlayer().getVehicle() instanceof ArmorStand)) {
            main.getLogger().info("Return 2");
            return;
        }

        ArmorStand armorStand = (ArmorStand) e.getPlayer().getVehicle();

        if (!main.IsKnownArmorstand(armorStand.getUniqueId())) {
            main.getLogger().info("Return 3");
            return;
        }

        main.RemoveKnownHorse(armorStand.getLocation(), armorStand.getUniqueId(), UUIDEnumerator.ARMORSTAND);
    }
}
