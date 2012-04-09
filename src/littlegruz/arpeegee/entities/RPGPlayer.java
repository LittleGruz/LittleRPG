package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, className, subClassName;
   private int level, rage;

   public RPGPlayer(String name, String className, String subClassName){
      this.name = name;
      this.className = className;
      this.subClassName = subClassName;
      level = 1;
      rage = 0;
   }
   
   public RPGPlayer(String name, String className, String subClassName, int level){
      this.name = name;
      this.className = className;
      this.subClassName = subClassName;
      this.level = level;
      rage = 0;
   }

   public String getName() {
      return name;
   }

   public String getClassName() {
      return className;
   }

   public String getSubClassName() {
      return subClassName;
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
