package littlegruz.arpeegee.entities;

public class RPGRangedPlayer extends RPGPlayer{
   private boolean egg, bow;
   private int onHit;

   public RPGRangedPlayer(String name){
      super(name);
      onHit = 0;
      egg = true;
      bow = true;
   }

   public RPGRangedPlayer(String name, int level, float gear, String incompleteQuests, String completeQuests, String party){
      super(name, level, gear, incompleteQuests, completeQuests, party);
      onHit = 0;
      egg = true;
      bow = true;
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
