package geeveedee.dualhorse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.java.JavaPlugin;

public final class DualHorse extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new HorseMountHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new HorseMoveHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new TwoPlayerHandler(this), this);

        getLogger().info("DualHorse Plugin - made by GeeVeeDee");
    }

    @Override
    public void onDisable() {
        for (ArmorStand as : hm.values()) {
            as.remove();
        }

        getLogger().info("Safely disabled DualHorses plugin.");
    }

    // Careful: potention cause for memory leak. See when can be emptied/restrict growth
    // Aslo, wtf are these variable names
    public HashMap<Horse, ArmorStand> hm = new HashMap<Horse, ArmorStand>();
    public Set<Horse> kh = new HashSet<Horse>();
    //public Set<Horse> dm = new HashSet<Horse>();

    //Constants
    public double HorseHeight = 0.45;
    private double Amplifier = 0.5;

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
	}*/

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
    }

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

    public void Log(String string) {
        //this.getLogger().info(string);
    }
}
