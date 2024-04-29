package geeveedee.dualhorse;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.HorseInventory;

public class HorseMountHandler implements Listener {

    DualHorse main;

    public HorseMountHandler(DualHorse main) {
        this.main = main;
    }

    @EventHandler
    public void onHorseMount(VehicleEnterEvent e) {
        //Checks
        if(e.getEntered() instanceof Player && e.getVehicle() instanceof Horse) {
            Horse h = (Horse) e.getVehicle();
            if(h.isTamed()) {

                //Spawn armor stand
                Player player = (Player) e.getEntered();
                ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(h.getLocation().add(main.getOffSetX(h), main.HorseHeight, main.getOffSetZ(h)), EntityType.ARMOR_STAND);
                stand.setGravity(false);
                stand.setSmall(true);
                stand.setVisible(false);

                //Change Variables
                main.hm.put(h, stand);
                main.kh.add(h);
            }
        }
    }

    @EventHandler
    public void onHorseRightClick(PlayerInteractEntityEvent e) {

        main.Log("Player " + e.getPlayer().getName() + " rightclicked an entity");

        if(e.getRightClicked() instanceof Horse) {

            main.Log("Player " + e.getPlayer().getName() + " rightclicked a horse");

            //Prevent double trigger
            if(e.getHand().equals(EquipmentSlot.OFF_HAND)) return;

            Horse h = (Horse) e.getRightClicked();

            //h.getInventory().getArmor().getType().toString().toLowerCase().contains("armor")
            if(h.getInventory().getArmor() != null && main.getConfig().getBoolean("block-when-horse-has-armor")) {
                main.Log("The horse has horse armor, blocking mount");
                return;
            }

            if(main.knownHorse(h) != null && main.kh.contains(h)) {
                main.Log("Horse is a known horse");
                ArmorStand as = main.knownHorse(h);

                if (as.getPassengers().size() == 0) {
                    main.Log("Player " + e.getPlayer().getName() + " added to the horse.");
                    as.addPassenger(e.getPlayer());
                }

                //} else if (h.isTamed()) {
            } else {
                main.Log("Horse is not a known horse.");
            }


        }
    }

    @EventHandler
    public void onHorseDismount(VehicleExitEvent e) {
        if(e.getExited() instanceof Player && e.getVehicle() instanceof Horse) {
            Horse h = (Horse) e.getVehicle();
            if(h.isTamed()) {

                //Mounted Horse
                if (main.knownHorse(h) != null) {
                    //If player is alone on horse
                    ArmorStand stand = main.knownHorse(h);
                    stand.remove();
                    main.kh.remove(h);
                    main.hm.remove(h);

                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if(e.getPlayer() instanceof Player && e.getPlayer().getVehicle() instanceof Horse) {
            Horse h = (Horse) e.getPlayer().getVehicle();
            if(h.isTamed()) {

                //Mounted Horse
                if (main.knownHorse(h) != null) {
                    ArmorStand stand = main.knownHorse(h);
                    stand.remove();
                    main.kh.remove(h);
                    main.hm.remove(h);

                }
            }
        }
    }

    // Dismount player if armor is being put on the horse
    @EventHandler
    public void horseInventoryChange(InventoryClickEvent e) {
        // Do nothing if block setting is disabled
        if(!main.getConfig().getBoolean("block-when-horse-has-armor")) {
            return;
        }

        if (e.getClickedInventory() instanceof HorseInventory) {
            Horse h = (Horse) e.getClickedInventory().getHolder();

            ArmorStand as = main.knownHorse(h);
            if (as == null) {
                return;
            }

            if (e.getCurrentItem().getType().toString().toLowerCase().contains("armor") || e.getCursor().getType().toString().toLowerCase().contains("armor")) {
                // dismount player on armor stand
                for(Entity ent : as.getPassengers()) {
                    as.removePassenger(ent);
                }
            }
        }
    }
}
