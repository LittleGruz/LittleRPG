package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGPlayer;
import littlegruz.arpeegee.entities.RPGSubClass;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Begin implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Begin(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(sender.hasPermission("arpeegee.begin") && !(sender instanceof ConsoleCommandSender)){
         if(cmd.getName().compareToIgnoreCase("iammelee") == 0){
            plugin.getPlayerMap().put(sender.getName(), new RPGPlayer(sender.getName(), "Melee", new RPGSubClass("Warrior",1,0,0,1,0)));
            plugin.getServer().broadcastMessage("Go forth to hack and slash to your hearts content! This weapon may help.");
            plugin.getServer().broadcastMessage("*pocketed*");
            //Give player iron sword
         }
         else if(cmd.getName().compareToIgnoreCase("iamranged") == 0){
            plugin.getPlayerMap().put(sender.getName(), new RPGPlayer(sender.getName(), "Ranged", new RPGSubClass("Archer",1,0,0,1,0)));
            plugin.getServer().broadcastMessage("Time to prescribe some fatal acupuncture!");
            plugin.getServer().broadcastMessage("...Hey! Give me back my bow!");
            //Give player bow and arrows
         }
         else if(cmd.getName().compareToIgnoreCase("iammagic") == 0){
            plugin.getPlayerMap().put(sender.getName(), new RPGPlayer(sender.getName(), "Magic", new RPGSubClass("Wizard",1,0,0,1,0)));
            plugin.getServer().broadcastMessage("You will now be imbued with the knowledge of zap!");
            plugin.getServer().broadcastMessage("*knowledged*");
            //Give player yellow dye
         }
      }
      return true;
   }

}
