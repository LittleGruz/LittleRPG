package littlegruz.arpeegee.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnemyDeath implements Listener{
   @EventHandler
   public void onEnemyDeath(EntityDeathEvent event){
      if(event.getEntity() instanceof Animals
            || event.getEntity() instanceof Monster){
         float add, exp;
         
         //TODO Determine base exp values for creatures here
         exp = (float) 0.05;
         
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
