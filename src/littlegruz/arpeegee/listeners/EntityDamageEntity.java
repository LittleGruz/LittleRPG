package littlegruz.arpeegee.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMagicPlayer;
import littlegruz.arpeegee.entities.RPGMeleePlayer;
import littlegruz.arpeegee.entities.RPGSheep;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageEntity implements Listener {
   private ArpeegeeMain plugin;
   
   public EntityDamageEntity(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onEntityDamageEntity(EntityDamageByEntityEvent event){
      if(plugin.getWorldsMap().containsKey(event.getEntity().getWorld().getName())){
         // Fist bump!
         if(event.getEntity() instanceof Player
               && event.getDamager() instanceof Player){
            Player playa = (Player) event.getEntity();
            if(playa.getItemInHand().getTypeId() == 0){
               ((Player) event.getEntity()).sendMessage("*fist bumped by " + playa.getName() + "*");
               playa.sendMessage("*fist bump*");
            }
         }
         if(event.getDamager() instanceof Player
               && event.getEntity() instanceof LivingEntity){
            Player playa = (Player) event.getDamager();
            LivingEntity victim = (LivingEntity) event.getEntity();
            
            event.setDamage(1);
            
            // Heal spell
            if(playa.getItemInHand().getData().toString().contains("WHITE DYE")
                  && plugin.getMagicPlayerMap().get(playa.getName()) != null
                  && playa.getLevel() >= 3){
               if(!plugin.getMagicPlayerMap().get(playa.getName()).isHealReady()){
                  playa.sendMessage("Heal is still on cooldown");
                  return;
               }
               else{
                  ItemStack is = new ItemStack(351,1);
                  is.setDurability((short)15);
                  playa.getInventory().remove(is);
                  plugin.giveCooldown(playa, "heal", "magic", 7);
                  plugin.getMagicPlayerMap().get(playa.getName()).setHealReadiness(false);
               }
               
               // Normal heal/undead damage
               if(plugin.getBuildUpMap().get(playa.getName()) == null){
                  healSpell(playa, victim, 1);
                  plugin.getMagicPlayerMap().get(playa.getName()).addBuildUp(6);
               }
               // Advanced heal/undead damage
               else{
                  healSpell(playa, victim, 2);
                  plugin.getBuildUpMap().remove(playa.getName());
               }
            }
            // Damage by a sword by melee player
            else if(playa.getItemInHand().getType().toString().contains("SWORD")
                  && plugin.getMeleePlayerMap().get(playa.getName()) != null){
               int blade, crit;
               
               playa.sendMessage("Swing");
               
               // Check if the player can swing yet
               if(plugin.getMeleePlayerMap().get(playa.getName()).isSwordReady()){
                  plugin.giveCooldown(playa, "slash", "melee", 1);
                  plugin.getMeleePlayerMap().get(playa.getName()).setSwordReadiness(false);
               }
               else
                  return;
               
               // TODO change to be properly based off gear
               blade = plugin.getMeleePlayerMap().get(playa.getName()).getGearLevel();
               
               /* Crit chance 5% to 25%. Berserk mode adds 10%
                * Damage in berserk adds 1 to 3 damage*/
               if(plugin.getBerserkMap().get(playa.getName()) != null){
                  if(plugin.probabilityRoll(5 * blade + 10))
                     crit = 2;
                  else
                     crit = 1;
               }
               else{
                  if(plugin.probabilityRoll(5 * blade))
                     crit = 2;
                  else
                     crit = 1;
                  
                  plugin.getMeleePlayerMap().get(playa.getName()).addRage(5);
               }
               
               plugin.ohTheDamage(event, victim, blade * crit);
               
               if(crit == 2)
                  playa.sendMessage("*crit*");
               
               //playa.getItemInHand().setDurability((short) 0);
            }
            else if((playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0
                  || playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0
                  || playa.getItemInHand().getType().compareTo(Material.STONE_SWORD) == 0
                  || playa.getItemInHand().getType().compareTo(Material.WOOD_SWORD) == 0
                  || playa.getItemInHand().getType().compareTo(Material.BOW) == 0
                  || playa.getItemInHand().getType().compareTo(Material.AIR) == 0)
                  && (plugin.getMeleePlayerMap().get(playa.getName()) != null
                  || plugin.getMagicPlayerMap().get(playa.getName()) != null
                  || plugin.getRangedPlayerMap().get(playa.getName()) != null)){
               plugin.ohTheDamage(event, victim, 1);
            }
         }
         // TODO END OF PLAYER DAMAGE TO LIVINGENTITY
         // Player taking damage
         else if(event.getEntity() instanceof Player){
            double damage;
            Player playa = (Player) event.getEntity();
            
            // Melee player potentially adds to its bide
            if(plugin.getMeleePlayerMap().get(playa.getName()) != null){
               if(plugin.getBideMap().get(playa.getName()) != null){
                  plugin.meleeBide(playa, event.getDamage());
                  event.setDamage(0);
               }
               else if(plugin.getBerserkMap().get(playa.getName()) != null){
                  damage = event.getDamage() * 0.6;
                  event.setDamage((int)damage);
               }
               
               return;
            }
            
            if(playa.isBlocking()){
               
               damage = event.getDamage() * 0.9;
               
               // Stops smaller damages from being mostly ignored
               if(damage < event.getDamage() - 0.6){
                  event.setDamage((int)damage);
               }
            }
         }
         // Player arrow hit
         else if(event.getDamager() instanceof Arrow){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               int gear, type;
               String arrowData;
               StringTokenizer st;
               
               arrowData = plugin.getProjMap().get(event.getDamager()).replace("grounded", "");
               st = new StringTokenizer(arrowData, "|");
               
               gear = (int) Double.parseDouble(st.nextToken());
               type = Integer.parseInt(st.nextToken());
               
               // Type 2 is the blind arrow
               if(type == 2){
                  if(event.getEntity() instanceof Player){
                     if(plugin.getMagicPlayerMap().get(((Player) event.getEntity()).getName()) != null){
                        final RPGMagicPlayer rpgmp;
                        rpgmp = plugin.getMagicPlayerMap().get(((Player) event.getEntity()).getName());
                        
                        rpgmp.blindPlayer();
                        
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                           public void run(){
                              rpgmp.unblindPlayer();
                           }
                        }, gear * 20L);
                     }
                  }
                  else if(event.getEntity() instanceof Player){
                     if(plugin.getMeleePlayerMap().get(((Player) event.getEntity()).getName()) != null){
                        final RPGMeleePlayer rpgmp;
                        rpgmp = plugin.getMeleePlayerMap().get(((Player) event.getEntity()).getName());
                        
                        rpgmp.blindPlayer();
                        
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                           public void run(){
                              rpgmp.unblindPlayer();
                           }
                        }, gear * 20L);
                     }
                  }
               }
               
               // If crit do double damage. 0% to 30% chance TODO this chance has probably changed
               if(plugin.probabilityRoll(5 * (gear - 1))){
                  event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                  plugin.ohTheDamage(event, event.getEntity(), gear * 2);
               }
               else
                  plugin.ohTheDamage(event, event.getEntity(), gear);
               
               clearGroundedProjectiles();
            }
         }
         // Slow bow hit
         else if(event.getDamager() instanceof Snowball){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               int bow;
               
               bow = Integer.parseInt(plugin.getProjMap().get(event.getDamager()));
               
               plugin.getProjMap().remove(event.getDamager());
               // TODO add damage
               //bow = 4;
               
               event.setCancelled(true);
               
               if(event.getEntity() instanceof LivingEntity){
                  ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bow * 20, 2), true);
                  bow /= 2;
               }
               
               clearGroundedProjectiles();
            }
         }
         // Sheep bow hit
         else if(event.getDamager() instanceof SmallFireball){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               int bow;
               final Sheep primedSheep;
               
               bow = Integer.parseInt(plugin.getProjMap().get(event.getDamager()));
               
               plugin.getProjMap().remove(event.getDamager());
               
               // TODO add damage
               //bow = 3;
               if(event.getEntity() instanceof LivingEntity){
                  final LivingEntity le = (LivingEntity) event.getEntity();
                  event.setDamage(0);
                  
                  // Stop fire ticks later because doing it now wont do anything
                  plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                     public void run(){
                        le.setFireTicks(0);
                        le.setVelocity(le.getVelocity().multiply(0.5F));
                     }
                  }, 1L);
               }
               
               primedSheep = event.getDamager().getWorld().spawn(event.getDamager().getLocation(), RPGSheep.class);
               primedSheep.setHealth(bow);
               
               plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                  public void run(){
                     primedSheep.getLocation().getWorld().createExplosion(primedSheep.getLocation(), primedSheep.getHealth());
                     primedSheep.remove();
                  }
               }, 30L);

               clearGroundedProjectiles();
            }
         }
         // Fireball spell hit
         else if(event.getDamager() instanceof Fireball){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               int magic;
               char discharge;
               String ballData;
               StringTokenizer st;
               
               ballData = plugin.getProjMap().get(event.getDamager());
               st = new StringTokenizer(ballData, "|");
               
               magic = (int) Double.parseDouble(st.nextToken());
               discharge = st.nextToken().charAt(0);
               
               if(discharge == 'y')
                  magic = (int)(magic * 1.5);
               
               plugin.getProjMap().remove(event.getDamager());
               // TODO add damage
               
               clearGroundedProjectiles();
            }
         }
      }
   }
   
   private void healSpell(Player playa, LivingEntity fortunate, int adv){
      // TODO change to be properly based off gear
      int spell = plugin.getMagicPlayerMap().get(playa.getName()).getGearLevel();
      if(fortunate instanceof Player){
         playa.playEffect(fortunate.getLocation(), Effect.SMOKE, 1);
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
         playa.sendMessage("Undead damage!");
      }
   }
   
   private void clearGroundedProjectiles(){
      int i;
      ArrayList<Entity> entList = new ArrayList<Entity>();
      
      // Remove all arrows that have hit the ground from hashmap
      Iterator<Map.Entry<Entity, String>> it = plugin.getProjMap().entrySet().iterator();
      while(it.hasNext()){
         Entry<Entity, String> arrow = it.next();
         if(arrow.getValue().contains("grounded"))
            entList.add(arrow.getKey());
      }
      // The removal is separate to stop concurrency issues
      for(i = entList.size() - 1; entList.size() > 0; i--){
         plugin.getProjMap().remove(entList.get(i));
         entList.remove(i);
      }
   }
}
