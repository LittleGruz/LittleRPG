package littlegruz.arpeegee.entities;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class RPGMeleePlayer extends RPGPlayer{
   private int rage, bide;
   private boolean flash, silence, imob, slash;
   public RPGMeleePlayer(String name){
      super(name);
      rage = 0;
      bide = 0;
      flash = true;
      silence = true;
      imob = true;
      slash = true;
   }

   public RPGMeleePlayer(String name, int level, int gear, int rage, String incomplete, String complete, String party){
      super(name, level, gear, incomplete, complete, party);
      this.rage = rage;
      bide = 0;
      flash = true;
      silence = true;
      imob = true;
      slash = true;
   }

   public int getRage(){
      return rage;
   }
   
   public void setRage(int rage){
      this.rage = rage;
   }
   
   public void addRage(int add){
      if(rage + add > 100)
         rage = 100;
      else
         rage += add;
   }
   
   public int getBide(){
      return bide;
   }
   
   public boolean addBide(int add){
      bide += add;
      
      if(bide > 2 * super.getGearLevel()) //TODO create suitable limit
         return false;
      else
         return true;
   }
   
   public void setBide(int bide){
      this.bide = bide;
   }

   public boolean isFlashReady(){
      return flash;
   }

   public void setFlashReadiness(boolean flash){
      this.flash = flash;
   }

   public boolean isSilenceReady(){
      return silence;
   }

   public void setSilenceReadiness(boolean silence){
      this.silence = silence;
   }

   public boolean isImobiliseReady(){
      return imob;
   }

   public void setImobiliseReadiness(boolean imob){
      this.imob = imob;
   }

   public boolean isSwordReady(){
      return slash;
   }

   public void setSwordReadiness(boolean slash){
      this.slash = slash;
   }
   
   public void blindPlayer(){
      slash = false;
   }
   
   public void unblindPlayer(){
      slash = true;
   }
   
   public void calcGearLevel(PlayerInventory playerInv){
      super.calcGearLevel(playerInv);
      
      if(playerInv.getChestplate().getType().compareTo(Material.WOOD_SWORD) == 0)
         super.setGearLevel(super.getGearLevel() + 1);
      else if(playerInv.getChestplate().getType().compareTo(Material.STONE_SWORD) == 0)
         super.setGearLevel(super.getGearLevel() + 2);
      else if(playerInv.getChestplate().getType().compareTo(Material.IRON_SWORD) == 0)
         super.setGearLevel(super.getGearLevel() + 3);
      else if(playerInv.getChestplate().getType().compareTo(Material.GOLD_SWORD) == 0)
         super.setGearLevel(super.getGearLevel() + 4);
      else if(playerInv.getChestplate().getType().compareTo(Material.DIAMOND_SWORD) == 0)
         super.setGearLevel(super.getGearLevel() + 5);
   }
}
