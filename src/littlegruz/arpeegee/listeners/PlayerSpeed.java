package littlegruz.arpeegee.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerSpeed implements Listener{

   @EventHandler
   public void onPlayerSprinting(PlayerMoveEvent event){
      if(event.getPlayer().isSprinting()){
         // TODO Only happen when wearing certain armour
         if(!event.getPlayer().hasPotionEffect(PotionEffectType.SPEED))
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
      }
   }
}
