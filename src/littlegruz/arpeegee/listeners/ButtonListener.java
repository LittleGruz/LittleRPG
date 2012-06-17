package littlegruz.arpeegee.listeners;

import littlegruz.arpeegee.ArpeegeeMain;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;

public class ButtonListener implements Listener{
   private ArpeegeeMain plugin;
   
   public ButtonListener(ArpeegeeMain instance){
      plugin = instance;
   }
   
   @EventHandler
   public void onButtonClick(ButtonClickEvent event){
      if(event.getButton().getText().compareToIgnoreCase("melee") == 0){
         event.getPlayer().getMainScreen().closePopup();
         plugin.getServer().dispatchCommand(event.getPlayer(), "ichoosemelee");
      }
      else if(event.getButton().getText().compareToIgnoreCase("magic") == 0){
         event.getPlayer().getMainScreen().closePopup();
         plugin.getServer().dispatchCommand(event.getPlayer(), "ichoosemagic");
      }
      else if(event.getButton().getText().compareToIgnoreCase("ranged") == 0){
         event.getPlayer().getMainScreen().closePopup();
         plugin.getServer().dispatchCommand(event.getPlayer(), "ichooseranged");
      }
   }
}
