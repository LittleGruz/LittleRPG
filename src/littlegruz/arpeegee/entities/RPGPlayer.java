package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, incomplete, complete;
   private int level, quest, party;
   private RPGSubClass subObj;

   // New RPGPlayer
   public RPGPlayer(String name, RPGSubClass subClassObj){
      this.name = name;
      subObj = subClassObj;
      level = 1;
      quest = -1;
      party = -1;
      incomplete = "0";
   }
   
   // Restoring an RPGPlayer from a saved state (with party id [unimplemented])
   public RPGPlayer(String name, RPGSubClass subClassObj, int level, int quest, String incomplete, String complete, int party){
      this.name = name;
      subObj = subClassObj;
      this.level = level;
      this.quest = quest;
      this.party = party;
      this.incomplete = incomplete;
      this.complete = complete;
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

   public int getQuest(){
      return quest;
   }

   public int getParty(){
      return party;
   }

   public void setQuest(int quest){
      this.quest = quest;
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
