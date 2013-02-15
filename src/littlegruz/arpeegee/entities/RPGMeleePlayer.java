package littlegruz.arpeegee.entities;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class RPGMeleePlayer extends RPGPlayer{
   private int rage;
   private boolean flash, silence, punch, imob, slash;
   public RPGMeleePlayer(String name){
      super(name);
      rage = 0;
      flash = true;
      silence = true;
      punch = true;
      imob = true;
      slash = true;
   }

   public RPGMeleePlayer(String name, int level, int gear, int rage, String incomplete, String complete, String party){
      super(name, level, gear, incomplete, complete, party);
      this.rage = rage;
      flash = true;
      silence = true;
      punch = true;
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

   public boolean isPunchReady(){
      return punch;
   }

   public void setPunchReadiness(boolean punch){
      this.punch = punch;
   }

   public boolean isImobiliseReady(){
      return imob;
   }

   public void setImobiliseReadiness(boolean imob){
      this.imob = imob;
   }

   public boolean isSlashReady(){
      return slash;
   }

   public void setSlashReadiness(boolean slash){
      this.slash = slash;
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
