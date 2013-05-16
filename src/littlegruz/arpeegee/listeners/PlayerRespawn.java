package littlegruz.arpeegee.listeners;

import java.util.ArrayList;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerRespawn implements Listener{
   private ArpeegeeMain plugin;
   
   public PlayerRespawn(ArpeegeeMain instance){
      plugin = instance;
   }
   
   
   @EventHandler
   public void onPlayerRespawn(PlayerRespawnEvent event){
      if(plugin.getWorldsMap().containsKey(event.getPlayer().getWorld().getName())){
         // Restore weapons and levels for the melee class
         if(plugin.getMeleePlayerMap().get(event.getPlayer().getName()) != null){
            // Give player back their base weapon
            event.getPlayer().getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD,1));
         }
         // Restore weapons and levels for the ranged class
         else if(plugin.getRangedPlayerMap().get(event.getPlayer().getName()) != null){
            // Give player back their base weapon
            event.getPlayer().getInventory().setItem(0, new ItemStack(Material.BOW,1));
            event.getPlayer().getInventory().setItem(9, new ItemStack(Material.ARROW,10));
         }
         // Restore weapons and levels for the magic class
         else if(plugin.getMagicPlayerMap().get(event.getPlayer().getName()) != null){
            // Give player back their base skill
            // Lightning
            ItemStack is = new ItemStack(351,1);
            is.setDurability((short)11);
            event.getPlayer().getInventory().setItem(0, is);
         }
      }
   }

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event){
      if(plugin.getWorldsMap().containsKey(event.getEntity().getWorld().getName())){
         int i;
         boolean bowTaken, swordTaken, nonHairySackTaken, arrowTaken;
         ArrayList<ItemStack> removeList = new ArrayList<ItemStack>();
         
         event.setDroppedExp(0);
         event.setKeepLevel(true);
         
         /* Set up to only take one stack since player gets one back on respawn*/
         bowTaken = false;
         swordTaken = false;
         nonHairySackTaken = false;
         arrowTaken = false;
         
         for(ItemStack is : event.getDrops()){
            if(is.getType().compareTo(Material.INK_SACK) == 0
                  && is.getDurability() == 11 && !nonHairySackTaken){
               removeList.add(is);
               nonHairySackTaken = true;
            }
            else if(is.getType().compareTo(Material.WOOD_SWORD) == 0
                  && !swordTaken){
               removeList.add(is);
               swordTaken = true;
            }
            else if(is.getType().compareTo(Material.BOW) == 0
                  && !bowTaken){
               removeList.add(is);
               bowTaken = true;
            }
            else if(is.getType().compareTo(Material.ARROW) == 0
                  && !arrowTaken){
               removeList.add(is);
               arrowTaken = true;
            }
         }
         for(i = 0; i < removeList.size(); i++)
            event.getDrops().remove(removeList.get(i));
         removeList.clear();
         
         // Reset gear value
         if(plugin.getRPGPlayer(event.getEntity().getName()) != null)
            plugin.getRPGPlayer(event.getEntity().getName()).setGearLevel(0);
      }
   }
}
