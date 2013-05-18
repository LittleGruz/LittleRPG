package littlegruz.arpeegee.entities;

public class RPGRangedPlayer extends RPGPlayer{
   private boolean egg, bow, slow, blind, sheep;
   private int onHit;

   public RPGRangedPlayer(String name){
      super(name);
      onHit = 0;
      egg = true;
      bow = true;
      slow = true;
      blind = true;
      sheep = true;
   }

   public RPGRangedPlayer(String name, int level, float attack, String incompleteQuests, String completeQuests, String party){
      super(name, level, attack, incompleteQuests, completeQuests, party);
      onHit = 0;
      egg = true;
      bow = true;
      slow = true;
      blind = true;
      sheep = true;
   }

   public boolean isEggReady(){
      return egg;
   }
   
   public void setEggReadiness(boolean egg){
      this.egg = egg;
   }
   
   public boolean isBowReady(){
      return bow;
   }

   public void setBowReadiness(boolean bow){
      this.bow = bow;
   }

   public boolean isSlowReady(){
      return slow;
   }

   public void setSlowReadiness(boolean slow){
      this.slow = slow;
   }

   public boolean isBlindReady(){
      return blind;
   }

   public void setBlindReadiness(boolean blind){
      this.blind = blind;
   }

   public boolean isSheepReady(){
      return sheep;
   }

   public void setSheepReadiness(boolean sheep){
      this.sheep = sheep;
   }

   public void blindPlayer(){
      bow = false;
   }
   
   public void unblindPlayer(){
      bow = true;
   }
   
   public int getOnHit(){
      return onHit;
   }
   
   /* 0 normal; 1 slow; 2 blind; 3 sheep*/
   public void setOnHit(int hit){
      onHit = hit;
   }
}
