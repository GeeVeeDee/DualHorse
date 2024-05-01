package geeveedee.dualhorse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import geeveedee.dualhorse.Enums.UUIDEnumerator;
import geeveedee.dualhorse.HorseDismount.*;
import geeveedee.dualhorse.HorseMount.HorseRightClickHandler;
import geeveedee.dualhorse.MobilityLogic.HorseDamageHandler;
import geeveedee.dualhorse.MobilityLogic.HorseMoveHandler;
import geeveedee.dualhorse.MobilityLogic.PlayerDamageHandler;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DualHorse extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new HorseRightClickHandler(this), this);

        Bukkit.getPluginManager().registerEvents(new EntityDeathHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDismountHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSneakHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new HorseInventoryUpdateHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveHandler(this), this);

        Bukkit.getPluginManager().registerEvents(new HorseMoveHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamageHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new HorseDamageHandler(this), this);

        getLogger().info("DualHorse Plugin - made by GeeVeeDee");
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player.getVehicle() == null) {
                continue;
            }

            if (!horseArmorStandLink.containsValue(player.getVehicle().getUniqueId())) {
                continue;
            }

            player.getVehicle().remove();
        }

        getLogger().info("Safely disabled DualHorses plugin.");
    }

    // Main hashmap
    private HashMap<UUID, UUID> horseArmorStandLink = new HashMap<UUID, UUID>();

    // Constants
    public double HorseHeight = 0.45;
    private double Amplifier = 0.5;

    public boolean IsKnownHorse(UUID horseUUID)
    {
        return horseArmorStandLink.containsKey(horseUUID);
    }

    public boolean IsKnownArmorstand(UUID armorstandUUID) {
        return horseArmorStandLink.containsValue(armorstandUUID);
    }

    public UUID GetKnownArmorstandFromHorseUUID(UUID horseUUID) {
        if (horseArmorStandLink.containsKey(horseUUID)) {
            return horseArmorStandLink.get(horseUUID);
        }

        return null;
    }

    @org.jetbrains.annotations.Nullable
    public UUID GetKnownHorseFromArmorstandUUID(UUID armorstandUUID) {
        for (HashMap.Entry<UUID, UUID> entry : horseArmorStandLink.entrySet()) {
            if (entry.getValue().equals(armorstandUUID)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void AddKnownHorse(UUID horseUUID, UUID armorstandUUI) {
        horseArmorStandLink.put(horseUUID, armorstandUUI);
    }

    public void RemoveKnownHorse(Location location, UUID uuid, UUIDEnumerator enumerator) {

        UUID armorstandUUID;

        if (enumerator == UUIDEnumerator.HORSE) {
            armorstandUUID = horseArmorStandLink.get(uuid);
            horseArmorStandLink.remove(uuid);
        } else {
            armorstandUUID = uuid;
            UUID key = GetKnownHorseFromArmorstandUUID(uuid);
            horseArmorStandLink.remove(key);
        }

        GetArmorstand(location, armorstandUUID).remove();
    }

    public ArmorStand GetArmorstand(Location location, UUID armorstandUUID) {

        World world = location.getWorld();
        location.add(0, 1, 0);

        for (Entity entity : world.getNearbyEntities(location, 4, 2, 4)) {
            if (entity.getUniqueId().equals(armorstandUUID)) {
                return (ArmorStand) entity;
            }
        }

        return null;
    }

	/*
	//Returns horse if horse is ridden
	public Horse isMounted(Horse h) {
		for (Horse kh : rh) {
			if (kh.getUniqueId().equals(h.getUniqueId())) {
				return kh;
			}
		}
		return null;
	}

	//Returns horse if horse is double mounted
	public Horse isDoubleMounted(Horse h) {
		for (Horse kh : dm) {
			if (kh.getUniqueId().equals(h.getUniqueId())) {
				return kh;
			}
		}
		return null;
	}

    //Returns horse AS linked to horse if horse is known
    public ArmorStand knownHorse(Horse h) {
        for (Horse kh : hm.keySet()) {
            if (kh.getUniqueId().equals(h.getUniqueId())) {
                return getEntityByUniqueId(hm.get(kh));
            }
        }
        return null;
    }

    //Returns armorstand in the world with the same UUID
    public ArmorStand getEntityByUniqueId(ArmorStand as){
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (Entity entity : chunk.getEntities()) {
                    if (entity.getUniqueId().equals(as.getUniqueId()))
                        return (ArmorStand) entity;
                }
            }
        }

        return null;
    } */

    //Transfer degrees to radian
    public double inRadians (float degrees) {
        return degrees * Math.PI / 180;
    }

    //Calculates X coords to be added
    public double getOffSetX(Horse h) {
        float pitch = h.getLocation().getYaw() + 90;
        return (double) Amplifier * -Math.cos(inRadians(pitch));
    }

    //Calculates Z coords to be added
    public double getOffSetZ(Horse h) {
        float pitch = h.getLocation().getYaw() + 90;
        return (double) Amplifier * -Math.sin(inRadians(pitch));
    }
}
