package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import geeveedee.dualhorse.Enums.UUIDEnumerator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathHandler implements Listener {

    DualHorse main;

    public EntityDeathHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Horse) {
            horseDies(e);
        } else if (e.getEntity() instanceof Player) {
            playerDies(e);
        }
    }

    private void horseDies(EntityDeathEvent e) {
        Horse horse = (Horse) e.getEntity();

        if (!main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        main.RemoveKnownHorse(horse.getLocation(), horse.getUniqueId(), UUIDEnumerator.HORSE);
    }

    private void playerDies(EntityDeathEvent e) {
        Player player = (Player) e.getEntity();

        if (player.getVehicle() == null) {
            return;
        }

        if (player.getVehicle() instanceof Horse) {
            Horse horse = (Horse) player.getVehicle();

            if(!horse.isTamed()) {
                return;
            }

            if (!main.IsKnownHorse(horse.getUniqueId())) {
                return;
            }

            main.RemoveKnownHorse(horse.getLocation(), horse.getUniqueId(), UUIDEnumerator.HORSE);
        } else if (player.getVehicle() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) player.getVehicle();

            if (!main.IsKnownArmorstand(armorStand.getUniqueId())) {
                return;
            }

            main.RemoveKnownHorse(armorStand.getLocation(), armorStand.getUniqueId(), UUIDEnumerator.ARMORSTAND);
        }
    }
}

