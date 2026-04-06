package geeveedee.dualhorse.MobilityLogic;

import geeveedee.dualhorse.DualHorse;
import jdk.jfr.internal.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class HorseMoveHandler implements Listener {

    DualHorse main;

    public HorseMoveHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent e) {
        if (!e.getPlayer().isInsideVehicle()) {
            return;
        }

        if (!main.looksLikeAHorse(e.getPlayer().getVehicle())) {
            return;
        }

        Entity horse = e.getPlayer().getVehicle();

        if (!main.IsKnownHorse(horse.getUniqueId())) {
            return;
        }

        ArmorStand armorStand = main.GetArmorstand(horse.getLocation(), main.GetKnownArmorstandFromHorseUUID(horse.getUniqueId()));
        //main.getLogger().info(armorStand.toString());

        Location loc = horse.getLocation().add(
                main.getOffSetX(horse),
                main.GetArmorstandHeight(e.getPlayer().getVehicle()),
                main.getOffSetZ(horse)
        );

        Location target = horse.getLocation().add(
                main.getOffSetX(horse),
                main.GetArmorstandHeight(e.getPlayer().getVehicle()),
                main.getOffSetZ(horse)
        );


// ...
        Location target = ...; // your target location
        entity.teleport(target, TeleportFlag.EntityState.RETAIN_PASSENGERS);

        // Doesn't work
        /*
        Vector velocity = target.toVector().subtract(armorStand.getLocation().toVector());
        velocity.multiply(0.5); // smoothing factor (0.3–0.7 works well)

        Player player = (Player) armorStand.getPassengers().getFirst();
        armorStand.eject();
        armorStand.setVelocity(velocity);
        armorStand.addPassenger(player);*/

        // Jittery
        /*
        Player player = (Player) armorStand.getPassengers().getFirst();
        armorStand.eject();
        armorStand.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        armorStand.addPassenger(player);
        */


        /*armorStand.teleport(horse.getLocation().add(main.getOffSetX(horse), main.GetArmorstandHeight(e.getPlayer().getVehicle()), main.getOffSetZ(horse)));

        Method[] methods = ((Supplier<Method[]>) () -> {
            try {
                Method getHandle = Class.forName(Bukkit.getServer().getClass().getPackage().getName() + ".entity.CraftEntity").getDeclaredMethod("getHandle");
                return new Method[] {
                        //pre-1.18: "setPositionRotation"
                        //post-1.18: "b"
                        getHandle, getHandle.getReturnType().getDeclaredMethod("b", double.class, double.class, double.class, float.class, float.class)
                };
            } catch (Exception ex1) {
                return null;
            }
        }).get();

        Location loc = horse.getLocation().add(main.getOffSetX(horse), main.GetArmorstandHeight(e.getPlayer().getVehicle()), main.getOffSetZ(horse));

        try {
            methods[1].invoke(methods[0].invoke(armorStand), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        } catch (Exception ex2) {
        }*/
    }
}
