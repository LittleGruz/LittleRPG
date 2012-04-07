package littlegruz.arpeegee.entities;

public class RPGPlayer {
   private String name, className, subClassName;
   private int level;

   public RPGPlayer(String name, String className, String subClassName){
      this.name = name;
      this.className = className;
      this.subClassName = subClassName;
      level = 1;
   }
   
   public RPGPlayer(String name, String className, String subClassName, int level){
      this.name = name;
      this.className = className;
      this.subClassName = subClassName;
      this.level = level;
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
}
