package littlegruz.arpeegee.gui;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;

import littlegruz.arpeegee.ArpeegeeMain;

public class RPGGUI{
   private ArpeegeeMain plugin;
   private GenericPopup popup;
   
   public RPGGUI(ArpeegeeMain instance){
      plugin = instance;
      popup = new GenericPopup();
   }
   
   public void chooseClass(Player player){
      SpoutPlayer sp = SpoutManager.getPlayer(player);
      GenericButton button = new GenericButton("Class1");
      
      button.setColor(new Color(255,255,255));
      button.setHoverColor(new Color(255,0,0));
      button.setX(100).setY(100);
      button.setWidth(100).setHeight(20);
      button.setWidth(100).setHeight(20);
      popup.attachWidget(plugin, button);
      sp.getMainScreen().attachPopupScreen(popup);
   }
}
