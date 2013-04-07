package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerSpeed implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerSpeed(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onPlayerMovement(PlayerMoveEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         if(plugin.getRangedPlayerMap().get(event.getPlayer().getName()) != null){
            // Cancel player movement cancel if imobilised
            if(!plugin.getRangedPlayerMap().get(event.getPlayer().getName()).canMove()){
               event.setTo(stopMovement(event.getFrom(), event.getTo()));
               return;
            }
            
            if(event.getPlayer().isSprinting()){
               // Give all rangers extra boost when sprinting
               if(!event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)
                     && plugin.getRangedPlayerMap().containsKey(event.getPlayer().getName()))
                  event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
            }
         }
         else if(plugin.getMagicPlayerMap().get(event.getPlayer().getName()) != null
               && !plugin.getMagicPlayerMap().get(event.getPlayer().getName()).canMove()){
            event.setTo(stopMovement(event.getFrom(), event.getTo()));
            return;
         }
         else if(plugin.getMeleePlayerMap().get(event.getPlayer().getName()) != null
               && !plugin.getMeleePlayerMap().get(event.getPlayer().getName()).canMove()){
            event.setTo(stopMovement(event.getFrom(), event.getTo()));
            return;
         }
      }
   }
   
   private Location stopMovement(Location from, Location to){
      Location loc;
      
      loc = to.clone();

      loc.setX(from.getX());
      loc.setY(from.getY());
      loc.setZ(from.getZ());
      
      
      return loc;
   }
}
