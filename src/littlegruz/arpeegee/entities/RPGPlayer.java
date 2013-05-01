package littlegruz.arpeegee.entities;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class RPGPlayer {
   private String name, incomplete, complete, party;
   private int level;
   private float gear;
   private boolean move, baseAttack;

   // New RPGPlayer
   public RPGPlayer(String name){
      this.name = name;
      level = 1;
      gear = 0;
      incomplete = "none";
      complete = "none";
      party = "none";
      move = true;
   }
   
   // Restoring an RPGPlayer from a saved state
   public RPGPlayer(String name, int level, float gear, String incomplete, String complete, String party){
      this.name = name;
      this.level = level;
      this.gear = gear;
      this.incomplete = incomplete;
      this.complete = complete;
      this.party = party;
      move = true;
   }

   public String getName() {
      return name;
   }
   
   public int getLevel(){
      return level;
   }
   
   public void setLevel(int lvl){
      level = lvl;
   }
   
   public float getGearLevel(){
      return gear;
   }
   
   public void setGearLevel(float gear){
      this.gear = gear;
   }
   
   public void calcGearLevel(PlayerInventory playerInv){
      gear = 0;
      
      if(playerInv.getHelmet().getType().compareTo(Material.LEATHER_HELMET) == 0)
         gear = 1;
      else if(playerInv.getHelmet().getType().compareTo(Material.IRON_HELMET) == 0)
         gear = 2;
      else if(playerInv.getHelmet().getType().compareTo(Material.GOLD_HELMET) == 0)
         gear = 3;
      else if(playerInv.getHelmet().getType().compareTo(Material.CHAINMAIL_HELMET) == 0)
         gear = 3;
      else if(playerInv.getHelmet().getType().compareTo(Material.DIAMOND_HELMET) == 0)
         gear = 4;
      
      if(playerInv.getChestplate().getType().compareTo(Material.LEATHER_CHESTPLATE) == 0)
         gear += 1;
      else if(playerInv.getChestplate().getType().compareTo(Material.IRON_CHESTPLATE) == 0)
         gear += 2;
      else if(playerInv.getChestplate().getType().compareTo(Material.GOLD_CHESTPLATE) == 0)
         gear += 3;
      else if(playerInv.getChestplate().getType().compareTo(Material.CHAINMAIL_CHESTPLATE) == 0)
         gear += 3;
      else if(playerInv.getChestplate().getType().compareTo(Material.DIAMOND_CHESTPLATE) == 0)
         gear += 4;
      
      if(playerInv.getLeggings().getType().compareTo(Material.LEATHER_LEGGINGS) == 0)
         gear += 1;
      else if(playerInv.getLeggings().getType().compareTo(Material.IRON_LEGGINGS) == 0)
         gear += 2;
      else if(playerInv.getLeggings().getType().compareTo(Material.GOLD_LEGGINGS) == 0)
         gear += 3;
      else if(playerInv.getLeggings().getType().compareTo(Material.CHAINMAIL_LEGGINGS) == 0)
         gear += 3;
      else if(playerInv.getLeggings().getType().compareTo(Material.DIAMOND_LEGGINGS) == 0)
         gear += 4;
      
      if(playerInv.getBoots().getType().compareTo(Material.LEATHER_BOOTS) == 0)
         gear += 1;
      else if(playerInv.getBoots().getType().compareTo(Material.IRON_BOOTS) == 0)
         gear += 2;
      else if(playerInv.getBoots().getType().compareTo(Material.GOLD_BOOTS) == 0)
         gear += 3;
      else if(playerInv.getBoots().getType().compareTo(Material.CHAINMAIL_BOOTS) == 0)
         gear += 3;
      else if(playerInv.getBoots().getType().compareTo(Material.DIAMOND_BOOTS) == 0)
         gear += 4;
      
      // Average out the values
      gear /= 4;
      // Give value range to all the max to match the original Minecraft sword damage
      gear *= 2;
   }

   public String getParty(){
      return party;
   }

   public void setParty(String party){
      this.party = party;
   }

   public String getIncomplete(){
      return incomplete;
   }

   public void setIncomplete(String incomplete){
      this.incomplete = incomplete;
   }

   public String getComplete(){
      return complete;
   }

   public void setComplete(String complete){
      this.complete = complete;
   }

   public boolean canMove(){
      return move;
   }

   public void setMove(boolean move){
      this.move = move;
   }

   public boolean isBaseAttackReady(){
      return baseAttack;
   }

   public void setBaseAttackReadiness(boolean baseAttack){
      this.baseAttack = baseAttack;
   }
}
