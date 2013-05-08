package littlegruz.arpeegee.listeners;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerChat(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerChat(AsyncPlayerChatEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         RPGPlayer rpgp;
         String party;
         
         rpgp = plugin.getRPGPlayer(event.getPlayer().getName());
         
         if(rpgp != null && rpgp.getChat() == 1){
            party = rpgp.getParty();
            
            if(party.compareTo("none") != 0){
               Iterator<Map.Entry<String, String>> it = plugin.getPartyMap().get(party).getMembers().entrySet().iterator();
               String msg = ChatColor.YELLOW + "<" + event.getPlayer().getName() + "> " + event.getMessage();
               
               /* Send the message to everyone but the sender in the party*/
               while(it.hasNext()){
                  Entry<String, String> playa = it.next();
                  plugin.getServer().getPlayer(playa.getKey()).sendMessage(msg);
               }
               
               event.setCancelled(true);
            }
            else{
               event.getPlayer().sendMessage("You must join a party first");
               event.setCancelled(true);
            }
         }
      }
   }
}
