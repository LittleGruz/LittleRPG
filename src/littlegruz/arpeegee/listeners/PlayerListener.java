package littlegruz.arpeegee.listeners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {
   private ArpeegeeMain plugin;
   
   public PlayerListener(ArpeegeeMain instance){
      plugin = instance;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event){
      plugin.getPlayerMap().put(event.getPlayer().getName(), new RPGPlayer(event.getPlayer().getName(), "Warrior", "Minion"));
   }

   @EventHandler
   public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
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
                     e.damage(1);
                  }
               }
           }, 20L);
         }
         // Provides a base 5% critical hit chance
         else if(playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0){
            event.setCancelled(true);
            
            /* If player is in Berserk mode, attack has a base 10% chance of
             * crit otherwise the base crit is 5%*/
            //TODO Sword damage and perhaps determine set doubles for probabilities i.e. ten = 1.1, five = 1.052
            if(plugin.getBerserkMap().get(playa.getName()) != null)
               victim.damage(1 * (int) getRandomDouble(1.1));
            else{
               victim.damage(1 * (int) getRandomDouble(1.052));
               plugin.getPlayerMap().get(playa.getName()).addRage(25);
            }
            
            playa.getItemInHand().setDurability((short) 0);
         }
         // Provides a base 10% dodge chance
         else if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
            event.setCancelled(true);

            //TODO Sword damage
            victim.damage(1);
            
            if(plugin.getBerserkMap().get(playa.getName()) == null)
               plugin.getPlayerMap().get(playa.getName()).addRage(25);
            
            playa.getItemInHand().setDurability((short) 0);
            
         }
      }
      else if(event.getEntity() instanceof Player){
         Player playa = (Player) event.getEntity();
         if(playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0){
            if((int) getRandomDouble(1.052) == 1){
               event.setCancelled(true);
               playa.sendMessage("*dodged*");
            }
         }
      }
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event){
      Player playa = event.getPlayer();
      //playa.sendMessage(playa.getItemInHand().getData().toString());//Data checking
      //playa.sendMessage(event.getAction().toString());//Data checking
      
      // Casting weapon to "Flash"
      if(playa.getItemInHand().getData().toString().contains("MAGENTA DYE")
            && event.getAction().toString().contains("RIGHT_CLICK")){
         HashSet<Byte> hs = new HashSet<Byte>();
         Block block;
         Location loc;
   
         hs.add((byte)0); //Air
         hs.add((byte)8); //Flowing water
         hs.add((byte)9); //Stationary water
         hs.add((byte)20); //Glass
         hs.add((byte)101); //Iron bar
         hs.add((byte)102); //Glass pane
         
         //TODO Flat distance until I determine a proper scale
         //playa.sendMessage(playa.getTargetBlock(hs, 20).getType().toString());
         block = playa.getTargetBlock(hs, 20);
         loc = block.getLocation();
         
         //playa.sendMessage(block.getType().toString());
         
         if(block.getType().compareTo(Material.AIR) != 0
               && block.getType().compareTo(Material.WATER) != 0
               && block.getType().compareTo(Material.STATIONARY_WATER) != 0
               && block.getType().compareTo(Material.GLASS) != 0
               && block.getType().compareTo(Material.THIN_GLASS) != 0
               && block.getType().compareTo(Material.IRON_FENCE) != 0){
            loc.setY(loc.getY() + 1.5);
            
            if(loc.getBlock().getType().compareTo(Material.WATER) == 0
                  || loc.getBlock().getType().compareTo(Material.STATIONARY_WATER) == 0
                  || loc.getBlock().getType().compareTo(Material.AIR) == 0){
               playa.teleport(new Location(loc.getWorld(), loc.getX(),
                     loc.getY(), loc.getZ(), playa.getLocation().getYaw(),
                     playa.getLocation().getPitch()));
               playa.sendMessage("*Zoom*");
            }
            else
               playa.sendMessage("You can not flash to there");
         }
         else
            playa.sendMessage("You can not flash that far!");
         
         event.setCancelled(true);
      }
      // This fireball creation code is based off MadMatt199's code (https://github.com/madmatt199/GhastBlast)
      // Casting weapon to launch a fireball
      else if(playa.getItemInHand().getData().toString().contains("RED DYE")
            && event.getAction().toString().compareTo("RIGHT_CLICK_AIR") == 0){
         Vector dir = playa.getLocation().getDirection().multiply(10);
         Location loc = playa.getLocation();
         
         EntityLiving entityPlaya = ((CraftPlayer) playa).getHandle();
         EntityFireball fireball = new EntityFireball(
               ((CraftWorld) playa.getWorld()).getHandle(), entityPlaya,
               dir.getX(), dir.getY(), dir.getZ());
         
         // Spawn the fireball a bit up and away from the player
         fireball.locX = loc.getX() + (dir.getX()/5.0) + 0.25;
         fireball.locY = loc.getY() + (playa.getEyeHeight()/2.0);
         fireball.locZ = loc.getZ() + (dir.getZ()/5.0);
         dir = dir.multiply(10);
         
         ((CraftWorld) playa.getWorld()).getHandle().addEntity(fireball);

         playa.sendMessage("*Fwoosh*");
         event.setCancelled(true);
      }
      // Active berserk mode if player has gained enough rage
      else if(event.getAction().toString().contains("RIGHT_CLICK")
            && (playa.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0
            || playa.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0)){
         final String pName = playa.getName();
         
         if(plugin.getPlayerMap().get(pName).getRage() == 100){
            playa.sendMessage("RAAAAGE (Berserk mode activated)");
            plugin.getPlayerMap().get(pName).setRage(0);
            plugin.getBerserkMap().put(pName, pName);
            // 10 seconds of Berserker mode (increased damage and sword bonuses)
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
               public void run() {
                  plugin.getBerserkMap().remove(pName);
                  plugin.getServer().getPlayer(pName).sendMessage("Cool it");
               }
           }, 200L);
         }
         else
            playa.sendMessage("Not enough rage. Current rage: " + Integer.toString(plugin.getPlayerMap().get(playa.getName()).getRage()));
      }
   }
   
   private void healSpell(Player playa, LivingEntity victim, int adv){
      if(victim instanceof Pig){
         playa.sendMessage("Healed");
         playa.playEffect(victim.getLocation(), Effect.SMOKE, 1); //Change when Pig is changed to Player
         //TODO Player heal
         victim.setHealth((victim.getHealth() + 1));
      }
      // If player heals a zombie, then deal damage instead
      else if(victim instanceof Zombie){
         victim.playEffect(EntityEffect.HURT);
         //TODO Zombie damage
         victim.damage(1 * adv);
      }
   }
   
   private double getRandomDouble(double range){
      Random rand = new Random();
      return (rand.nextDouble() * range) + 1;
   }
   
   
}
