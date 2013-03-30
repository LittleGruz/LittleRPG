package littlegruz.arpeegee.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class RPGSheep implements Sheep{
   int damage;
   boolean fire;

   public RPGSheep(int dam, boolean fire){
      damage = dam;
      this.fire = fire;
   }
   
   public RPGSheep(int dam){
      damage = dam;
      fire = true;
   }
   
   public RPGSheep(){
      damage = 0;
      fire = true;
   }
   
   public int getDamage(){
      return damage;
   }
   
   public boolean isIncedenary(){
      return fire;
   }
   
   public RPGSheep setDamage(int dam){
      damage = dam;
      return this;
   }

   @Override
   public boolean canBreed(){ 
      return false;
   }

   @Override
   public int getAge(){
      return 0;
   }

   @Override
   public boolean getAgeLock(){
      return false;
   }

   @Override
   public boolean isAdult(){
      return false;
   }

   @Override
   public void setAdult(){
      
   }

   @Override
   public void setAge(int arg0){

   }

   @Override
   public void setAgeLock(boolean arg0){
      
   }

   @Override
   public void setBaby(){
      
   }

   @Override
   public void setBreed(boolean arg0){
      
   }

   @Override
   public LivingEntity getTarget(){
      return null;
   }

   @Override
   public void setTarget(LivingEntity arg0){
      
   }

   @Override
   public boolean addPotionEffect(PotionEffect arg0){
      return false;
   }

   @Override
   public boolean addPotionEffect(PotionEffect arg0, boolean arg1){
      return false;
   }

   @Override
   public boolean addPotionEffects(Collection<PotionEffect> arg0){
      return false;
   }

   @Override
   public Collection<PotionEffect> getActivePotionEffects(){
      return null;
   }

   @Override
   public boolean getCanPickupItems(){
      return false;
   }

   @Override
   public EntityEquipment getEquipment(){
      return null;
   }

   @Override
   public double getEyeHeight(){
      return 0;
   }

   @Override
   public double getEyeHeight(boolean arg0){
      return 0;
   }

   @Override
   public Location getEyeLocation(){
      return null;
   }

   @Override
   public Player getKiller(){
      return null;
   }

   @Override
   public int getLastDamage(){
      return 0;
   }

   @Override
   public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1){
      return null;
   }

   @Override
   public List<Block> getLineOfSight(HashSet<Byte> arg0, int arg1){
      return null;
   }

   @Override
   public int getMaximumAir(){
      return 0;
   }

   @Override
   public int getMaximumNoDamageTicks(){
      return 0;
   }

   @Override
   public int getNoDamageTicks(){
      return 0;
   }

   @Override
   public int getRemainingAir(){
      return 0;
   }

   @Override
   public boolean getRemoveWhenFarAway(){
      return false;
   }

   @Override
   public Block getTargetBlock(HashSet<Byte> arg0, int arg1){
      return null;
   }

   @Override
   public boolean hasLineOfSight(Entity arg0){
      return false;
   }

   @Override
   public boolean hasPotionEffect(PotionEffectType arg0){
      return false;
   }

   @Override
   public <T extends Projectile> T launchProjectile(Class<? extends T> arg0){
      return null;
   }

   @Override
   public void removePotionEffect(PotionEffectType arg0){
      
   }

   @Override
   public void setCanPickupItems(boolean arg0){
      
   }

   @Override
   public void setLastDamage(int arg0){
      
   }

   @Override
   public void setMaximumAir(int arg0){
      
   }

   @Override
   public void setMaximumNoDamageTicks(int arg0){
      
   }

   @Override
   public void setNoDamageTicks(int arg0){
      
   }

   @Override
   public void setRemainingAir(int arg0){
      
   }

   @Override
   public void setRemoveWhenFarAway(boolean arg0){
      
   }

   @Override
   @Deprecated
   public Arrow shootArrow(){
      return null;
   }

   @Override
   @Deprecated
   public Egg throwEgg(){
      return null;
   }

   @Override
   @Deprecated
   public Snowball throwSnowball(){
      return null;
   }

   @Override
   public boolean eject(){
      return false;
   }

   @Override
   public int getEntityId(){
      return 0;
   }

   @Override
   public float getFallDistance(){
      return 0;
   }

   @Override
   public int getFireTicks(){
      return 0;
   }

   @Override
   public EntityDamageEvent getLastDamageCause(){
      return null;
   }

   @Override
   public Location getLocation(){
      return null;
   }

   @Override
   public Location getLocation(Location arg0){
      return null;
   }

   @Override
   public int getMaxFireTicks(){
      return 0;
   }

   @Override
   public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2){
      return null;
   }

   @Override
   public Entity getPassenger(){
      return null;
   }

   @Override
   public Server getServer(){
      return null;
   }

   @Override
   public int getTicksLived(){
      return 0;
   }

   @Override
   public EntityType getType(){
      return null;
   }

   @Override
   public UUID getUniqueId(){
      return null;
   }

   @Override
   public Entity getVehicle(){
      return null;
   }

   @Override
   public Vector getVelocity(){
      return null;
   }

   @Override
   public World getWorld(){
      return null;
   }

   @Override
   public boolean isDead(){
      return false;
   }

   @Override
   public boolean isEmpty(){
      return false;
   }

   @Override
   public boolean isInsideVehicle(){
      return false;
   }

   @Override
   public boolean isValid(){
      return false;
   }

   @Override
   public boolean leaveVehicle(){
      return false;
   }

   @Override
   public void playEffect(EntityEffect arg0){
      
   }

   @Override
   public void remove(){
      
   }

   @Override
   public void setFallDistance(float arg0){
      
   }

   @Override
   public void setFireTicks(int arg0){
      
   }

   @Override
   public void setLastDamageCause(EntityDamageEvent arg0){
      
   }

   @Override
   public boolean setPassenger(Entity arg0){
      return false;
   }

   @Override
   public void setTicksLived(int arg0){
      
   }

   @Override
   public void setVelocity(Vector arg0){
      
   }

   @Override
   public boolean teleport(Location arg0){
      return false;
   }

   @Override
   public boolean teleport(Entity arg0){
      return false;
   }

   @Override
   public boolean teleport(Location arg0, TeleportCause arg1){
      return false;
   }

   @Override
   public boolean teleport(Entity arg0, TeleportCause arg1){
      return false;
   }

   @Override
   public List<MetadataValue> getMetadata(String arg0){
      return null;
   }

   @Override
   public boolean hasMetadata(String arg0){
      return false;
   }

   @Override
   public void removeMetadata(String arg0, Plugin arg1){
      
   }

   @Override
   public void setMetadata(String arg0, MetadataValue arg1){
      
   }

   @Override
   public void damage(int arg0){
      
   }

   @Override
   public void damage(int arg0, Entity arg1){
      
   }

   @Override
   public int getHealth(){
      return 0;
   }

   @Override
   public int getMaxHealth(){
      return 0;
   }

   @Override
   public void resetMaxHealth(){
      
   }

   @Override
   public void setHealth(int arg0){
      
   }

   @Override
   public void setMaxHealth(int arg0){
      
   }

   @Override
   public DyeColor getColor(){
      return null;
   }

   @Override
   public void setColor(DyeColor arg0){
      
   }

   @Override
   public boolean isSheared(){
      return false;
   }

   @Override
   public void setSheared(boolean arg0){
      
   }
}
