package littlegruz.arpeegee;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RPGCommands implements CommandExecutor {

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args) {
      if(cmd.getName().compareToIgnoreCase("testy") == 0){
         sender.sendMessage("Command!");
         return true;
         /*if(sender.hasPermission("saveme.savetheworld")){
            
         }
         else
            sender.sendMessage("You do not have permissions for this command");*/
      }
      return false;
   }

}
