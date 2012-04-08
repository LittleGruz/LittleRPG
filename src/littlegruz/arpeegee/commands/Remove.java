package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Remove implements CommandExecutor {
   private ArpeegeeMain plugin;
   
   public Remove(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args) {
      if(sender.hasPermission("arpeegee.remove")){
         // Remove a class
         if(cmd.getName().compareToIgnoreCase("removeclass") == 0){
            if(plugin.getClassMap().remove(args[0]) == null)
               sender.sendMessage("A class with the name \"" + args[0] + "\" does not exist");
            else
               sender.sendMessage("Removal successful");
         }
         // Remove a sub-class
         else if(cmd.getName().compareToIgnoreCase("removesubclass") == 0){
            if(plugin.getSubClassMap().remove(args[0]) == null)
               sender.sendMessage("A sub-class with the name \"" + args[0] + "\" does not exist");
            else
               sender.sendMessage("Removal successful");
         }
      }
      else
         sender.sendMessage("You do not have enough permissions for this command");
      return true;
   }
}
