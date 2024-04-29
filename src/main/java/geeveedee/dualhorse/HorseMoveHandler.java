package geeveedee.dualhorse;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
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

        if (e.getPlayer().isInsideVehicle()) {
            if (e.getPlayer().getVehicle() instanceof Horse) {
                Horse h = (Horse) e.getPlayer().getVehicle();
                if (main.knownHorse(h) != null) {
                    ArmorStand stand = main.knownHorse(h);

                    stand.teleport(h.getLocation().add(main.getOffSetX(h), main.HorseHeight, main.getOffSetZ(h)));

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

                    Location loc = h.getLocation().add(main.getOffSetX(h), main.HorseHeight, main.getOffSetZ(h));

                    try {
                        methods[1].invoke(methods[0].invoke(stand), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                    } catch (Exception ex2) {
                    }
                }
            }
        }
    }
}
