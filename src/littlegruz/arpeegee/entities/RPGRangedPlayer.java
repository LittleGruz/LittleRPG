package littlegruz.arpeegee.entities;

public class RPGRangedPlayer extends RPGPlayer{
   private boolean egg, slowBow, sheepBow, blindBow, arrow; 

   public RPGRangedPlayer(String name){
      super(name);
      egg = true;
      slowBow = true;
      sheepBow = true;
      blindBow = true;
      arrow = true;
   }

   public RPGRangedPlayer(String name, int level, float gear, String incompleteQuests, String completeQuests, String party){
      super(name, level, gear, incompleteQuests, completeQuests, party);
      egg = true;
      slowBow = true;
      sheepBow = true;
      blindBow = true;
      arrow = true;
   }

   public boolean isEggReady(){
      return egg;
   }
   
   public void setEggReadiness(boolean egg){
      this.egg = egg;
   }

   public boolean isSlowBowReady(){
      return slowBow;
   }
   
   public void setSlowBowReadiness(boolean slow){
      slowBow = slow;
   }

   public boolean isSheepBowReady(){
      return sheepBow;
   }
   
   public void setSheepBowReadiness(boolean sheep){
      sheepBow = sheep;
   }

   public boolean isBlindBowReady(){
      return blindBow;
   }
   
   public void setBlindBowReadiness(boolean blind){
      blindBow = blind;
   }
   
   public boolean isArrowReady(){
      return arrow;
   }

   public void setArrowReadiness(boolean arrow){
      this.arrow = arrow;
   }

   public void blindPlayer(){
      arrow = false;
   }
   
   public void unblindPlayer(){
      arrow = true;
   }
   
   public void silencePlayer(){
      slowBow = false;
      sheepBow = false;
      blindBow = false;
   }
   
   public void unsilencePlayer(){
      slowBow = true;
      sheepBow = true;
      blindBow = true;
   }
}
