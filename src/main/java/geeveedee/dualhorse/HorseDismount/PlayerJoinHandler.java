package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinHandler implements Listener {
    DualHorse main;

    public PlayerJoinHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().isInsideVehicle()) {
            return;
        }

        if (!(e.getPlayer().getVehicle() instanceof ArmorStand)) {
            return;
        }

        e.getPlayer().getVehicle().remove();
    }
}
