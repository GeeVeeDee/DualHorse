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
        armorStand.teleport(horse.getLocation().add(main.getOffSetX(horse), main.GetArmorstandHeight(e.getPlayer().getVehicle()), main.getOffSetZ(horse)));

        Method[] methods = ((Supplier<Method[]>) () -> {
            try {
                Method getHandle = Class.forName("org.bukkit.craftbukkit.entity.CraftEntity")
                        .getDeclaredMethod("getHandle");
                getHandle.setAccessible(true);

                Method snapToMethod = null;
                Class<?> clazz = getHandle.getReturnType();
                while (clazz != null && snapToMethod == null) {
                    try {
                        // pre-1.18: "setPositionRotation"
                        // post-1.18 (obfuscated): "b"
                        // 26.1+ (unobfuscated): "snapTo"
                        snapToMethod = clazz.getDeclaredMethod("snapTo", double.class, double.class, double.class, float.class, float.class);
                    } catch (NoSuchMethodException ex) {
                        clazz = clazz.getSuperclass();
                    }
                }

                if (snapToMethod == null) return null;
                snapToMethod.setAccessible(true);
                return new Method[] { getHandle, snapToMethod };
            } catch (Exception ex1) {
                ex1.printStackTrace();
                return null;
            }
        }).get();

        Location loc = horse.getLocation().add(
                main.getOffSetX(horse),
                main.GetArmorstandHeight(e.getPlayer().getVehicle()),
                main.getOffSetZ(horse)
        );
        try {
            methods[1].invoke(methods[0].invoke(armorStand), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
}
