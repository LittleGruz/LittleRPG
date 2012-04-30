package littlegruz.arpeegee.entities;

public class RPGRangedPlayer extends RPGPlayer{
   private boolean egg; 

   public RPGRangedPlayer(String name, RPGSubClass subClassObj){
      super(name, subClassObj);
      egg = true;
   }

   public RPGRangedPlayer(String name, RPGSubClass subClassObj, int level){
      super(name, subClassObj, level);
      egg = true;
   }

   public boolean isEggReady(){
      return egg;
   }
   
   public void setEggReadiness(boolean egg){
      this.egg = egg;
   }
}
