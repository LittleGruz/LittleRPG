package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMeleePlayer;
import littlegruz.arpeegee.entities.RPGRangedPlayer;

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

         if(plugin.getMeleePlayerMap().get(playa.getName()) != null){
            RPGMeleePlayer rpgmp = plugin.getMeleePlayerMap().get(playa.getName());
            if(event.getPreviousSlot() == 2
                  && event.getNewSlot() == 0
                  && playa.getItemInHand().getType().compareTo(Material.CARROT_ITEM) == 0
                  && playa.getLevel() >= 11){
               rpgmp.setOnHit(1);
               playa.sendMessage("Next physical attack will silence");
            }
            else if(event.getPreviousSlot() == 3
                  && event.getNewSlot() == 0
                  && playa.getItemInHand().getType().compareTo(Material.PUMPKIN_PIE) == 0
                  && playa.getLevel() >= 16){
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
         else if(plugin.getRangedPlayerMap().get(playa.getName()) != null){
            RPGRangedPlayer rpgr = plugin.getRangedPlayerMap().get(playa.getName());
            
            if(playa.getItemInHand().getType().compareTo(Material.INK_SACK) == 0){
               if(event.getPreviousSlot() == 1
                     && event.getNewSlot() == 0
                     && playa.getItemInHand().getDurability() == 12
                     && playa.getLevel() >= 6){
                  rpgr.setOnHit(1);
                  playa.sendMessage("Slowing arrow set");
               }
               else if(event.getPreviousSlot() == 2
                     && event.getNewSlot() == 0
                           && playa.getItemInHand().getDurability() == 0
                     && playa.getLevel() >= 11){
                  rpgr.setOnHit(2);
                  playa.sendMessage("Blinding arrow set");
               }
               else if(event.getPreviousSlot() == 4
                     && event.getNewSlot() == 0
                           && playa.getItemInHand().getDurability() == 15
                     && playa.getLevel() >= 20){
                  rpgr.setOnHit(3);
                  playa.sendMessage("Sheep arrow set");
               }
               else if(event.getPreviousSlot() == 0
                     && event.getNewSlot() != 1
                     && event.getNewSlot() != 2
                     && event.getNewSlot() != 4
                     && rpgr.getOnHit() != 0){
                  rpgr.setOnHit(0);
                  playa.sendMessage("Normal arrow");
               }
            }else if(event.getPreviousSlot() == 0
                  && rpgr.getOnHit() != 0){
               rpgr.setOnHit(0);
               playa.sendMessage("Normal arrow");
            }
         }
      }
   }
}
