package littlegruz.arpeegee.entities;

import java.util.UUID;

public class RPGMagicPlayer extends RPGPlayer{
   private int buildUp, sheepCount;
   private boolean heal, lightning, fire, sheeeep, conf;
   private UUID[] sheepArray;

   public RPGMagicPlayer(String name){
      super(name);
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
      conf = true;
      buildUp = 0;
      sheepCount = 0;
      sheepArray = new UUID[4];
      
      /*for(int i = 0; i < 4; i++)
         sheepArray[i] = null;*/
   }

   public RPGMagicPlayer(String name, int level, float gear, int buildUp, String incompleteQuests, String completeQuests, String party){
      super(name, level, gear, incompleteQuests, completeQuests, party);
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
      conf = true;
      this.buildUp = buildUp;
      sheepCount = 0;
      sheepArray = new UUID[4];
      
      /*for(int i = 0; i < 4; i++)
         sheepArray[i] = null;*/
   }

   public int getBuildUp(){
      return buildUp;
   }
   
   public void setBuildUp(int buildUp){
      this.buildUp = buildUp;
   }
   
   public void addBuildUp(int add){
      if(buildUp + add > 100)
         buildUp = 100;
      else
         buildUp += add;
   }

   public boolean isHealReady(){
      return heal;
   }

   public void setHealReadiness(boolean heal){
      this.heal = heal;
   }

   public boolean isLightningReady(){
      return lightning;
   }

   public void setLightningReadiness(boolean lightning){
      this.lightning = lightning;
   }

   public boolean isFireReady(){
      return fire;
   }

   public void setFireReadiness(boolean fire){
      this.fire = fire;
   }

   public boolean isSheepReady(){
      return sheeeep;
   }

   public void setSheepReadiness(boolean sheep){
      this.sheeeep = sheep;
   }
   
   public boolean isConfusionReady(){
      return conf;
   }

   public void setConfusionReadiness(boolean conf){
      this.conf = conf;
   }

   public int getSheepCount(){
      return sheepCount;
   }

   public void setSheepCount(int sheepCount){
      this.sheepCount = sheepCount;
   }

   public UUID[] getSheepArray(){
      return sheepArray;
   }
   
   /* Adds a sheep to a position in the array*/
   public boolean setSheepArrayID(int pos, UUID id){
      if(pos < 4 && pos > -1){
         sheepArray[pos] = id;
         return true;
      }
      else
         return false;
   }
   
   /* Searches for a sheep based on its UUID*/
   public boolean sheepSearchAndDestroy(UUID id, boolean destroy){
      int i, j;
      
      for(i = sheepCount - 1; i >= 0; i--){
         if(sheepArray[i].compareTo(id) == 0){
               if(destroy){
                  /* Move down IDs to keep the array contiguous*/
                  j = i;
                  while(j < 3){
                     sheepArray[j] = sheepArray[j + 1];
                     j++;
                  }
                  /* If array was bigger would probably bother to check in each loop
                   * iteration for a UUID of 0 and stop the loop*/
                  sheepArray[j] = null;
                  sheepCount--;
               }
            return true;
         }
      }
      
      return false;
   }

   public void silencePlayer(){
      heal = false;
      lightning = false;
      fire = false;
      sheeeep = false;
   }
   
   public void unsilencePlayer(){
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
   }
}
