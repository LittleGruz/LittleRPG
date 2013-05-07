package littlegruz.arpeegee.commands;

import littlegruz.arpeegee.ArpeegeeMain;
import littlegruz.arpeegee.entities.RPGParty;
import littlegruz.arpeegee.entities.RPGPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Party implements CommandExecutor{
   private ArpeegeeMain plugin;
   
   public Party(ArpeegeeMain instance){
      plugin = instance;
   }

   @Override
   public boolean onCommand(CommandSender sender, Command cmd,
         String commandLabel, String[] args){
      if(sender.hasPermission("rpg.party")){
         /* Create a party*/
         if(cmd.getName().compareToIgnoreCase("createparty") == 0){
            if(args.length > 0){
               if(plugin.getPartyMap().get(args[0]) == null){
                  RPGParty rpgp;

                  /* Leave old party if in one */
                  if(plugin.getRPGPlayer(sender.getName()) != null){
                     if(plugin.getRPGPlayer(sender.getName()).getParty().compareTo("none") != 0)
                        plugin.getServer().dispatchCommand(sender, "leaveparty");
                  }
                  else{
                     sender.sendMessage("You need to choose a class first");
                     return true;
                  }
                  
                  plugin.getPartyMap().put(args[0], new RPGParty(args[0]));
                  rpgp = plugin.getPartyMap().get(args[0]);
                  rpgp.addMember(sender.getName());
                  plugin.getRPGPlayer(sender.getName()).setParty(args[0]);
                  
                  sendInvite(args, rpgp, 1);
                  sender.sendMessage(args[0] + " party created");
               }
               else
                  sender.sendMessage("A party with that name already exists");
            }
            else
               sender.sendMessage("Not enough arguments");
         }
         /* Join a party */
         else if(cmd.getName().compareToIgnoreCase("joinparty") == 0){
            if(args.length > 0){
               RPGParty rpgp;
               
               rpgp = plugin.getPartyMap().get(args[0]);
               if(rpgp != null){
                  if(rpgp.isInvited(sender.getName())){
                     /* Leave old party if in one */
                     if(plugin.getRPGPlayer(sender.getName()) != null){
                        if(plugin.getRPGPlayer(sender.getName()).getParty().compareTo("none") != 0)
                           plugin.getServer().dispatchCommand(sender, "leaveparty");
                     }
                     else{
                        sender.sendMessage("You need to choose a class first");
                        return true;
                     }
                     
                     rpgp.addMember(sender.getName());
                     rpgp.removeInvitation(sender.getName());
                     plugin.getRPGPlayer(sender.getName()).setParty(args[0]);
                     sender.sendMessage("Party joined");
                  }
                  else
                     sender.sendMessage("You have not been invited to that party"); 
               }
               else
                  sender.sendMessage("That party does not exist");
            }
            else
               sender.sendMessage("Not enough arguments");
         }
         /* Leave a party */
         else if(cmd.getName().compareToIgnoreCase("leaveparty") == 0){
            if(plugin.getRPGPlayer(sender.getName()) != null){
               String party;
               
               party = plugin.getRPGPlayer(sender.getName()).getParty();
               if(plugin.getPartyMap().get(party) != null){
                  plugin.getPartyMap().get(party).removeMember(sender.getName());
                  plugin.getRPGPlayer(sender.getName()).setParty("none");
                  sender.sendMessage("You left party " + party);
                  
                  /* Check if the player was the last one to leave*/
                  if(plugin.getPartyMap().get(party).getMembers().size() == 0){
                     plugin.getPartyMap().remove(party);
                     sender.sendMessage("You were the last party member. Party disbanded");
                  }
               }
               else
                  sender.sendMessage("Your party seems to have already disbanded");
            }
            else
               sender.sendMessage("You need to choose a class first");
         }
         /* Issue party invite(s) */
         else if(cmd.getName().compareToIgnoreCase("sendpartyinvite") == 0){
            if(args.length > 0){
               String party;
               
               if(plugin.getRPGPlayer(sender.getName()) != null){
                  party = plugin.getRPGPlayer(sender.getName()).getParty();
                  if(plugin.getPartyMap().get(party) != null)
                     sendInvite(args, plugin.getPartyMap().get(party), 0);
                  else
                     sender.sendMessage("You must be in a party to invite someone");
               }
            }
            else
               sender.sendMessage("Not enough arguments");
         }
         /* Remove party invite(s) */
         else if(cmd.getName().compareToIgnoreCase("removepartyinvite") == 0){
            if(args.length > 0){
               RPGParty rpgp;
               String party;
               int i;
               
               if(plugin.getRPGPlayer(sender.getName()) != null){
                  party = plugin.getRPGPlayer(sender.getName()).getParty();
                  if(plugin.getPartyMap().get(party) != null){
                     rpgp = plugin.getPartyMap().get(party);
                     
                     for(i = 0; i < args.length; i++){
                        rpgp.removeInvitation(args[i]);
                        
                        /* Send invitation cancellation message to player if they are online */
                        if(plugin.getServer().getPlayer(args[i]) != null)
                           plugin.getServer().getPlayer(args[i]).sendMessage("Your invitation to join " + party + " has been revoked");
                     }
                  }
               }
               else
                  sender.sendMessage("You must be in a party to remove inviations");
            }
            else
               sender.sendMessage("Not enough arguments");
         }
         else if(cmd.getName().compareToIgnoreCase("party") == 0){
            if(sender instanceof Player){
               RPGPlayer rpgp;
               
               rpgp = plugin.getRPGPlayer(((Player) sender).getName());
               
               if(rpgp != null){
                  if(rpgp.getChat() == 0){
                     rpgp.setChat(1);
                     sender.sendMessage("Party chat set");
                  }
                  else{
                     rpgp.setChat(0);
                     sender.sendMessage("Party chat unset");
                  }
               }
            }
         }
      }
      return true;
   }
   
   private void sendInvite(String[] args, RPGParty rpgp, int i){
      while(i < args.length){
         rpgp.addInvitation(args[i]);
         /* Send invitation message to player if they are online */
         if(plugin.getServer().getPlayer(args[i]) != null)
            plugin.getServer().getPlayer(args[i]).sendMessage("You have been invited to join " + rpgp.getName());
         i++;
      }
   }
}
