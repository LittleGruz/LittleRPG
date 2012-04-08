package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
   private ArpeegeeMain plugin;
   
   public PlayerListener(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
      plugin.getLogger().info("Point0");
      plugin.getPlayerMap().put(event.getPlayer().getName(), new RPGPlayer(event.getPlayer().getName(), "Warrior", "Minion"));
      plugin.getLogger().info("Point1");
      plugin.getGUI().chooseClass(event.getPlayer());
   }
}
