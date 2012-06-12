package littlegruz.arpeegee.entities;

public class RPGRangedPlayer extends RPGPlayer{
   private boolean egg, fireBow; 

   public RPGRangedPlayer(String name, RPGSubClass subClassObj){
      super(name, subClassObj);
      egg = true;
      fireBow = true;
   }

   public RPGRangedPlayer(String name, RPGSubClass subClassObj, int level, int quest, int party){
      super(name, subClassObj, level, quest, party);
      egg = true;
      fireBow = true;
   }

   public boolean isEggReady(){
      return egg;
   }
   
   public void setEggReadiness(boolean egg){
      this.egg = egg;
   }

   public boolean isFireBowReady(){
      return fireBow;
   }
   
   public void setFireBowReadiness(boolean fire){
      fireBow = egg;
   }
}
