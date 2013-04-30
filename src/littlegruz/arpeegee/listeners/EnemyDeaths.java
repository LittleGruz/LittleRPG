package littlegruz.arpeegee.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMagicPlayer;

import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EnemyDeaths implements Listener{
   private ArpeegeeMain plugin;
   
   public EnemyDeaths(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onEnemyDeath(EntityDeathEvent event){
      if(plugin.getWorldsMap().containsKey(event.getEntity().getWorld().getName())){
         if(event.getEntity() instanceof Animals
               || event.getEntity() instanceof Monster){
            int exp;
            
            event.setDroppedExp(0);

            /* Remove the summoned mage sheep from list*/
            if(event.getEntity() instanceof Sheep){
               Iterator<Map.Entry<String, RPGMagicPlayer>> it = plugin.getMagicPlayerMap().entrySet().iterator();
               while(it.hasNext()){
                  it.next().getValue().sheepSearchAndDestroy(event.getEntity().getUniqueId(), true);
               }
            }
            
            //Base exp values for creatures here
            exp = 15;
            if(event.getEntity() instanceof Animals){
               exp = 5;
               itemDrops(event.getDrops(), 0);
            }
            else if(event.getEntity() instanceof Squid){
               exp = 6;
               itemDrops(event.getDrops(), 0);
            }
            else if(event.getEntity() instanceof PigZombie){
               exp = 25;
               itemDrops(event.getDrops(), 5);
            }
            else if(event.getEntity() instanceof Zombie){
               exp = 15;
               itemDrops(event.getDrops(), 3);
            }
            else if(event.getEntity() instanceof Silverfish){
               exp = 15;
               itemDrops(event.getDrops(), 5);
            }
            else if(event.getEntity() instanceof CaveSpider){
               exp = 30;
               itemDrops(event.getDrops(), 7);
            }
            else if(event.getEntity() instanceof Spider){
               exp = 20;
               itemDrops(event.getDrops(), 4);
            }
            else if(event.getEntity() instanceof Skeleton){
               exp = 17;
               itemDrops(event.getDrops(), 5);
            }
            else if(event.getEntity() instanceof Ghast){
               exp = 35;
               itemDrops(event.getDrops(), 7);
            }
            else if(event.getEntity() instanceof MagmaCube){
               //if(((MagmaCube) event.getEntity()).getSize() > 1)
               exp = 17;
               itemDrops(event.getDrops(), 8);
            }
            else if(event.getEntity() instanceof Slime){
               //if(((Slime) event.getEntity()).getSize() > 1)
               exp = 15;
               itemDrops(event.getDrops(), 8);
            }
            else if(event.getEntity() instanceof Creeper){
               exp = 20;
               itemDrops(event.getDrops(), 5);
            }
            else if(event.getEntity() instanceof Enderman){
               exp = 25;
               itemDrops(event.getDrops(), 7);
            }
            else if(event.getEntity() instanceof Blaze){
               exp = 30;
               itemDrops(event.getDrops(), 7);
            }
            else if(event.getEntity() instanceof EnderDragon){
               exp = 150;
               itemDrops(event.getDrops(), 100);
            }
            
            for(Entity e : event.getEntity().getNearbyEntities(15, 15, 15)){
               if (e instanceof Player)
                  plugin.giveExp((Player) e, exp);
            }
         }
      }
   }
   
   private void itemDrops(List<ItemStack> drops, int baseChance){
      int totalDrops, randNum;
      Random rand = new Random();

      //drops.removeAll(drops);
      totalDrops = 0;
      
      if(plugin.probabilityRoll(baseChance)){
         totalDrops = 1;
         // Chance for a double drop
         if(plugin.probabilityRoll(3))
            totalDrops++;
      }
      // Increased total drops for big bosses
      if(baseChance == 100)
         totalDrops += 4;
      
      // Set the drops
      while(totalDrops > 0){
         randNum = rand.nextInt() % 100;
         if(randNum < 10)
            drops.add(new ItemStack(Material.GOLD_CHESTPLATE,1));
         else if(randNum < 20)
            drops.add(new ItemStack(Material.GOLD_HELMET,1));
         else if(randNum < 30)
            drops.add(new ItemStack(Material.LEATHER_BOOTS,1));
         else if(randNum < 40)
            drops.add(new ItemStack(Material.LEATHER_CHESTPLATE,1));
         else if(randNum < 50)
            drops.add(new ItemStack(Material.LEATHER_HELMET,1));
         else if(randNum < 60)
            drops.add(new ItemStack(Material.LEATHER_LEGGINGS,1));
         else if(randNum < 70)
            drops.add(new ItemStack(Material.IRON_BOOTS,1));
         else if(randNum < 80)
            drops.add(new ItemStack(Material.IRON_CHESTPLATE,1));
         else if(randNum < 90)
            drops.add(new ItemStack(Material.IRON_HELMET,1));
         else if(randNum < 100)
            drops.add(new ItemStack(Material.IRON_LEGGINGS,1));
         totalDrops--;
      }
   }
}
