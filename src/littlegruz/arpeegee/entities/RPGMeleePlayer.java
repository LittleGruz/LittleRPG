package littlegruz.arpeegee.entities;

public class RPGMeleePlayer extends RPGPlayer{
   private int rage;
   private boolean jump, silence, punch, imob;
   
   public RPGMeleePlayer(String name){
      super(name);
      rage = 0;
      jump = true;
      silence = true;
      punch = true;
      imob = true;
   }

   public RPGMeleePlayer(String name, int level, int gear, int rage, String incomplete, String complete, String party){
      super(name, level, gear, incomplete, complete, party);
      this.rage = rage;
      jump = true;
      silence = true;
      punch = true;
      imob = true;
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

   public boolean isJumpReady(){
      return jump;
   }

   public void setJumpReadiness(boolean jump){
      this.jump = jump;
   }

   public boolean isSilenceReady(){
      return silence;
   }

   public void setSilenceReadiness(boolean silence){
      this.silence = silence;
   }

   public boolean isPunchReady(){
      return punch;
   }

   public void setPunchReadiness(boolean punch){
      this.punch = punch;
   }

   public boolean isImobiliseReady(){
      return imob;
   }

   public void setImobiliseReadiness(boolean imob){
      this.imob = imob;
   }
}
