package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, incomplete, complete, party;
   private int level, chat;
   private float gear;
   private boolean move, baseAttack;

   // New RPGPlayer
   public RPGPlayer(String name){
      this.name = name;
      level = 1;
      gear = 1;
      chat = 0;
      incomplete = "none";
      complete = "none";
      party = "none";
      move = true;
   }
   
   // Restoring an RPGPlayer from a saved state
   public RPGPlayer(String name, int level, float gear, String incomplete, String complete, String party){
      this.name = name;
      this.level = level;
      this.gear = gear;
      this.incomplete = incomplete;
      this.complete = complete;
      this.party = party;
      move = true;
      chat = 0;
   }

   public String getName() {
      return name;
   }
   
   public int getLevel(){
      return level;
   }
   
   public void setLevel(int lvl){
      level = lvl;
   }
   
   public float getGearLevel(){
      return gear;
   }
   
   public void setGearLevel(float gear){
      this.gear = gear;
   }

   public String getParty(){
      return party;
   }

   public void setParty(String party){
      this.party = party;
   }

   public String getIncomplete(){
      return incomplete;
   }

   public void setIncomplete(String incomplete){
      this.incomplete = incomplete;
   }

   public String getComplete(){
      return complete;
   }

   public void setComplete(String complete){
      this.complete = complete;
   }

   public boolean canMove(){
      return move;
   }

   public void setMove(boolean move){
      this.move = move;
   }

   public boolean isBaseAttackReady(){
      return baseAttack;
   }

   public void setBaseAttackReadiness(boolean baseAttack){
      this.baseAttack = baseAttack;
   }

   /* 0 is normal global chat; 1 is party chat*/
   public int getChat(){
      return chat;
   }

   public void setChat(int chat){
      this.chat = chat;
   }
}
