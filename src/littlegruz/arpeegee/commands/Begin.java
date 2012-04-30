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
      if(sender.hasPermission("arpeegee.begin") && sender instanceof Player){
         if(cmd.getName().compareToIgnoreCase("iammelee") == 0){
            plugin.getMeleePlayerMap().put(sender.getName(), new RPGMeleePlayer(sender.getName(), new RPGSubClass("Warrior",0,1,1,0,0)));
            plugin.getServer().broadcastMessage("Go forth to hack and slash to your hearts content! This weapon may help.");
            plugin.getServer().broadcastMessage("*pocketed*");
            //Give player iron sword
            ((Player) sender).getInventory().setItem(0, new ItemStack(Material.IRON_SWORD,1));
         }
         else if(cmd.getName().compareToIgnoreCase("iamranged") == 0){
            plugin.getRangedPlayerMap().put(sender.getName(), new RPGRangedPlayer(sender.getName(), new RPGSubClass("Archer",1,0,0,1,0)));
            plugin.getServer().broadcastMessage("Time to prescribe some fatal acupuncture!");
            plugin.getServer().broadcastMessage("...Hey! Give me back my bow!");
            //Give player bow and arrows
            ((Player) sender).getInventory().setItem(0, new ItemStack(Material.BOW,1));
            ((Player) sender).getInventory().setItem(9, new ItemStack(Material.ARROW,64));
            ((Player) sender).getInventory().setItem(10, new ItemStack(Material.ARROW,64));
         }
         else if(cmd.getName().compareToIgnoreCase("iammagic") == 0){
            plugin.getMagicPlayerMap().put(sender.getName(), new RPGMagicPlayer(sender.getName(), new RPGSubClass("Wizard",0,0,0,0,1)));
            plugin.getServer().broadcastMessage("Aight, you put on your robe and wizard hat.");
            plugin.getServer().broadcastMessage("*knowledged*");
            //Give player yellow dye
            ItemStack is = new ItemStack(351,1);
            is.setDurability((short) 11);
            ((Player) sender).getInventory().setItem(0, is);
         }
      }
      return true;
   }

}
