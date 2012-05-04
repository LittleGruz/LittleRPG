package littlegruz.arpeegee.unused;

public class RPGClass {
   private String name;
   private double str, acc, intel;
   
   public RPGClass(String name, double strength, double accuracy, double intelligence){
      this.name = name;
      str = strength;
      acc = accuracy;
      intel = intelligence;
   }

   public String getName() {
      return name;
   }

   public double getStr() {
      return str;
   }

   public double getAcc() {
      return acc;
   }

   public double getIntel() {
      return intel;
   }
}
