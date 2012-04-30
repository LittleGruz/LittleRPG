package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerJoin(ArpeegeeMain instance){
      plugin = instance;
   }


   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
      Player playa = event.getPlayer();
      if(plugin.getMeleePlayerMap().get(event.getPlayer().getName()) == null
            && plugin.getRangedPlayerMap().get(event.getPlayer().getName()) == null
            && plugin.getMagicPlayerMap().get(event.getPlayer().getName()) == null){
         playa.sendMessage("Welcome, " + playa.getName() + ", to LittleRPG.");
         playa.sendMessage("Before you begin your adventure, you should pick a class.");
         delayMessage(playa, "For those who wish to be able to converse with their prey, the melee class would be suitable. Type: '/iammelee'", 150);
         delayMessage(playa, "If you would rather pick enemies off from a perch where they can not hit you, the ranged class is most appropriate. Type: '/iamranged'", 300);
         delayMessage(playa, "But if spewing arcane energies from your fingertips appeals to you, the magic class is your best bet. Type: '/iammagic'", 450);
      }
      else
         playa.sendMessage("Welcome back, brave adventurer.");
   }
   
   private void delayMessage(final Player playa, final String msg, long delay){
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            playa.sendMessage(msg);
         }
     }, delay);
   }
}
