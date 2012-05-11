package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnemyDeaths implements Listener{
   private ArpeegeeMain plugin;
   
   public EnemyDeaths(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onEnemyDeath(EntityDeathEvent event){
      if(plugin.getWorldsMap().containsKey(event.getEntity().getWorld().getUID().toString())){
         if(event.getEntity() instanceof Animals
               || event.getEntity() instanceof Monster){
            float add, exp;
            
            event.setDroppedExp(0);
            
            //Base exp values for creatures here
            exp = (float) 0.15;
            if(event.getEntity() instanceof Animals)
               exp = (float) 0.05;
            else if(event.getEntity() instanceof PigZombie)
               exp = (float) 0.25;
            else if(event.getEntity() instanceof Zombie)
               exp = (float) 0.15;
            else if(event.getEntity() instanceof Silverfish)
               exp = (float) 0.15;
            else if(event.getEntity() instanceof CaveSpider)
               exp = (float) 0.30;
            else if(event.getEntity() instanceof Spider)
               exp = (float) 0.15;
            else if(event.getEntity() instanceof Skeleton)
               exp = (float) 0.20;
            else if(event.getEntity() instanceof Ghast)
               exp = (float) 0.30;
            else if(event.getEntity() instanceof MagmaCube){
               //if(((MagmaCube) event.getEntity()).getSize() > 1)
               exp = (float) 0.17;
            }
            else if(event.getEntity() instanceof Slime){
               //if(((Slime) event.getEntity()).getSize() > 1)
               exp = (float) 0.15;
            }
            else if(event.getEntity() instanceof Creeper)
               exp = (float) 0.20;
            else if(event.getEntity() instanceof Enderman)
               exp = (float) 0.25;
            else if(event.getEntity() instanceof Blaze)
               exp = (float) 0.25;
            else if(event.getEntity() instanceof EnderDragon)
               exp = (float) 1.50;
            
            for(Entity e : event.getEntity().getNearbyEntities(20, 20, 20)) {
               if (e instanceof Player) {
                  Player p = (Player) e;
                  
                  /* If max exp is represented as 100, then the player is given 5
                   * exp points which is divided by their level and 0.33. This will
                   * make it longer to level as the player progresses*/
                  add = (float) (exp/(p.getLevel() * 0.33));
                  if(p.getExp() + add > 1){
                     add = (add + p.getExp()) - 1;
                     p.setLevel(p.getLevel() + 1);
                     p.setExp(add);
                  }
                  else
                     p.setExp(p.getExp() + add);
               }
            }
         }
      }
   }
}
