package littlegruz.arpeegee.commands;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGClass;
import littlegruz.arpeegee.entities.RPGSubClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Display implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Display(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args) {
      RPGClass rpgClass;
      RPGSubClass rpgSubClass;
      
      if(sender.hasPermission("arpeegee.display")){
         if(cmd.getName().compareToIgnoreCase("displayclass") == 0){
            // Just display the names of the classes
            if(args.length == 0){
               sender.sendMessage("List of all the classes:");
               Iterator<Map.Entry<String, RPGClass>> it = plugin.getClassMap().entrySet().iterator();
               while(it.hasNext()){
                  Entry<String, RPGClass> classIter = it.next();
                  sender.sendMessage(classIter.getKey());
               }
               sender.sendMessage("Use /displayclass [name] to see the stats");
            }
            // Display the statistics of the selected class
            else{
               rpgClass = plugin.getClassMap().get(args[0]);
               sender.sendMessage("The " + args[0] + " class stats:");
               sender.sendMessage("Strength: " + rpgClass.getStr());
               sender.sendMessage("Accuracy: " + rpgClass.getAcc());
               sender.sendMessage("Intelligence: " + rpgClass.getIntel());
            }
         }
         else if(cmd.getName().compareToIgnoreCase("displaysubclass") == 0){
            // Just display the names of the sub-classes
            if(args.length == 0){
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
               sender.sendMessage("Egg: " + rpgSubClass.getEgg());
               sender.sendMessage("Farming: " + rpgSubClass.getFarm());
               sender.sendMessage("Health: " + rpgSubClass.getHealth());
               sender.sendMessage("Healing: " + rpgSubClass.getHeal());
               sender.sendMessage("Mining: " + rpgSubClass.getMining());
            }
         }
      }
      else
         sender.sendMessage("You do not have enough permissions for this command");
      return true;
   }
}
