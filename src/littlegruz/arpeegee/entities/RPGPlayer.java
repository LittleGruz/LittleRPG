package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, classObj;
   private int level, rage;
   private RPGSubClass subObj;

   // New RPGPlayer
   public RPGPlayer(String name, String classObj, RPGSubClass subClassObj){
      this.name = name;
      this.classObj = classObj;
      subObj = subClassObj;
      level = 1;
      rage = 0;
   }
   
   // Restoring an RPGPlayer from a saved state
   public RPGPlayer(String name, String classObj, RPGSubClass subClassObj, int level, int rage){
      this.name = name;
      this.classObj = classObj;
      subObj = subClassObj;
      this.level = level;
      this.rage = rage;
   }

   public String getName() {
      return name;
   }

   public String getClassName() {
      return classObj;
   }

   public RPGSubClass getSubClassObject() {
      return subObj;
   }
   
   public int getLevel(){
      return level;
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
}
