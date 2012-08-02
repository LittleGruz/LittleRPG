package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGMagicPlayer;
import littlegruz.arpeegee.entities.RPGMeleePlayer;
import littlegruz.arpeegee.entities.RPGRangedPlayer;
import littlegruz.arpeegee.entities.RPGSubClass;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Begin implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Begin(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(sender.hasPermission("rpg.begin") && sender instanceof Player){
         if(plugin.getMeleePlayerMap().get(sender.getName()) == null
            && plugin.getRangedPlayerMap().get(sender.getName()) == null
            && plugin.getMagicPlayerMap().get(sender.getName()) == null){
            // Sets the players class as melee
            if(cmd.getName().compareToIgnoreCase("ichoosemelee") == 0){
               plugin.getMeleePlayerMap().put(sender.getName(), new RPGMeleePlayer(sender.getName(), new RPGSubClass("Warrior",0,1,1,0,0)));
               sender.sendMessage("Go forth to hack and slash to your hearts content!");
               sender.sendMessage("This weapon may help.");
               sender.sendMessage("*pocketed*");
               //Give player iron sword
               ((Player) sender).getInventory().setItem(0, new ItemStack(Material.IRON_SWORD,1));
            }
            // Sets the players class as ranged
            else if(cmd.getName().compareToIgnoreCase("ichooseranged") == 0){
               plugin.getRangedPlayerMap().put(sender.getName(), new RPGRangedPlayer(sender.getName(), new RPGSubClass("Archer",1,0,0,1,0)));
               sender.sendMessage("Ah! You also like to attack from afar.");
               sender.sendMessage("I have a fantastic bow kept at my house over there.");
               sender.sendMessage("...Hey! Come back! Give me back my bow!");
               //Give player bow and arrows
               ((Player) sender).getInventory().setItem(0, new ItemStack(Material.BOW,1));
               ((Player) sender).getInventory().setItem(9, new ItemStack(Material.ARROW,10));
            }
            // Makes it all complete
            else if(cmd.getName().compareToIgnoreCase("ichoosemagic") == 0){
               plugin.getMagicPlayerMap().put(sender.getName(), new RPGMagicPlayer(sender.getName(), new RPGSubClass("Wizard",0,0,0,0,1)));
               sender.sendMessage("Aight, you put on your robe and wizard hat.");
               sender.sendMessage("*knowledged*");
               //Give player yellow dye
               ItemStack is = new ItemStack(351,1);
               is.setDurability((short) 11);
               ((Player) sender).getInventory().setItem(0, is);
            }
         }
         else
            sender.sendMessage("You have already chosen a class!");
      }
      else
         sender.sendMessage("You do not have sufficient permissions");
      return true;
   }

}
