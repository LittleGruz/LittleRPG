package littlegruz.arpeegee.entities;

public class RPGSubClass {
   private String name;
   private double arch, blade, block, egg, spell;

   public RPGSubClass(String name, double archery, double blade, double block, double egg,
         double spell){
      this.name = name;
      arch = archery;
      this.blade = blade;
      this.block = block;
      this.egg = egg;
      this.spell = spell;
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

   public double getBlock() {
      return block;
   }

   public double getEgg() {
      return egg;
   }

   public double getSpell() {
      return spell;
   }
}
