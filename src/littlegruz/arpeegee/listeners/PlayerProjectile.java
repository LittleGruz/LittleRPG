package littlegruz.arpeegee.listeners;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

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
         if(event.getProjectile() instanceof Arrow)
            playa.sendMessage(event.getProjectile().toString());
         plugin.getProjMap().put(event.getProjectile(), "arrow");
         
      }
   }
   
   @EventHandler
   public void onEggThrow(PlayerEggThrowEvent event){
      event.setHatching(false);
   }

   // Not a great solution for shrinking down the arrow hashmap, but it will do for now
   @EventHandler
   public void clearHash(PlayerItemHeldEvent event){
      Iterator<Map.Entry<Entity, String>> it = plugin.getProjMap().entrySet().iterator();
      try{
         while(it.hasNext()){
            Entry<Entity, String> arrow = it.next();
            if(arrow.getValue().compareTo("grounded") == 0)
               plugin.getProjMap().remove(arrow.getKey());
         }
      }catch(ConcurrentModificationException e){}
   }
   
   @EventHandler
   public void onProjectileHit(ProjectileHitEvent event){
      Entity ent = event.getEntity();
      
      if(plugin.getProjMap().get(ent) != null){
         plugin.getProjMap().put(ent, "grounded");
         return;
      }
      
      //TODO Explodey chances
      if((int) plugin.getChance(15) == 2 && ent instanceof Egg){
         ent.getLocation().getWorld().createExplosion(ent.getLocation(), 1F, false);
      }
   }
}
