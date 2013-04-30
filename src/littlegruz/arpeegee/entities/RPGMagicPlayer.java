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
      
      for(int i = 0; i < 4; i++)
         sheepArray[i] = UUID.fromString("0");
   }

   public RPGMagicPlayer(String name, int level, float gear, int buildUp, String incomplete, String complete, String party){
      super(name, level, gear, incomplete, complete, party);
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
      conf = true;
      this.buildUp = buildUp;
      sheepCount = 0;
      sheepArray = new UUID[4];
      
      for(int i = 0; i < 4; i++)
         sheepArray[i] = UUID.fromString("0");
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
   public boolean sheepSearch(UUID id, boolean destroy){
      int i;
      
      for(i = 0; i < 4; i++){
         if(sheepArray[i].compareTo(id) == 0){
               if(destroy){
                  sheepArray[i] = UUID.fromString("0");
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
