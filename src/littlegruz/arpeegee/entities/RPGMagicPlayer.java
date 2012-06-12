package littlegruz.arpeegee.entities;

public class RPGMagicPlayer extends RPGPlayer{
   private boolean heal, advHeal, lightning, advLightning, tele, fire, sheeeep;

   public RPGMagicPlayer(String name, RPGSubClass subClassObj){
      super(name, subClassObj);
      heal = true;
      advHeal = true;
      lightning = true;
      advLightning = true;
      tele = true;
      fire = true;
      sheeeep = true;
   }

   public RPGMagicPlayer(String name, RPGSubClass subClassObj, int level, String incomplete, String complete, int party){
      super(name, subClassObj, level, incomplete, complete, party);
      heal = true;
      advHeal = true;
      lightning = true;
      advLightning = true;
      tele = true;
      fire = true;
      sheeeep = true;
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

   public boolean isTeleportReady(){
      return tele;
   }

   public void setTeleportReadiness(boolean tele){
      this.tele = tele;
   }

   public boolean isFireballReady(){
      return fire;
   }

   public void setFireballReadiness(boolean fire){
      this.fire = fire;
   }

   public boolean isSheepReady(){
      return sheeeep;
   }

   public void setSheepReadiness(boolean sheep){
      this.sheeeep = sheep;
   }

}
