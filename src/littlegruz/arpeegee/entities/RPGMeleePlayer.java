package littlegruz.arpeegee.entities;

public class RPGMeleePlayer extends RPGPlayer{
   private int rage;
   
   public RPGMeleePlayer(String name, RPGSubClass subClassObj){
      super(name, subClassObj);
      rage = 0;
   }

   public RPGMeleePlayer(String name, RPGSubClass subClassObj,
         int level, int rage){
      super(name, subClassObj, level);
      this.rage = rage;
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

}
