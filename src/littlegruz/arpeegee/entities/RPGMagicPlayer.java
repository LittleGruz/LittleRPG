package littlegruz.arpeegee.entities;

public class RPGMagicPlayer extends RPGPlayer{
   private int buildUp;
   private boolean heal, lightning, fire, sheeeep;

   public RPGMagicPlayer(String name){
      super(name);
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
      buildUp = 0;
   }

   public RPGMagicPlayer(String name, int level, int gear, int buildUp, String incomplete, String complete, String party){
      super(name, level, gear, incomplete, complete, party);
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
      this.buildUp = buildUp;
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
   
   public void blindPlayer(){
      heal = false;
      lightning = false;
      fire = false;
      sheeeep = false;
   }
   
   public void unblindPlayer(){
      heal = true;
      lightning = true;
      fire = true;
      sheeeep = true;
   }
}
