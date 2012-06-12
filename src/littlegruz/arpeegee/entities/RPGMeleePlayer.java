package littlegruz.arpeegee.entities;

public class RPGMeleePlayer extends RPGPlayer{
   private int rage;
   private boolean attack;
   
   public RPGMeleePlayer(String name, RPGSubClass subClassObj){
      super(name, subClassObj);
      rage = 0;
      attack = true;
   }

   public RPGMeleePlayer(String name, RPGSubClass subClassObj, int level, int rage, String incomplete, String complete, int party){
      super(name, subClassObj, level, incomplete, complete, party);
      this.rage = rage;
      attack = true;
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

   public boolean isAttackReady(){
      return attack;
   }

   public void setAttackReadiness(boolean attack){
      this.attack = attack;
   }
}
