package littlegruz.arpeegee.listeners;

import org.bukkit.entity.Egg;
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
      if(event.getEntity() instanceof Player){
         Player playa = (Player) event.getEntity();

         //event.setProjectile(playa.getWorld().spawnCreature(playa.getLocation(), EntityType.SHEEP));
         if(plugin.getRangedPlayerMap().get(playa.getName()) != null)
            plugin.getProjMap().put(event.getProjectile(),
                  Double.toString(plugin.getRangedPlayerMap().get(playa.getName()).getSubClassObject().getArch()));
         
      }
   }
   
   @EventHandler
   public void onEggThrow(PlayerEggThrowEvent event){
      // Determining explosion chance by egg skill
      if(plugin.getRangedPlayerMap().get(event.getPlayer().getName()) != null){
         event.setHatching(false);
         int egg = (int) plugin.getRangedPlayerMap().get(event.getPlayer().getName()).getSubClassObject().getEgg();
         if(plugin.probabilityRoll(5 * egg)){
            event.getEgg().getLocation().getWorld().createExplosion(event.getEgg().getLocation(), 1F, false);
         }
      }
   }
   
   @EventHandler
   public void onProjectileHit(ProjectileHitEvent event){
      // Projectile hitting something
      Entity ent = event.getEntity();
      if(plugin.getProjMap().get(ent) != null && !(ent instanceof Egg)){
         plugin.getProjMap().put(ent, plugin.getProjMap().get(ent) + "grounded");
         return;
      }
      /*else if(ent instanceof CraftSmallFireball)
         return;*/
   }
}
