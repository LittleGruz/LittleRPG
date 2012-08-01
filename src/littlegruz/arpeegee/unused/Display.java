package littlegruz.arpeegee.unused;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGSubClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
public class Display implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Display(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      RPGSubClass rpgSubClass;
      
      if(sender.hasPermission("arpeegee.display")){
         // Display sub-class info
         if(cmd.getName().compareToIgnoreCase("displaysubclass") == 0){
            // Just display the names of the sub-classes
            /*if(args.length == 0){
               sender.sendMessage("List of all the sub-classes:");
               Iterator<Map.Entry<String, RPGSubClass>> it = plugin.getSubClassMap().entrySet().iterator();
               while(it.hasNext()){
                  Entry<String, RPGSubClass> subClassIter = it.next();
                  sender.sendMessage(subClassIter.getKey());
               }
               sender.sendMessage("Use /displaysubclass [name] to see the stats");
            }
            // Display the statistics of the selected sub-class
            else{
               rpgSubClass = plugin.getSubClassMap().get(args[0]);
               sender.sendMessage("The " + args[0] + " sub-class stats:");
               sender.sendMessage("Archery: " + rpgSubClass.getArch());
               sender.sendMessage("Blade: " + rpgSubClass.getBlade());
               sender.sendMessage("Block: " + rpgSubClass.getBlock());
               sender.sendMessage("Egg: " + rpgSubClass.getEgg());
               sender.sendMessage("Spells: " + rpgSubClass.getSpell());
            }*/
         }
      }
      else
         sender.sendMessage("You do not have sufficient permissions");
      return true;
   }
}
