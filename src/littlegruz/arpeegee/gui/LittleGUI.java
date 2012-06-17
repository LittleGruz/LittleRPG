package littlegruz.arpeegee.gui;

import org.spout.api.gui.widget.GenericLabel;

import littlegruz.arpeegee.ArpeegeeMain;

public class LittleGUI{
   private ArpeegeeMain plugin;
   private GenericLabel intro;
   
   public LittleGUI(ArpeegeeMain instance){
      plugin = instance;
      intro = new GenericLabel(plugin.getTextsMap().get("intro"));
   }

   public void setIntro(GenericLabel intro){
      this.intro = intro;
   }

   public GenericLabel getIntro(){
      return intro;
   }
}
