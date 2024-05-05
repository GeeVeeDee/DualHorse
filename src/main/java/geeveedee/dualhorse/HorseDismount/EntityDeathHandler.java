package geeveedee.dualhorse.HorseDismount;

import geeveedee.dualhorse.DualHorse;
import geeveedee.dualhorse.Enums.UUIDEnumerator;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
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
        if(main.looksLikeAHorse(e.getEntity())) {
            horseDies(e);
        } else if (e.getEntity() instanceof Player) {
            playerDies(e);
        }
    }

    private void horseDies(EntityDeathEvent e) {
        Entity horse = e.getEntity();

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

        if (main.looksLikeAHorse(player.getVehicle())) {
            Entity horse = player.getVehicle();

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

