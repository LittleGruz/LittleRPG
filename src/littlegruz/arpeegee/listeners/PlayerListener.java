package littlegruz.arpeegee.listeners;

import java.util.HashSet;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {
   private ArpeegeeMain plugin;
   
   public PlayerListener(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
      plugin.getPlayerMap().put(event.getPlayer().getName(), new RPGPlayer(event.getPlayer().getName(), "Warrior", "Minion"));
   }

   @EventHandler
   public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
      if(event.getDamager() instanceof Player){
         Player playa = (Player) event.getDamager();
         
         // Heal spell
         if(playa.getItemInHand().getData().toString().contains("WHITE DYE")){
            event.setCancelled(true);
            healSpell(playa, event.getEntity(), 0);
         }
         // Advanced heal spell
         else if(playa.getItemInHand().getData().toString().contains("BONE")){
            event.setCancelled(true);
            healSpell(playa, event.getEntity(), 1);
         }
         // Lightning (single target) spell
         else if(playa.getItemInHand().getData().toString().contains("YELLOW DYE")){
            Location loc = event.getEntity().getLocation();
            
            event.setCancelled(true);
            loc.setY(loc.getY() + 1);
            loc.getWorld().strikeLightningEffect(loc);
            //TODO Lightning damage
         }
      }
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event){
      // The fireball creation code is based off MadMatt199's code (https://github.com/madmatt199/GhastBlast)
      Player playa = event.getPlayer();
      playa.sendMessage(playa.getItemInHand().getData().toString());//Data checking
      
      if(playa.getItemInHand().getData().toString().contains("MAGENTA DYE")){
         HashSet<Byte> hs = new HashSet<Byte>();
         Block block;
         Location loc;
   
         hs.add((byte)0); //Air
         hs.add((byte)8); //Flowing water
         hs.add((byte)9); //Stationary water
         hs.add((byte)20); //Glass
         hs.add((byte)102); //Glass pane
         
         //TODO Flat distance until I determine a proper scale
         //playa.sendMessage(playa.getTargetBlock(hs, 20).getType().toString());
         block = playa.getTargetBlock(hs, 20);
         loc = block.getLocation();
         
         if(block.getType().compareTo(Material.AIR) != 0
               && block.getType().compareTo(Material.WATER) != 0
               && block.getType().compareTo(Material.STATIONARY_WATER) != 0
               && block.getType().compareTo(Material.GLASS) != 0
               && block.getType().compareTo(Material.THIN_GLASS) != 0){
            loc.setY(loc.getY() + 1);
            
            if(loc.getBlock().getType().compareTo(Material.WATER) == 0
                  || loc.getBlock().getType().compareTo(Material.STATIONARY_WATER) == 0
                  || loc.getBlock().getType().compareTo(Material.AIR) == 0){
               playa.teleport(loc);
               playa.sendMessage("*Zoom*");
            }
            else
               playa.sendMessage("You can not flash to there");
         }
         else
            playa.sendMessage("You can not flash that far!");
         
         event.setCancelled(true);
      }
      else if(playa.getItemInHand().getData().toString().contains("RED DYE")
            && event.getAction().toString().compareTo("LEFT_CLICK_AIR") == 0){
         Vector dir = playa.getLocation().getDirection().multiply(10);
         Location loc = playa.getLocation();
         
         EntityLiving entityPlaya = ((CraftPlayer) playa).getHandle();
         EntityFireball fireball = new EntityFireball(((CraftWorld) playa.getWorld()).getHandle(), entityPlaya, dir.getX(), dir.getY(), dir.getZ());
         
         // Spawn the fireball a bit up and away from the player
         fireball.locX = loc.getX() + (dir.getX()/5.0) + 0.25;
         fireball.locY = loc.getY() + (playa.getEyeHeight()/2.0);
         fireball.locZ = loc.getZ() + (dir.getZ()/5.0);
         dir = dir.multiply(10);
         
         ((CraftWorld) playa.getWorld()).getHandle().addEntity(fireball);
         
         event.setCancelled(true);
      }
   }
   
   private void healSpell(Player playa, Entity entity, int adv){
      if(entity instanceof Pig){
         playa.sendMessage("Pig healed");
         //TODO Player heal
      }
      // If player heals a zombie, then deal damage instead
      else if(entity instanceof Zombie){
         entity.playEffect(EntityEffect.HURT);
         //TODO Zombie damage
      }
   }
}
