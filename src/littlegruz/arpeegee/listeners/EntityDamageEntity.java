package littlegruz.arpeegee.listeners;

import java.util.ArrayList;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EntityDamageEntity implements Listener {
   private ArpeegeeMain plugin;
   
   public EntityDamageEntity(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
      plugin.getPlayerMap().put(event.getPlayer().getName(), new RPGPlayer(event.getPlayer().getName(), "Warrior", "Minion"));
   }

   @EventHandler
   public void onEntityDamageEntity(EntityDamageByEntityEvent event){
      if(event.getDamager() instanceof Player
            && event.getEntity() instanceof LivingEntity){
         Player playa = (Player) event.getDamager();
         LivingEntity victim = (LivingEntity) event.getEntity();
         
         // Heal spell
         if(playa.getItemInHand().getData().toString().contains("WHITE DYE")){
            event.setCancelled(true);
            healSpell(playa, victim, 1);
         }
         // Advanced heal spell
         else if(playa.getItemInHand().getData().toString().contains("BONE")){
            event.setCancelled(true);
            healSpell(playa, victim, 2);
         }
         // Lightning (single target) spell
         else if(playa.getItemInHand().getData().toString().contains("YELLOW DYE")){
            Location loc = event.getEntity().getLocation();
            
            event.setCancelled(true);
            
            loc.setY(loc.getY() + 1);
            loc.getWorld().strikeLightningEffect(loc);
            //TODO Lightning damage
            victim.damage(1);
            playa.sendMessage("*Zap*");
         }
         // Lightning (area) spell
         else if(playa.getItemInHand().getType().compareTo(Material.BLAZE_ROD) == 0){
            Location loc = event.getEntity().getLocation();
            final ArrayList<LivingEntity> nearEnemies = new ArrayList<LivingEntity>();
            
            event.setCancelled(true);
            
            loc.setY(loc.getY() + 1);
            loc.getWorld().strikeLightningEffect(loc);
            //TODO Lightning damage
            victim.damage(1);
            
            // Place all nearby enemies into an array for the area lightning effect
            nearEnemies.add(victim);
            for(Entity e : event.getEntity().getNearbyEntities(5, 5, 5)) {
               if (e instanceof LivingEntity) {
                  nearEnemies.add((LivingEntity)e);
               }
            }
            playa.sendMessage("*Zap zap zap*");
            
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
               public void run() {
                  for(LivingEntity e : nearEnemies) {
                     Location enemyLoc = e.getLocation();
                     enemyLoc.setY(enemyLoc.getY() + 1);
                     enemyLoc.getWorld().strikeLightningEffect(enemyLoc);
                     //TODO Lightning damage
                     e.damage(0);
                  }
               }
           }, 20L);
         }
         // Provides a base 5% critical hit chance
         else if(playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0){
            int crit;
            
            event.setCancelled(true);
            
            /* If player is in Berserk mode, attack has a base 10% chance of
             * crit (double damage) otherwise the base crit is 5%*/
            //TODO Sword damage and crit addition
            if(plugin.getBerserkMap().get(playa.getName()) != null){
               crit = (int) plugin.getChance(10);
               victim.damage(1 * crit);
            }
            else{
               crit = (int) plugin.getChance(5);
               victim.damage(0 * crit);
               //TODO Rage gain
               plugin.getPlayerMap().get(playa.getName()).addRage(25);
               victim.remove();
            }
            if(crit == 2)
               playa.sendMessage("*crit*");
            
            playa.getItemInHand().setDurability((short) 0);
         }
         // Provides a base 10% dodge chance
         else if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
            event.setCancelled(true);

            //TODO Sword damage
            victim.damage(0);

            //TODO Rage gain
            if(plugin.getBerserkMap().get(playa.getName()) == null)
               plugin.getPlayerMap().get(playa.getName()).addRage(25);
            
            playa.getItemInHand().setDurability((short) 0);
            
         }
      }
      // Player dodging
      else if(event.getEntity() instanceof Player){
         Player playa = (Player) event.getEntity();
         if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
            //TODO Dodge chance
            if((int) plugin.getChance(5) == 1){
               event.setCancelled(true);
               playa.sendMessage("*dodged*");
            }
         }
      }
      // Player arrow hit
      else if(event.getDamager() instanceof Arrow){
         // Check that it came from a player
         if(plugin.getProjMap().get(event.getDamager()) != null){
            //Location loc;
            LivingEntity le;
            
            /* Since cancelling the event causes the arrow to bounce of, it
             * gets removed manually */
            event.setCancelled(true);
            event.getDamager().remove();

            le = (LivingEntity) event.getEntity();
            //loc = le.getLocation();
            
            //TODO Arrow damage
            le.damage(0);
            // If crit, do additional damage
            if(plugin.getChance(15) == 2){
               //loc.getWorld().strikeLightningEffect(loc);
               le.damage(0);
            }
         }
      }
   }
   
   private void healSpell(Player playa, LivingEntity fortunate, int adv){
      if(fortunate instanceof Animals){
         playa.sendMessage("Healed");
         playa.playEffect(fortunate.getLocation(), Effect.SMOKE, 1); //Change when Pig is changed to Player
         //TODO Player heal
         playa.sendMessage(Integer.toString(fortunate.getMaxHealth()));
         if(fortunate.getHealth() + (1 * adv) > fortunate.getMaxHealth())
            fortunate.setHealth(fortunate.getMaxHealth());
         else
            fortunate.setHealth(fortunate.getHealth() + (1 * adv));
      }
      // If player heals a zombie, then deal damage instead
      else if(fortunate instanceof Zombie){
         fortunate.playEffect(EntityEffect.HURT);
         //TODO Zombie damage
         fortunate.damage(1 * adv);
         playa.sendMessage("Burn zombie!");
      }
   }
}
