package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name;
   private int level;
   private RPGSubClass subObj;

   // New RPGPlayer
   public RPGPlayer(String name, RPGSubClass subClassObj){
      this.name = name;
      subObj = subClassObj;
      level = 1;
   }
   
   // Restoring an RPGPlayer from a saved state
   public RPGPlayer(String name, RPGSubClass subClassObj, int level){
      this.name = name;
      subObj = subClassObj;
      this.level = level;
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
}
