package littlegruz.arpeegee.listeners;

import java.util.HashSet;

import littlegruz.arpeegee.ArpeegeeMain;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntitySmallFireball;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PlayerInteract implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerInteract(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event){
      Player playa = event.getPlayer();
      //playa.sendMessage(playa.getItemInHand().getData().toString());//Data checking
      //playa.sendMessage(event.getAction().toString());//Data checking

      // Casting weapon to "Flash"
      if(playa.getItemInHand().getData().toString().contains("MAGENTA DYE")
            && event.getAction().toString().contains("RIGHT_CLICK")){
         HashSet<Byte> hs = new HashSet<Byte>();
         Block block;
         Location loc;
   
         hs.add((byte)0); //Air
         hs.add((byte)8); //Flowing water
         hs.add((byte)9); //Stationary water
         hs.add((byte)20); //Glass
         hs.add((byte)101); //Iron bar
         hs.add((byte)102); //Glass pane
         
         //TODO Flash range
         block = playa.getTargetBlock(hs, 20);
         loc = block.getLocation();
         
         //playa.sendMessage(block.getType().toString());
         
         if(block.getType().compareTo(Material.AIR) != 0
               && block.getType().compareTo(Material.WATER) != 0
               && block.getType().compareTo(Material.STATIONARY_WATER) != 0
               && block.getType().compareTo(Material.GLASS) != 0
               && block.getType().compareTo(Material.THIN_GLASS) != 0
               && block.getType().compareTo(Material.IRON_FENCE) != 0){
            loc.setY(loc.getY() + 1.5);
            
            if(loc.getBlock().getType().compareTo(Material.WATER) == 0
                  || loc.getBlock().getType().compareTo(Material.STATIONARY_WATER) == 0
                  || loc.getBlock().getType().compareTo(Material.AIR) == 0){
               playa.teleport(new Location(loc.getWorld(), loc.getX(),
                     loc.getY(), loc.getZ(), playa.getLocation().getYaw(),
                     playa.getLocation().getPitch()));
               playa.sendMessage("*Zoom*");
            }
            else
               playa.sendMessage("You can not flash to there");
         }
         else
            playa.sendMessage("You can not flash that far!");
         
         event.setCancelled(true);
      }
      // Melancholy (high intelligence only version of rage). Spawns sheep around mage.
      else if(playa.getItemInHand().getType().compareTo(Material.WHEAT) == 0
            && event.getAction().toString().compareTo("RIGHT_CLICK_AIR") == 0){
         Location loc = event.getPlayer().getLocation();
         plugin.getPlayerMap().get(playa.getName()).setRage(100);
         //TODO Add intelligence check
         if(plugin.getPlayerMap().get(playa.getName()).getRage() == 100){
            loc.setY(loc.getY() + 1.5);
            loc.setX(loc.getX() + 1);
            loc.getWorld().spawnCreature(loc, EntityType.SHEEP);
            loc.setX(loc.getX() - 2);
            loc.getWorld().spawnCreature(loc, EntityType.SHEEP);
            loc.setX(loc.getX() + 1);
            loc.setZ(loc.getZ() + 1);
            loc.getWorld().spawnCreature(loc, EntityType.SHEEP);
            loc.setZ(loc.getZ() - 2);
            loc.getWorld().spawnCreature(loc, EntityType.SHEEP);
            plugin.getPlayerMap().get(playa.getName()).setRage(0);
         }
      }
      // This fireball creation code is based off MadMatt199's code (https://github.com/madmatt199/GhastBlast)
      // Casting weapon to launch a fireball
      else if(playa.getItemInHand().getData().toString().contains("RED DYE")
            && event.getAction().toString().compareTo("RIGHT_CLICK_AIR") == 0){
         Vector dir = playa.getLocation().getDirection().multiply(10);
         Location loc = playa.getLocation();
         
         EntityLiving entityPlaya = ((CraftPlayer) playa).getHandle();
         EntitySmallFireball fireball = new EntitySmallFireball(
               ((CraftWorld) playa.getWorld()).getHandle(), entityPlaya,
               dir.getX(), dir.getY(), dir.getZ());
         
         // Spawn the fireball a bit up and away from the player
         fireball.locX = loc.getX() + (dir.getX()/5.0) + 0.25;
         fireball.locY = loc.getY() + (playa.getEyeHeight()/2.0);
         fireball.locZ = loc.getZ() + (dir.getZ()/5.0);
         dir = dir.multiply(10);
         
         ((CraftWorld) playa.getWorld()).getHandle().addEntity(fireball);

         playa.sendMessage("*Fwoosh*");
         event.setCancelled(true);
      }
      // Active berserk mode if player has gained enough rage
      else if(event.getAction().toString().contains("RIGHT_CLICK")
            && (playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0
                  || playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0)){
         final String pName = playa.getName();
         
         if(plugin.getPlayerMap().get(pName).getRage() == 100){
            playa.sendMessage("RAAAAGE (Berserker mode activated)");
            plugin.getPlayerMap().get(pName).setRage(0);
            plugin.getBerserkMap().put(pName, pName);
            
            // 10 seconds of Berserker mode (increased damage and sword bonuses)
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
               public void run() {
                  plugin.getBerserkMap().remove(pName);
                  plugin.getServer().getPlayer(pName).sendMessage("Berserker mode deactivated");
               }
           }, 200L);
         }
         else
            playa.sendMessage("Not enough rage. Current rage: " + Integer.toString(plugin.getPlayerMap().get(playa.getName()).getRage()));
      }
   }
}
