package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.entity.ArmorStand;
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

        if (!(e.getPlayer().getVehicle() instanceof Horse)) {
            return;
        }

        Horse horse = (Horse) e.getPlayer().getVehicle();

        if(!horse.isTamed()) {
            return;
        }

        ArmorStand armorStand = main.knownHorse(horse);

        if (armorStand == null) {
            return;
        }

        ArmorStand stand = main.knownHorse(horse);
        assert stand != null;
        stand.remove();
        // TODO: rewrite to only use one list
        main.kh.remove(horse);
        main.hm.remove(horse);
    }
}
