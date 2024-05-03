package geeveedee.dualhorse.MobilityLogic;

import geeveedee.dualhorse.DualHorse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

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
        armorStand.teleport(horse.getLocation().add(main.getOffSetX(horse), main.GetArmorstandHeight(e.getPlayer().getVehicle()), main.getOffSetZ(horse)));

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
        }

    }
}
