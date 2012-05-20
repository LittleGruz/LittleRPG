package littlegruz.arpeegee.entities;

import java.util.List;

public class RPGQuest{
   int pq, rl, parts;
   String ri, fc;
   List<String> diag;
   
   public RPGQuest(int prerequisitQuest, int requiredLevel, int parts, String requiredItem, List<String> dialogue, String finishingConditions){
      pq = prerequisitQuest;
      rl = requiredLevel;
      this.parts = parts;
      ri = requiredItem;
      diag = dialogue;
      fc = finishingConditions;
   }

   public int getPreQuest(){
      return pq;
   }

   public int getReqLevel(){
      return rl;
   }

   public String getReqItem(){
      return ri;
   }

   public List<String> getDiag(){
      return diag;
   }

   public String getFinishConds(){
      return fc;
   }
}
