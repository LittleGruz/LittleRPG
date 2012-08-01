package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, incomplete, complete, party;
   private int level;
   private RPGSubClass subObj;

   // New RPGPlayer
   public RPGPlayer(String name, RPGSubClass subClassObj){
      this.name = name;
      subObj = subClassObj;
      level = 1;
      incomplete = "none";
      complete = "none";
      party = "none";
   }
   
   // Restoring an RPGPlayer from a saved state (with party id [unimplemented])
   public RPGPlayer(String name, RPGSubClass subClassObj, int level, String incomplete, String complete, String party){
      this.name = name;
      subObj = subClassObj;
      this.level = level;
      this.incomplete = incomplete;
      this.complete = complete;
      // TODO Remove after enough updates (put in at v1.5.1)
      if(party.compareTo("-1") == 0)
         this.party = "none";
      else
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
}
