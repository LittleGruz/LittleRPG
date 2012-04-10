package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class PlayerSpeed implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerSpeed(ArpeegeeMain instance){
      plugin = instance;
   }
   
   // It don't work!
   @EventHandler
   public void onPlayerMoving(PlayerToggleSprintEvent event){
      event.getPlayer().setVelocity(event.getPlayer().getVelocity().multiply(200.0));
      event.getPlayer().sendMessage("Sprint");
   }
}
