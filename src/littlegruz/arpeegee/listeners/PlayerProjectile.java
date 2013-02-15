package littlegruz.arpeegee.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

import littlegruz.arpeegee.ArpeegeeMain;

public class PlayerProjectile implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerProjectile(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onPlayerBowShoot(EntityShootBowEvent event){
      if(plugin.getWorldsMap().containsKey(event.getEntity().getWorld().getName())){
         if(event.getEntity() instanceof Player){
            Player playa = (Player) event.getEntity();
   
            if(plugin.getRangedPlayerMap().get(playa.getName()) != null){
               // Normal arrow
               if(playa.getInventory().getHeldItemSlot() == 0){
               plugin.getProjMap().put(event.getProjectile(),
                     Integer.toString(plugin.getRangedPlayerMap().get(playa.getName()).getGearLevel()) + "|1");
               }
               else if(playa.getInventory().getHeldItemSlot() == 1){
                  // Slow arrow
                  if(playa.getLevel() >= 7){
                     plugin.getProjMap().put(event.getProjectile(),
                           Integer.toString(plugin.getRangedPlayerMap().get(playa.getName()).getGearLevel()) + "|2");
                     playa.getInventory().setItemInHand(null);
                     plugin.getRangedPlayerMap().get(playa.getName()).setSlowBowReadiness(false);
                     plugin.giveCooldown(playa, "slow", "ranged", 3);
                  }
                  // Sheep arrow
                  else if(playa.getLevel() >= 7){
                     plugin.getProjMap().put(event.getProjectile(),
                           Integer.toString(plugin.getRangedPlayerMap().get(playa.getName()).getGearLevel()) + "|3");
                     playa.getInventory().setItemInHand(null);
                     plugin.getRangedPlayerMap().get(playa.getName()).setSheepBowReadiness(false);
                     plugin.giveCooldown(playa, "woof", "ranged", 3);
                  }
                  // Blind arrow
                  else if(playa.getLevel() >= 7){
                     plugin.getProjMap().put(event.getProjectile(),
                           Integer.toString(plugin.getRangedPlayerMap().get(playa.getName()).getGearLevel()) + "|4");
                     playa.getInventory().setItemInHand(null);
                     plugin.getRangedPlayerMap().get(playa.getName()).setBlindBowReadiness(false);
                     plugin.giveCooldown(playa, "ICANTSEE", "ranged", 3);
                  }
                  // Normal arrow
                  else{
                     plugin.getProjMap().put(event.getProjectile(),
                           Integer.toString(plugin.getRangedPlayerMap().get(playa.getName()).getGearLevel()) + "|1");
                  }
               }
               // Normal arrow
               else if(playa.getInventory().getHeldItemSlot() > 1){
                  plugin.getProjMap().put(event.getProjectile(),
                        Integer.toString(plugin.getRangedPlayerMap().get(playa.getName()).getGearLevel()) + "|1");
               }
               if(playa.getInventory().getItem(9).getAmount() < 2)
                  playa.getInventory().getItem(9).setAmount(10);
            }
         }
      }
   }
   
   @EventHandler
   public void onEggThrow(PlayerEggThrowEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         event.setHatching(false);
         // Determining explosion chance by egg skill
         if(plugin.getRangedPlayerMap().get(event.getPlayer().getName()) != null
               && event.getPlayer().getLevel() >= 10){
            int egg = plugin.getRangedPlayerMap().get(event.getPlayer().getName()).getGearLevel();
            if(plugin.probabilityRoll(5 * egg)){
               event.getEgg().getLocation().getWorld().createExplosion(event.getEgg().getLocation(), 1F, false);
            }
         }
      }
   }
   
   @EventHandler
   public void onProjectileHit(ProjectileHitEvent event){
      if(plugin.getWorldsMap().containsKey(event.getEntity().getWorld().getName())){
         // Projectile hitting something
         Entity ent = event.getEntity();
         if(plugin.getProjMap().get(ent) != null){
            plugin.getProjMap().put(ent, plugin.getProjMap().get(ent) + "grounded");
            return;
         }
         /*else if(ent instanceof CraftSmallFireball)
            return;*/
      }
   }
}
