package littlegruz.arpeegee.entities;

import java.util.ArrayList;

public class RPGQuest{
   int pq, rl, quest;
   String ri, fc, rew;
   ArrayList<String> dialo;
   
   public RPGQuest(int questNumber, int prerequisitQuest, int requiredLevel, String requiredItem, ArrayList<String> dialogue, String finishingConditions, String reward){
      quest = questNumber;
      pq = prerequisitQuest;
      rl = requiredLevel;
      ri = requiredItem;
      dialo = dialogue;
      fc = finishingConditions;
      rew = reward;
   }

   public int getQuestNumber(){
      return quest;
   }

   public int getPrerequisiteQuest(){
      return pq;
   }

   public int getRequiredLevel(){
      return rl;
   }

   public String getRequiredItem(){
      return ri;
   }

   public ArrayList<String> getDialogue(){
      return dialo;
   }

   public String getFinishConditions(){
      return fc;
   }

   public String getReward(){
      return rew;
   }
}
