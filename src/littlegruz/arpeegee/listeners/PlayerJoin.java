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
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         Player playa = event.getPlayer();
         if(plugin.getRPGPlayer(event.getPlayer().getName()) == null){
            if(!plugin.isSpoutEnabled()){
               event.setJoinMessage(plugin.getDialogueMap().get("intro"));
            }
            else{
               plugin.getGUI().attachIntro(playa);
            }
            
            // Just in case they have levels/experience
            setRPGLevel(event.getPlayer());
            event.getPlayer().setExp(0);
         }
         else{
            event.setJoinMessage(plugin.getDialogueMap().get("return"));
            setRPGLevel(event.getPlayer());
         }
      }
   }
   
   private void setRPGLevel(Player playa){
      if(plugin.getMeleePlayerMap().get(playa.getName()) != null)
         playa.setLevel(plugin.getMeleePlayerMap().get(playa.getName()).getLevel());
      else if(plugin.getRangedPlayerMap().get(playa.getName()) != null)
         playa.setLevel(plugin.getRangedPlayerMap().get(playa.getName()).getLevel());
      else if(plugin.getMagicPlayerMap().get(playa.getName()) != null)
         playa.setLevel(plugin.getMagicPlayerMap().get(playa.getName()).getLevel());
   }
}
