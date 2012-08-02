package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Worlds implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Worlds(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(sender.hasPermission("rpg.world")){
         if(sender instanceof Player){
            Player playa = (Player) sender;
            if(cmd.getName().compareToIgnoreCase("addrpgworld") == 0){
               if(plugin.getWorldsMap().get(playa.getWorld().getName()) != null)
                  playa.sendMessage("This world is already added");
               else{
                  plugin.getWorldsMap().put(playa.getWorld().getName(), playa.getWorld().getName());
                  playa.sendMessage("World added");
               }
            }
            else if(cmd.getName().compareToIgnoreCase("removerpgworld") == 0){
               if(plugin.getWorldsMap().get(playa.getWorld().getName()) == null)
                  playa.sendMessage("This world has not been added yet");
               else{
                  plugin.getWorldsMap().remove(playa.getWorld().getName());
                  playa.sendMessage("World removed");
               }
            }
         }
         else
            sender.sendMessage("Please do not use this command from the console");
      }
      else
         sender.sendMessage("You do not have sufficient permissions");
      return true;
   }

}
