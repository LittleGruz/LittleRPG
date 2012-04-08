package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGClass;
import littlegruz.arpeegee.entities.RPGSubClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Create implements CommandExecutor{
private ArpeegeeMain plugin;
   
   public Create(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args) {
      if(sender.hasPermission("arpeegee.create")){
         // Add a class
         if(cmd.getName().compareToIgnoreCase("addclass") == 0){
            if(args.length == 4){
               if(plugin.getClassMap().get(args[0]) == null){
                  plugin.getClassMap().put(args[0], new RPGClass(args[0],
                        Double.parseDouble(args[1]), Double.parseDouble(args[2]),
                        Double.parseDouble(args[3])));
                  sender.sendMessage("Class addition successful");
               }
               else
                  sender.sendMessage("A class with the name \"" + args[0] + "\" already exists");
            }
            else
               sender.sendMessage("Wrong number of parameters");
         }
         // Add a sub-class
         else if(cmd.getName().compareToIgnoreCase("addsubclass") == 0){
            if(args.length == 8){
               if(plugin.getSubClassMap().get(args[0]) == null){
                  plugin.getSubClassMap().put(args[0], new RPGSubClass(args[0],
                        Double.parseDouble(args[1]), Double.parseDouble(args[2]),
                        Double.parseDouble(args[3]), Double.parseDouble(args[4]),
                        Double.parseDouble(args[5])));
                  sender.sendMessage("Sub-class addition successful");
               }
               else
                  sender.sendMessage("A sub-class with the name \"" + args[0] + "\" already exists");
            }
            else
               sender.sendMessage("Wrong number of parameters");
         }
      }
      else
         sender.sendMessage("You do not have enough permissions for this command");
      return true;
   }
}
