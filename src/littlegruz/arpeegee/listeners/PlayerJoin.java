package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.ChatColor;
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
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getUID().toString())){
         Player playa = event.getPlayer();
         if(plugin.getMeleePlayerMap().get(event.getPlayer().getName()) == null
               && plugin.getRangedPlayerMap().get(event.getPlayer().getName()) == null
               && plugin.getMagicPlayerMap().get(event.getPlayer().getName()) == null){
            event.setJoinMessage(ChatColor.RED + "Welcome, " + playa.getName() + ", to LittleRPG.");
            
            delayMessage(playa, ChatColor.YELLOW + "Before you begin your adventure, you should pick a class.", 5);
            delayMessage(playa, ChatColor.GREEN + "If you are one that desires to get close enough to taunt their prey, the melee class would be most suitable. Type: '/ichoosemelee'", 150);
            delayMessage(playa, ChatColor.BLUE + "If you would rather pick enemies off from a perch where they can not hit you, the ranged class is most appropriate. Type: '/ichooseranged'", 350);
            delayMessage(playa, ChatColor.LIGHT_PURPLE + "But if spewing arcane energies from your fingertips appeals  to you best, then the magic class is your best bet. Type: '/ichoosemagic'", 500);
         }
         else{
            event.setJoinMessage(ChatColor.RED + "Welcome back, brave adventurer.");
            if(plugin.getMeleePlayerMap().get(event.getPlayer().getName()) != null)
               event.getPlayer().setLevel(plugin.getMeleePlayerMap().get(event.getPlayer().getName()).getLevel());
            else if(plugin.getRangedPlayerMap().get(event.getPlayer().getName()) != null)
               event.getPlayer().setLevel(plugin.getRangedPlayerMap().get(event.getPlayer().getName()).getLevel());
            else if(plugin.getMagicPlayerMap().get(event.getPlayer().getName()) != null)
               event.getPlayer().setLevel(plugin.getMagicPlayerMap().get(event.getPlayer().getName()).getLevel());
         }
      }
   }
   
   private void delayMessage(final Player playa, final String msg, long delay){
      plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
            playa.sendMessage(msg);
         }
     }, delay);
   }
}
