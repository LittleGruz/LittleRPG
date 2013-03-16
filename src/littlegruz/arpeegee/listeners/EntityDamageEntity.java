package littlegruz.arpeegee.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMagicPlayer;
import littlegruz.arpeegee.entities.RPGSheep;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
            // Damage by a diamond sword (crit sword) TODO combine with iron?
            else if(playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0
                  && plugin.getMeleePlayerMap().get(playa.getName()) != null
                  && playa.getLevel() >= 8){
               int blade, crit, level;
               
               // Check if the player can swing yet
               if(plugin.getMeleePlayerMap().get(playa.getName()).isSlashReady()){
                  plugin.giveCooldown(playa, "slash", "melee", 1);
                  plugin.getMeleePlayerMap().get(playa.getName()).setSlashReadiness(false);
               }
               else
                  return;
               
               // TODO change to be properly based off gear
               blade = plugin.getMeleePlayerMap().get(playa.getName()).getGearLevel();
               level = plugin.getMeleePlayerMap().get(playa.getName()).getLevel();
               
               /* Crit chance 5% to 25%. Berserk mode adds 10-20%
                * Damage in berserk adds 1 to 3 damage*/
               if(plugin.getBerserkMap().get(playa.getName()) != null){
                  if(plugin.probabilityRoll(5 * blade + (level / 2) + 10))
                     crit = 2;
                  else
                     crit = 1;
                  
                  if(level >= plugin.MAX_LEVEL)
                     blade += 3;
                  else if(level >= 14)
                     blade += 2;
                  else if(level >= 7)
                     blade += 1;
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
            // Damage by an iron sword (block sword) TODO combine with diamond?
            else if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0
                  && plugin.getMeleePlayerMap().get(playa.getName()) != null){
               int blade;
   
               // Check if the player can swing yet
               if(plugin.getMeleePlayerMap().get(playa.getName()).isSlashReady()){
                  plugin.giveCooldown(playa, "slash", "melee", 1);
                  plugin.getMeleePlayerMap().get(playa.getName()).setSlashReadiness(false);
               }
               else
                  return;
               // TODO change to be properly based off gear
               blade = plugin.getMeleePlayerMap().get(playa.getName()).getGearLevel();
               
               if(blade > 1)
                  blade -= 1;

               plugin.ohTheDamage(event, victim, blade);
   
               if(plugin.getBerserkMap().get(playa.getName()) == null)
                  plugin.getMeleePlayerMap().get(playa.getName()).addRage(5);
               
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
         // Player taking damage
         else if(event.getEntity() instanceof Player){
            Player playa = (Player) event.getEntity();
            // Melee player taking (reduced) damage and possibly blocking an attack
            if(plugin.getMeleePlayerMap().get(playa.getName()) != null){
               int dmg;
               
               /* The amount that is decreased from the damage to be taken depends
                * on the players level */
               if(playa.getLevel() < 5)
                  dmg = event.getDamage();
               else if(playa.getLevel() < 10)
                  dmg = (int) (event.getDamage() * (event.getDamage() * 0.67) / event.getDamage());
               else
                  dmg = (int) (event.getDamage() * (event.getDamage() * 0.5) / event.getDamage());
               
               event.setDamage(dmg);
               armourDamage(playa,3);
               
               // Damage block check
               if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
                  int block;
                  // TODO change to be properly based off gear
                  block = plugin.getMeleePlayerMap().get(playa.getName()).getGearLevel();
                  
                  if(playa.isBlocking())
                     block += 2;
                  
                  // Block check from 5% to 35%. Add 10% if blocking.
                  if(plugin.probabilityRoll(5 * block)){
                     event.setCancelled(true);
                     playa.sendMessage("*blocked*");
                     return;
                  }
               }
            }
            else if(plugin.getRangedPlayerMap().get(playa.getName()) != null){
               armourDamage(playa,1);
            }
            else if(plugin.getMagicPlayerMap().get(playa.getName()) != null){
               armourDamage(playa,2);
            }
         }
         // Player arrow hit
         else if(event.getDamager() instanceof Arrow){
            // Check that it came from a player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               ArrayList<Entity> ent = new ArrayList<Entity>();
               int gear, type, i;
               String arrowData;
               StringTokenizer st;
               
               arrowData = plugin.getProjMap().get(event.getDamager()).replace("grounded", "");
               st = new StringTokenizer(arrowData, "|");
               
               gear = (int) Double.parseDouble(st.nextToken());
               type = Integer.parseInt(st.nextToken());
               
               // Type 2 is the slow arrow
               if(type == 2){
                  if(event.getEntity() instanceof LivingEntity){
                     ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, gear, 1), true);
                     gear /= 2;
                  }
               }
               // Type 3 is the sheep arrow
               else if(type == 3){
                  final RPGSheep sheep;
                  
                  sheep = new RPGSheep(gear);
                  
                  plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                     public void run(){
                        sheep.getLocation().getWorld().createExplosion(sheep.getLocation(), sheep.getDamage(), sheep.isIncedenary());
                        sheep.remove();
                     }
                 }, 40L);
               }
               // Type 4 is the blind arrow
               else if(type == 4){
                  if(event.getEntity() instanceof Player){
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
               
               // If crit do double damage. 0% to 30% chance TODO this chance has probably changed
               if(plugin.probabilityRoll(5 * (gear - 1))){
                  event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                  plugin.ohTheDamage(event, event.getEntity(), gear * 2);
               }
               else
                  plugin.ohTheDamage(event, event.getEntity(), gear);
               
               // Remove all arrows that have hit the ground from hashmap
               Iterator<Map.Entry<Entity, String>> it = plugin.getProjMap().entrySet().iterator();
               while(it.hasNext()){
                  Entry<Entity, String> arrow = it.next();
                  if(arrow.getValue().contains("grounded"))
                     ent.add(arrow.getKey());
               }
               // The removal is separate to stop concurrency issues
               for(i = ent.size() - 1; ent.size() > 0; i--){
                  plugin.getProjMap().remove(ent.get(i));
                  ent.remove(i);
               }
            }
         }
         // Player fireball hit
         else if(event.getDamager() instanceof Fireball){
            // Check that it came from a player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               ArrayList<Entity> entList = new ArrayList<Entity>();
               int magic, i;
               char discharge;
               String ballData;
               StringTokenizer st;
               
               ballData = plugin.getProjMap().get(event.getDamager());
               st = new StringTokenizer(ballData, "|");
               
               magic = (int) Double.parseDouble(st.nextToken());
               discharge = st.nextToken().charAt(0);
               
               if(discharge == 'y')
                  magic = (int)(magic * 1.5);
               
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
   
   private void armourDamage(Player playa, int dmg){
      if(playa.getInventory().getBoots() != null)
         playa.getInventory().getBoots().setDurability((short) (playa.getInventory().getBoots().getDurability() + dmg));
      if(playa.getInventory().getChestplate() != null)
         playa.getInventory().getChestplate().setDurability((short) (playa.getInventory().getChestplate().getDurability() + dmg));
      if(playa.getInventory().getHelmet() != null)
         playa.getInventory().getHelmet().setDurability((short) (playa.getInventory().getHelmet().getDurability() + dmg));
      if(playa.getInventory().getLeggings() != null)
         playa.getInventory().getLeggings().setDurability((short) (playa.getInventory().getLeggings().getDurability() + dmg));
   }
}
