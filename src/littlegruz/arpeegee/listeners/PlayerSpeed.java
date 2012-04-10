package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerSpeed implements Listener{
   @SuppressWarnings("unused")
   private ArpeegeeMain plugin;
   
   public PlayerSpeed(ArpeegeeMain instance){
      plugin = instance;
   }
   
   // It don't work!
   @EventHandler
   public void onPlayerSprinting(PlayerMoveEvent event){
      if(event.getPlayer().isSprinting()){
         event.getPlayer().sendMessage("Sprint");
         event.getPlayer().setVelocity(event.getPlayer().getVelocity().multiply(10.0));
      }
   }
}
