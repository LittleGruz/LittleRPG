package littlegruz.arpeegee.entities;

public class RPGMagicPlayer extends RPGPlayer{
   private int buildUp;
   private boolean heal, advHeal, lightning, advLightning, fire, sheeeep;

   public RPGMagicPlayer(String name){
      super(name);
      heal = true;
      advHeal = true;
      lightning = true;
      advLightning = true;
      fire = true;
      sheeeep = true;
   }

   public RPGMagicPlayer(String name, int level, int gear, String incomplete, String complete, String party){
      super(name, level, gear, incomplete, complete, party);
      heal = true;
      advHeal = true;
      lightning = true;
      advLightning = true;
      fire = true;
      sheeeep = true;
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

   public boolean isAdvHealReady(){
      return advHeal;
   }

   public void setAdvHealReadiness(boolean advHeal){
      this.advHeal = advHeal;
   }

   public boolean isLightningReady(){
      return lightning;
   }

   public void setLightningReadiness(boolean lightning){
      this.lightning = lightning;
   }

   public boolean isAdvLightningReady(){
      return advLightning;
   }

   public void setAdvLightningReadiness(boolean advLightning){
      this.advLightning = advLightning;
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

}
