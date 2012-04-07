package littlegruz.arpeegee.entities;

public class RPGSubClass {
   private String name;
   private double arch, blade, egg, farm, health, heal, mining;
   
   public RPGSubClass(String name, double archery, double blade, double egg,
         double farming, double health, double healing, double mining){
      this.name = name;
      arch = archery;
      this.blade = blade;
      this.egg = egg;
      farm = farming;
      this.health = health;
      heal = healing;
      this.mining = mining;
   }

   public String getName() {
      return name;
   }

   public double getArch() {
      return arch;
   }

   public double getBlade() {
      return blade;
   }

   public double getEgg() {
      return egg;
   }

   public double getFarm() {
      return farm;
   }

   public double getHealth() {
      return health;
   }

   public double getHeal() {
      return heal;
   }

   public double getMining() {
      return mining;
   }
}
