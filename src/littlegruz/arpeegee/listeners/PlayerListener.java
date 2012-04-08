package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;

import net.minecraft.server.EntityFireball;
import net.minecraft.server.EntityLiving;

import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
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
   public void onPlayerSmack(EntityDamageByEntityEvent event){
      if(event.getDamager() instanceof Player){
         Player playa = (Player) event.getDamager();
         if(playa.getItemInHand().getType().compareTo(Material.REDSTONE_TORCH_ON) == 0){
            event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
         }
      }
   }

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event){
      Player playa = event.getPlayer();
      if(playa.getItemInHand().getType().compareTo(Material.REDSTONE_TORCH_ON) == 0){
         Vector dir = playa.getLocation().getDirection().multiply(100);
         
         EntityLiving entityPlaya = ((CraftPlayer) playa).getHandle();
         EntityFireball fireball = new EntityFireball(((CraftWorld) playa.getWorld()).getHandle(), entityPlaya, dir.getX(), dir.getY(), dir.getZ());
         
         ((CraftWorld) playa.getWorld()).getHandle().addEntity(fireball);
      }
   }
}
