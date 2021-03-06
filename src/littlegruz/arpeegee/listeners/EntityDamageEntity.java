package littlegruz.arpeegee.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMagicPlayer;
import littlegruz.arpeegee.entities.RPGMeleePlayer;
import littlegruz.arpeegee.entities.RPGPlayer;

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
         
         // Should still take damage if blinded
         if(plugin.getConfMap().get(event.getDamager().getUniqueId()) != null){
            /* Apply damage and set the damage cause to the mage who cast this confusion
             * Can assume LivingEntity because confMap would have return false otherwise*/
            ((LivingEntity)event.getDamager()).damage(1, plugin.getServer().getPlayer(plugin.getConfMap().get(event.getEntity().getUniqueId())));
         }
         
         // Blindness miss
         if(plugin.getBlindMap().get(event.getDamager()) != null){
            event.setCancelled(true);
            if(event.getDamager() instanceof Player)
               ((Player) event.getDamager()).sendMessage("*missed*");
            return;
         }
         
         if(event.getDamager() instanceof Player
               && event.getEntity() instanceof LivingEntity){
            Player playa = (Player) event.getDamager();
            LivingEntity victim = (LivingEntity) event.getEntity();
            
            event.setDamage(1);
            
            // Heal spell
            if(playa.getItemInHand().getData().toString().contains("WHITE DYE")
                  && plugin.getMagicPlayerMap().get(playa.getName()) != null
                  && playa.getLevel() >= 16){
               event.setCancelled(true);
               
               if(!plugin.getMagicPlayerMap().get(playa.getName()).isHealReady()){
                  playa.sendMessage("Heal is still on cooldown");
                  return;
               }
               else{
                  ItemStack is = new ItemStack(351,1);
                  is.setDurability((short)15);
                  playa.getInventory().remove(is);
                  plugin.giveCooldown(playa, "heal", "magic", 10);
                  plugin.getMagicPlayerMap().get(playa.getName()).setHealReadiness(false);
               }
               
               healSpell(playa, victim);
            }
            // Cancel mage item attack damage
            else if(plugin.getMagicPlayerMap().get(playa.getName()) != null
                  && (playa.getItemInHand().getType().compareTo(Material.WHEAT) == 0
                  || playa.getItemInHand().getData().toString().contains("ORANGE DYE")
                  || playa.getItemInHand().getData().toString().contains("YELLOW DYE")
                  || playa.getItemInHand().getData().toString().contains("RED DYE"))){
               event.setDamage(0);
            }
            // Damage by a sword by melee player
            else if(playa.getItemInHand().getType().toString().contains("SWORD")
                  && plugin.getMeleePlayerMap().get(playa.getName()) != null){
               
               meleeSwordAttack(playa, victim, event);
            }
            // Non-default damage for fist by melee player
            else if(playa.getItemInHand().getTypeId() == 0
                  && plugin.getMeleePlayerMap().get(playa.getName()) != null){
               if(plugin.getMeleePlayerMap().get(playa.getName()).isBaseAttackReady()
                     && plugin.isEnemy(victim, plugin.getMeleePlayerMap().get(playa.getName()).getParty())){
                  plugin.ohTheDamage(event, victim, 2);
                  plugin.giveCooldown(playa, "default", "default", 1);
                  plugin.getMeleePlayerMap().get(playa.getName()).setBaseAttackReadiness(false);
               }
               else{
                  event.setCancelled(true);
               }
            }
            else if((playa.getItemInHand().getType().toString().contains("SWORD")
                  || playa.getItemInHand().getType().compareTo(Material.BOW) == 0
                  || playa.getItemInHand().getType().compareTo(Material.AIR) == 0)
                  && (plugin.getMeleePlayerMap().get(playa.getName()) != null
                  || plugin.getMagicPlayerMap().get(playa.getName()) != null
                  || plugin.getRangedPlayerMap().get(playa.getName()) != null)){
               RPGPlayer rpgPlaya;

               /* Find the right type of player*/
               if(plugin.getMeleePlayerMap().get(playa.getName()) != null)
                  rpgPlaya = plugin.getMeleePlayerMap().get(playa.getName());
               else if(plugin.getMagicPlayerMap().get(playa.getName()) != null)
                  rpgPlaya = plugin.getMagicPlayerMap().get(playa.getName());
               else
                  rpgPlaya = plugin.getRangedPlayerMap().get(playa.getName());
               
               if(rpgPlaya.isBaseAttackReady() && plugin.isEnemy(victim, rpgPlaya.getParty())){
                  plugin.ohTheDamage(event, victim, 1);
                  plugin.giveCooldown(playa, "default", "default", 1);
                  rpgPlaya.setBaseAttackReadiness(false);
               }
               else
                  event.setCancelled(true);
            }
         }
         // Player arrow hit
         else if(event.getDamager() instanceof Arrow){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               float attack, dmg;
               int type;
               String arrowData, party;
               StringTokenizer st;
               
               arrowData = plugin.getProjMap().get(event.getDamager()).replace("grounded", "");
               st = new StringTokenizer(arrowData, "|");
               
               attack = Float.parseFloat(st.nextToken());
               type = Integer.parseInt(st.nextToken());
               party = st.nextToken();
               
               if(!plugin.isEnemy(event.getEntity(), party)){
                  event.setCancelled(true);
                  return;
               }
               
               dmg = attack;
               
               // Type 2 is the blind arrow
               if(type == 2){
                  final Entity ent = event.getEntity();
                  plugin.getBlindMap().put(ent, 1);
                  plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                     public void run(){
                        plugin.getBlindMap().remove(ent);
                     }
                  }, (long)(attack * 20));
                  
                  /* Blinding arrow only deals half the normal damage*/
                  dmg = attack / 2;
               }
               
               /* Set player damage*/
               if(event.getEntity() instanceof Player){
                  dmg = plugin.damageToPlayer((Player) event.getEntity(), attack, true);
               }
               
               // If crit do double damage. 0% to 20% chance
               if(plugin.probabilityRoll((int)(5 * (attack / 2)))){
                  event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                  plugin.ohTheDamage(event, event.getEntity(), dmg * 2);
               }
               else
                  plugin.ohTheDamage(event, event.getEntity(), dmg);
               
               clearGroundedProjectiles();
            }
         }
         // Slow bow hit
         else if(event.getDamager() instanceof Snowball){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               float bow;
               String arrowData, party;
               StringTokenizer st;
               
               arrowData = plugin.getProjMap().get(event.getDamager()).replace("grounded", "");
               st = new StringTokenizer(arrowData, "|");
               
               bow = Float.parseFloat(st.nextToken());
               party = st.nextToken();
               
               if(!plugin.isEnemy(event.getEntity(), party)){
                  event.setCancelled(true);
                  return;
               }
               
               plugin.getProjMap().remove(event.getDamager());
               
               if(event.getEntity() instanceof LivingEntity){
                  ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)(bow * 20), 2), true);
                  /* Slowing arrow only deals half the normal damage*/
                  bow /= 2;
                  
                  /* Set player damage*/
                  if(event.getEntity() instanceof Player){
                     bow = plugin.damageToPlayer((Player) event.getEntity(), bow, true);
                  }
               }
               
               plugin.ohTheDamage(event, event.getEntity(), bow);
               
               clearGroundedProjectiles();
            }
         }
         // Sheep bow hit
         else if(event.getDamager() instanceof SmallFireball){
            // Check that it came from the right player
            if(plugin.getProjMap().get(event.getDamager()) != null){
               int bow;
               final Sheep primedSheep;
               String arrowData, party;
               StringTokenizer st;
               
               arrowData = plugin.getProjMap().get(event.getDamager()).replace("grounded", "");
               st = new StringTokenizer(arrowData, "|");
               
               bow = (int) Float.parseFloat(st.nextToken());
               party = st.nextToken();
               
               if(!plugin.isEnemy(event.getEntity(), party)){
                  event.setCancelled(true);
                  return;
               }
               
               plugin.getProjMap().remove(event.getDamager());
               
               if(event.getEntity() instanceof LivingEntity){
                  final LivingEntity le = (LivingEntity) event.getEntity();
                  event.setDamage(0);
                  
                  // Stop fire ticks one tick later because doing it now wont do anything
                  plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                     public void run(){
                        le.setFireTicks(0);
                        le.setVelocity(le.getVelocity().multiply(0.5F));
                     }
                  }, 1L);
               }
               
               primedSheep = event.getDamager().getWorld().spawn(event.getDamager().getLocation(), Sheep.class);
               primedSheep.setHealth(bow);
               
               plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                  public void run(){
                     primedSheep.getLocation().getWorld().createExplosion(primedSheep.getLocation(), (float) primedSheep.getHealth(), true);
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
               String ballData, party;
               StringTokenizer st;
               
               event.setCancelled(true);
               
               ballData = plugin.getProjMap().get(event.getDamager());
               st = new StringTokenizer(ballData, "|");
               
               magic = (int) Double.parseDouble(st.nextToken());
               discharge = st.nextToken().charAt(0);
               party = st.nextToken();
               
               if(!plugin.isEnemy(event.getEntity(), party)){
                  return;
               }
               
               if(discharge == 'y')
                  magic = (int)(magic * 1.5);
               
               plugin.getProjMap().remove(event.getDamager());
               // 20 server ticks per second
               event.getEntity().setFireTicks(magic * 20);
               
               clearGroundedProjectiles();
            }
         }
         // Player taking generic damage
         else if(event.getEntity() instanceof Player){
            event.setDamage(plugin.damageToPlayer((Player) event.getEntity(), event.getDamage(), true));
         }
      }
   }
   
   private void healSpell(Player playa, LivingEntity fortunate){
      int adv;
      float spell = plugin.getMagicPlayerMap().get(playa.getName()).getAttack();
      
      // Normal heal/undead damage
      if(plugin.getBuildUpMap().get(playa.getName()) == null){
         adv = 1;
         plugin.getMagicPlayerMap().get(playa.getName()).addBuildUp(6);
      }
      // Advanced heal/undead damage
      else{
         adv = 2;
         plugin.getBuildUpMap().remove(playa.getName());
      }
      
      if(fortunate instanceof Player){
         playa.playEffect(fortunate.getLocation(), Effect.SMOKE, 1);
         if(fortunate.getHealth() + (spell * adv) > fortunate.getMaxHealth())
            fortunate.setHealth(fortunate.getMaxHealth());
         else
            fortunate.setHealth((int)(fortunate.getHealth() + (spell * adv)));
         playa.sendMessage("Healed");
      }
      // If player heals a zombie, then deal damage instead
      else if(fortunate instanceof Zombie){
         fortunate.playEffect(EntityEffect.HURT);
         fortunate.damage((int)(spell * adv));
         playa.sendMessage("Undead damage!");
      }
   }
   
   private void meleeSwordAttack(Player playa, LivingEntity victim, EntityDamageByEntityEvent event){
      int crit;
      float attack;
      RPGMeleePlayer rpgMeleeP = plugin.getMeleePlayerMap().get(playa.getName());;
      
      /* Check if the player can swing yet and if the entity is an enemy*/
      if(rpgMeleeP.isSwordReady() && plugin.isEnemy(victim, rpgMeleeP.getParty())){
         plugin.giveCooldown(playa, "slash", "melee", 1.5);
         rpgMeleeP.setSwordReadiness(false);
      }
      else{
         event.setCancelled(true);
         return;
      }
      
      rpgMeleeP.calcGearLevel(playa.getInventory());
      attack = rpgMeleeP.getAttack();
      
      /* Crit chance 2% to 18%. Berserk mode adds 10%
       * Damage in berserk adds 1 to 3 damage*/
      if(plugin.getBerserkMap().get(playa.getName()) != null){
         if(plugin.probabilityRoll((int)(2 * attack + 10)))
            crit = 2;
         else
            crit = 1;
         attack++;
      }
      else{
         if(plugin.probabilityRoll((int)(5 * attack)))
            crit = 2;
         else
            crit = 1;
         
         rpgMeleeP.addRage(5);
      }
      
      if(victim instanceof Player)
         attack = plugin.damageToPlayer((Player) victim, attack, true);
      
      plugin.ohTheDamage(event, victim, attack * crit);
      
      if(crit == 2)
         playa.sendMessage("*crit*");
      
      // Silence
      if(rpgMeleeP.getOnHit() == 1){
         if(victim instanceof Player){
            
            /* Cause existing player to be unable to cast magic*/
            if(plugin.getMagicPlayerMap().get(((Player) victim).getName()) != null){
               final RPGMagicPlayer rpgMagicVic = plugin.getMagicPlayerMap().get(((Player) victim).getName());
               rpgMagicVic.silencePlayer();
               ((Player) victim).sendMessage("*silenced*");
               
               plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                  public void run(){
                     rpgMagicVic.unsilencePlayer();
                     plugin.getServer().getPlayer(rpgMagicVic.getName()).sendMessage("*unsilenced*");
                  }
               }, 60L);
            }
            else if(plugin.getMeleePlayerMap().get(((Player) victim).getName()) != null){
               final RPGMeleePlayer rpgMeleeVic = plugin.getMeleePlayerMap().get(((Player) victim).getName());
               rpgMeleeVic.silencePlayer();
               ((Player) victim).sendMessage("*silenced*");
               
               plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                  public void run(){
                     rpgMeleeVic.unsilencePlayer();
                     plugin.getServer().getPlayer(rpgMeleeVic.getName()).sendMessage("*unsilenced*");
                  }
               }, 60L);
            }
         }
         rpgMeleeP.setOnHit(0);
         plugin.giveCooldown(playa, "sssh", "melee", 8);
         rpgMeleeP.setSilenceReadiness(true);
      }
      // Imobilise
      else if(rpgMeleeP.getOnHit() == 2){
         if(victim instanceof Player){
            RPGPlayer rpgPlaya;

            rpgPlaya = plugin.getRPGPlayer(((Player) victim).getName());
            
            /* Cause existing player to be unable to move*/
            if(rpgPlaya != null){
               final RPGPlayer rpgPlayer = rpgPlaya;
               rpgPlayer.setMove(false);
               ((Player) victim).sendMessage("*imobilised*");
               
               plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                  public void run(){
                     rpgPlayer.setMove(true);
                     plugin.getServer().getPlayer(rpgPlayer.getName()).sendMessage("*mobilised*");
                  }
               }, 60L);
            }
         }
         /* Since non-players can not be rendered immobile due to no
          * non-player move events. So instead, they will just have a
          * hugely slowing potion effect*/
         else{
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 5), true);
         }
         rpgMeleeP.setOnHit(0);
         plugin.giveCooldown(playa, "imob", "melee", 10);
         rpgMeleeP.setImobiliseReadiness(true);
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
