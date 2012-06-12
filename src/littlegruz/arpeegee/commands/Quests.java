package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Quests implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Quests(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(sender.hasPermission("arpeegee.questing")){
         if(cmd.getName().compareToIgnoreCase("setquest") == 0){
            if(args.length == 1){
               try{
               if(plugin.getQuestMap().get(Integer.parseInt(args[0])) != null){
                  plugin.setQuestNumberToSet(Integer.parseInt(args[0]));
                  plugin.setQuestCanSet(true);
                  sender.sendMessage("Right click with your fist to set the quest giving block");
               }
               else
                  sender.sendMessage("No quest found with that number");
               }catch(NumberFormatException e){
                  sender.sendMessage("Please use a valid number as the quest number");
               }
            }
            else
               sender.sendMessage("Wrong number of arguments");
         }
      }
      else
         sender.sendMessage("You do not have sufficient permissions");
      return true;
   }

}
