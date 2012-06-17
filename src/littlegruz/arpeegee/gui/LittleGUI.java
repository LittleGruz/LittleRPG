package littlegruz.arpeegee.gui;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

import littlegruz.arpeegee.ArpeegeeMain;

public class LittleGUI{
   private ArpeegeeMain plugin;
   private GenericLabel intro;
   private GenericButton melee;
   private GenericButton magic;
   private GenericButton ranged;
   private GenericPopup introScreen;
   
   public LittleGUI(ArpeegeeMain instance){
      intro = new GenericLabel();
      melee = new GenericButton("Melee");
      magic = new GenericButton("Magic");
      ranged = new GenericButton("Ranged");
      introScreen = new GenericPopup();
      
      plugin = instance;
      
      intro.setX(10);
      intro.setY(20);
      intro.setWidth(300); // About 80 characters
      intro.setHeight(40);
      intro.setText(addNewLines(plugin.getTextsMap().get("intro")));
      
      melee.setX(160);
      melee.setY(80);
      melee.setWidth(100);
      melee.setHeight(20);
      
      magic.setX(160);
      magic.setY(120);
      magic.setWidth(100);
      magic.setHeight(20);
      
      ranged.setX(160);
      ranged.setY(160);
      ranged.setWidth(100);
      ranged.setHeight(20);
      
      introScreen.attachWidget(plugin, intro);
      introScreen.attachWidget(plugin, melee);
      introScreen.attachWidget(plugin, magic);
      introScreen.attachWidget(plugin, ranged);
   }

   public void attachIntro(Player playa){
      SpoutPlayer sp = SpoutManager.getPlayer(playa);

      sp.getMainScreen().attachPopupScreen(introScreen);
   }
   
   public String addNewLines(String text){
      int prev, curr;
      
      prev = 0;
      curr = 0;
      
      for(curr = 0; curr < text.length(); curr++){
         if(text.charAt(curr) == ' '){
            // If the previous word pushes it over the 80th character, add a new line character
            if(curr % 80 < prev % 80){
               text = text.substring(0, prev) + "\n" + text.substring(prev + 1, text.length());
               prev = curr;
            }
            else
               prev = curr;
         }
      }
      
      return text;
   }
}
