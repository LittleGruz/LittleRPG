package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, incomplete, complete;
   private int level, party;
   private RPGSubClass subObj;

   // New RPGPlayer
   public RPGPlayer(String name, RPGSubClass subClassObj){
      this.name = name;
      subObj = subClassObj;
      level = 1;
      incomplete = "-1";
      complete = "-1";
      party = -1;
   }
   
   // Restoring an RPGPlayer from a saved state (with party id [unimplemented])
   public RPGPlayer(String name, RPGSubClass subClassObj, int level, String incomplete, String complete, int party){
      this.name = name;
      subObj = subClassObj;
      this.level = level;
      this.incomplete = incomplete;
      this.complete = complete;
      this.party = party;
   }

   public String getName() {
      return name;
   }

   public RPGSubClass getSubClassObject() {
      return subObj;
   }
   
   public int getLevel(){
      return level;
   }
   
   public void setLevel(int lvl){
      level = lvl;
   }

   public int getParty(){
      return party;
   }

   public void setParty(int party){
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
}
