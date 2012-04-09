package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
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
      
      if(playa.getItemInHand().getData().toString().contains("RED DYE")
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
