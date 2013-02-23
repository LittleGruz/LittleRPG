package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

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
   public void onPlayerSprinting(PlayerMoveEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         if(event.getPlayer().isSprinting()){
            // Give all rangers extra boost when sprinting
            if(!event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)
                  && plugin.getRangedPlayerMap().containsKey(event.getPlayer().getName()))
               event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
         }
      }
   }
}
