package littlegruz.arpeegee.listeners;

import java.util.HashSet;

import littlegruz.arpeegee.ArpeegeeMain;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PlayerInteractListener implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerInteractListener(ArpeegeeMain instance){
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
         
         //TODO Flat distance until I determine a proper scale
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
      // This fireball creation code is based off MadMatt199's code (https://github.com/madmatt199/GhastBlast)
      // Casting weapon to launch a fireball
      else if(playa.getItemInHand().getData().toString().contains("RED DYE")
            && event.getAction().toString().compareTo("RIGHT_CLICK_AIR") == 0){
         Vector dir = playa.getLocation().getDirection().multiply(10);
         Location loc = playa.getLocation();
         
         EntityLiving entityPlaya = ((CraftPlayer) playa).getHandle();
         EntityFireball fireball = new EntityFireball(
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
            playa.sendMessage("RAAAAGE (Berserk mode activated)");
            plugin.getPlayerMap().get(pName).setRage(0);
            plugin.getBerserkMap().put(pName, pName);
            // 10 seconds of Berserker mode (increased damage and sword bonuses)
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
               public void run() {
                  plugin.getBerserkMap().remove(pName);
                  plugin.getServer().getPlayer(pName).sendMessage("Cool it");
               }
           }, 200L);
         }
         else
            playa.sendMessage("Not enough rage. Current rage: " + Integer.toString(plugin.getPlayerMap().get(playa.getName()).getRage()));
      }
   }
}
