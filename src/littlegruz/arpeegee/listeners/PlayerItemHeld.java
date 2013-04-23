package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMeleePlayer;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerItemHeld implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerItemHeld(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerItemHeld(PlayerItemHeldEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         Player playa = event.getPlayer();
         playa.sendMessage("o hai");

         if(plugin.getMeleePlayerMap().get(playa.getName()) != null){
            RPGMeleePlayer rpgmp = plugin.getMeleePlayerMap().get(playa.getName());
            if(event.getPreviousSlot() == 2
                  && event.getNewSlot() == 0
                  && playa.getItemInHand().getType().compareTo(Material.CARROT_ITEM) == 0){
               rpgmp.setOnHit(1);
               playa.sendMessage("Next physical attack will silence");
            }
            else if(event.getPreviousSlot() == 3
                  && event.getNewSlot() == 0
                  && playa.getItemInHand().getType().compareTo(Material.PUMPKIN_PIE) == 0){
               rpgmp.setOnHit(2);
               playa.sendMessage("Next physical attack will imobilise");
            }
            else if(event.getPreviousSlot() == 0
                  && event.getNewSlot() != 2
                  && event.getNewSlot() != 3
                  && rpgmp.getOnHit() != 0){
               rpgmp.setOnHit(0);
               playa.sendMessage("Special attack canceled");
            }
         }
      }
   }
}
