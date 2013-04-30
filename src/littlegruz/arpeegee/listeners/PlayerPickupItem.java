package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItem implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerPickupItem(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onPlayerPickupItem(PlayerPickupItemEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         /* Prevent player from picking up items if confused*/
         if(plugin.getConfMap().get(event.getPlayer().getUniqueId()) != null){
            event.setCancelled(true);
         }
      }
   }
}
