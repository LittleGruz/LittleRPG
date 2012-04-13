package littlegruz.arpeegee.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;
import littlegruz.arpeegee.entities.RPGSubClass;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
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

/* This class contains melee damage methods...apart from arrow damage and heal.*/
public class EntityDamageEntity implements Listener {
   private ArpeegeeMain plugin;
   
   public EntityDamageEntity(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
      plugin.getPlayerMap().put(event.getPlayer().getName(), new RPGPlayer(event.getPlayer().getName(), "Ranged", new RPGSubClass("Archer",1,0,0,1,0)));
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
         // Damaged by a diamond sword (crit sword)
         else if(playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0){
            int blade, crit, level;
            
            event.setCancelled(true);
            blade = (int) plugin.getPlayerMap().get(playa.getName()).getSubClassObject().getBlade();
            level = (int) plugin.getPlayerMap().get(playa.getName()).getLevel();
            
            /* If player is in Berserk mode, attack has an increased chance of
             * crit (double damage) otherwise the crit is (5 * blade skill)%*/
            if(plugin.getBerserkMap().get(playa.getName()) != null){
               if(plugin.probabilityRoll(5 * blade + level))
                  crit = 2;
               else
                  crit = 1;
               victim.damage((blade + (level / 2)) * crit);
            }
            else{
               if(plugin.probabilityRoll(5 * (blade)))
                  crit = 2;
               else
                  crit = 1;
               victim.damage(blade * crit);
               plugin.getPlayerMap().get(playa.getName()).addRage(5);
            }
            if(crit == 2)
               playa.sendMessage("*crit*");
            
            playa.getItemInHand().setDurability((short) 0);
         }
         // Damaged by an iron sword (block sword)
         else if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
            int blade;
            
            event.setCancelled(true);
            blade = (int) plugin.getPlayerMap().get(playa.getName()).getSubClassObject().getBlade();

            victim.damage(blade);

            if(plugin.getBerserkMap().get(playa.getName()) == null)
               plugin.getPlayerMap().get(playa.getName()).addRage(5);
            
            playa.getItemInHand().setDurability((short) 0);
            
         }
      }
      // Player blocking an attack
      else if(event.getEntity() instanceof Player){
         Player playa = (Player) event.getEntity();
         if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
            int block;
            
            block = (int) plugin.getPlayerMap().get(playa.getName()).getSubClassObject().getBlock();
            if(plugin.probabilityRoll(5 * block)){
               event.setCancelled(true);
               playa.sendMessage("*blocked*");
            }
         }
      }
      // Player arrow hit
      else if(event.getDamager() instanceof Arrow){
         // Check that it came from a player
         if(plugin.getProjMap().get(event.getDamager()) != null){
            //Location loc;
            LivingEntity le;
            ArrayList<Entity> ent = new ArrayList<Entity>();
            int arch, i;
            
            /* Since cancelling the event causes the arrow to bounce of, it
             * gets removed manually */
            event.setCancelled(true);
            event.getDamager().remove();

            le = (LivingEntity) event.getEntity();
            //loc = le.getLocation();
            
            arch = (int) Double.parseDouble(plugin.getProjMap().get(event.getDamager()).replace("grounded", ""));

            // If crit, do double damage
            if(plugin.probabilityRoll(5 * arch)){
               //loc.getWorld().strikeLightningEffect(loc);
               le.damage(1 * arch * 2);
            }
            else
               le.damage(1 * arch);
            
            // Remove all arrows that have hit the ground from hashmap
            // The removal is separate to stop concurrency issues
            Iterator<Map.Entry<Entity, String>> it = plugin.getProjMap().entrySet().iterator();
            while(it.hasNext()){
               Entry<Entity, String> arrow = it.next();
               if(arrow.getValue().contains("grounded"))
                  ent.add(arrow.getKey());
            }
            for(i = ent.size() - 1; ent.size() > 0; i--){
               plugin.getProjMap().remove(ent.get(i));
               ent.remove(i);
            }
         }
      }
   }
   
   private void healSpell(Player playa, LivingEntity fortunate, int adv){
      int spell = (int) plugin.getPlayerMap().get(playa.getName()).getSubClassObject().getSpell();
      if(fortunate instanceof Animals){
         playa.playEffect(fortunate.getLocation(), Effect.SMOKE, 1); //Change when Pig is changed to Player
         if(fortunate.getHealth() + (spell * adv) > fortunate.getMaxHealth())
            fortunate.setHealth(fortunate.getMaxHealth());
         else
            fortunate.setHealth(fortunate.getHealth() + (spell * adv));
         playa.sendMessage("Healed");
      }
      // If player heals a zombie, then deal damage instead
      else if(fortunate instanceof Zombie){
         fortunate.playEffect(EntityEffect.HURT);
         fortunate.damage(spell * adv);
         playa.sendMessage("Burn zombie!");
      }
   }
}
