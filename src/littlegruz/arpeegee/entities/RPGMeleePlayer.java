package littlegruz.arpeegee.entities;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class RPGMeleePlayer extends RPGPlayer{
   private int rage, bideAmt, onHit;
   private boolean flash, silence, imob, slash, bide;
   
   public RPGMeleePlayer(String name){
      super(name);
      rage = 0;
      bideAmt = 0;
      onHit = 0;
      flash = true;
      silence = true;
      imob = true;
      slash = true;
      bide = true;
   }

   public RPGMeleePlayer(String name, int level, float gear, int rage, String incompleteQuests, String completeQuests, String party){
      super(name, level, gear, incompleteQuests, completeQuests, party);
      this.rage = rage;
      bideAmt = 0;
      onHit = 0;
      flash = true;
      silence = true;
      imob = true;
      slash = true;
      bide = true;
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
   
   public int getBideAmt(){
      return bideAmt;
   }
   
   public boolean addBideAmt(int add){
      bideAmt += add;
      
      if(bideAmt > super.getAttack())
         return false;
      else
         return true;
   }
   
   public void setBideAmt(int bideAmt){
      this.bideAmt = bideAmt;
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

   public boolean isBideReady(){
      return bide;
   }

   public void setBideReadiness(boolean bide){
      this.bide = bide;
   }
   
   public int getOnHit(){
      return onHit;
   }
   
   /* 0 is normal, 1 is silence, 2 is imobilise*/
   public void setOnHit(int hit){
      onHit = hit;
   }

   public void blindPlayer(){
      slash = false;
   }
   
   public void unblindPlayer(){
      slash = true;
   }

   public void silencePlayer(){
      imob = false;
      silence = false;
   }
   
   public void unsilencePlayer(){
      imob = true;
      silence = true;
   }
   
   public void calcGearLevel(PlayerInventory playerInv){
      if(playerInv.getItemInHand().getType().compareTo(Material.WOOD_SWORD) == 0)
         super.setAttack(super.getAttack() + 0.25F);
      else if(playerInv.getItemInHand().getType().compareTo(Material.STONE_SWORD) == 0)
         super.setAttack(super.getAttack() + 0.5F);
      else if(playerInv.getItemInHand().getType().compareTo(Material.IRON_SWORD) == 0)
         super.setAttack(super.getAttack() + 0.75F);
      else if(playerInv.getItemInHand().getType().compareTo(Material.GOLD_SWORD) == 0)
         super.setAttack(super.getAttack() + 1);
      else if(playerInv.getItemInHand().getType().compareTo(Material.DIAMOND_SWORD) == 0)
         super.setAttack(super.getAttack() + 1.25F);
   }
}
