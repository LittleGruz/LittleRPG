package littlegruz.arpeegee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.logging.Logger;

import littlegruz.arpeegee.commands.Create;
import littlegruz.arpeegee.commands.Display;
import littlegruz.arpeegee.commands.Remove;
import littlegruz.arpeegee.entities.RPGClass;
import littlegruz.arpeegee.entities.RPGPlayer;
import littlegruz.arpeegee.entities.RPGSubClass;
import littlegruz.arpeegee.listeners.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

/* Create a custom RPG where the admin creates classes with the desired power
 * levels along with wizz-bang spells and attributes
 * 
 * If blade/archery/egg is high enough, chance (or increase chance) of crit
 * Add spells which get opened up as the player levels
 * Attributes like health and mana will be linked to strength and intelligence
 * 
 * Spells (so far):
 * Heal
 * Adv. Heal
 * Lightning (single)
 * Lightning (area)
 * Fireball
 * Teleport*/

/*The file which the classes are stored would have a format like the following
 * Name [name]
 * Strength [modifier]
 * Accuracy [modifier]
 * Intelligence [modifier]
 * 
 * e.g. For a warrior class it may look like this (after a few levels)
 * Name Warrior
 * Strength 3
 * Accuracy 0.5
 * Intelligence 0.5
 * 
 * Deals 3x damage with swinging weapons (always hitting), deals 0.5x ranged
 * damage with 50% chance to miss and 0.5x mana*/

/*And then for the sub-classes
 * Name [name]
 * Archery [modifier]
 * Blade [modifier]
 * Block [modifier]
 * Egg [modifier]
 * Spells [modifier]
 * 
 * e.g. For a Eggman sub-class it may look like this
 * Name Eggman
 * Archery 0.5
 * Blade 0.5
 * Block 0.5
 * Egg 5
 * Spells 0
 * 
 * The modifiers change the normal damage done by those weapons or activates
 * certain perks*/

public class ArpeegeeMain extends JavaPlugin {
   private Logger log = Logger.getLogger("This is MINECRAFT!");
   private File playerFile;
   private File classFile;
   private File subClassFile;
   private HashMap<String, RPGPlayer> playerMap;
   private HashMap<String, RPGClass> classMap;
   private HashMap<String, RPGSubClass> subClassMap;
   
   public void onEnable(){
      BufferedReader br;
      String input;
      StringTokenizer st;
      
      // Create the directory if needed
      new File(getDataFolder().toString()).mkdir();
      playerFile = new File(getDataFolder().toString() + "/player.txt");
      classFile = new File(getDataFolder().toString() + "/classes.txt");
      subClassFile = new File(getDataFolder().toString() + "/subclasses.txt");
      

      classMap = new HashMap<String, RPGClass>();
      // Load up the classes from file
      try{
         br = new BufferedReader(new FileReader(classFile));
         
         // Load class file data into the class HashMap
         while((input = br.readLine()) != null){
            String name;
            st = new StringTokenizer(input, " ");
            name = st.nextToken();
            classMap.put(name, new RPGClass(name,
                  Double.parseDouble(st.nextToken()),
                  Double.parseDouble(st.nextToken()),
                  Double.parseDouble(st.nextToken())));
         }
         br.close();

      }catch(FileNotFoundException e){
         log.info("No original Arpeegy class file found. One will be created for you");
      }catch(IOException e){
         log.info("Error reading Arpeegy class file");
      }catch(Exception e){
         log.info("Incorrectly formatted Arpeegy class file");
      }

      subClassMap = new HashMap<String, RPGSubClass>();
      // Load up the sub-classes from file
      try{
         br = new BufferedReader(new FileReader(subClassFile));
         
         // Load sub-class file data into the sub-class HashMap
         while((input = br.readLine()) != null){
            String name;
            st = new StringTokenizer(input, " ");
            name = st.nextToken();
            subClassMap.put(name, new RPGSubClass(name,
                  Double.parseDouble(st.nextToken()),
                  Double.parseDouble(st.nextToken()),
                  Double.parseDouble(st.nextToken()),
                  Double.parseDouble(st.nextToken()),
                  Double.parseDouble(st.nextToken())));
         }
         br.close();

      }catch(FileNotFoundException e){
         log.info("No original Arpeegy sub-class file found. One will be created for you");
      }catch(IOException e){
         log.info("Error reading Arpeegy sub-class file");
      }catch(Exception e){
         log.info("Incorrectly formatted Arpeegy sub-class file");
      }

      playerMap = new HashMap<String, RPGPlayer>();
      // Load up the players from file
      try{
         br = new BufferedReader(new FileReader(playerFile));
         
         // Load player file data into the player HashMap
         while((input = br.readLine()) != null){
            String name;
            String rpgClass, rpgSubClass;
            
            st = new StringTokenizer(input, " ");
            name = st.nextToken();
            rpgClass = st.nextToken();
            rpgSubClass = st.nextToken();
            
            if(classMap.get(rpgClass) == null)
               log.warning("Player " + name + " has an unmatched class name. Please fix this before they login.");
            if(subClassMap.get(rpgSubClass) == null)
               log.warning("Player " + name + " has an unmatched sub-class name. Please fix this before they login.");
            
            playerMap.put(name, new RPGPlayer(name, rpgClass, rpgSubClass,
                  Integer.parseInt(st.nextToken())));
         }
         br.close();
         
      }catch(FileNotFoundException e){
         log.info("No original Arpeegy player file found. One will be created for you");
      }catch(IOException e){
         log.info("Error reading Arpeegy player file");
      }catch(Exception e){
         log.info("Incorrectly formatted Arpeegy player file");
      }
      
      getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

      getCommand("addclass").setExecutor(new Create(this));
      getCommand("addsubclass").setExecutor(new Create(this));
      getCommand("removeclass").setExecutor(new Remove(this));
      getCommand("removesubclass").setExecutor(new Remove(this));
      getCommand("displayclass").setExecutor(new Display(this));
      getCommand("displaysubclass").setExecutor(new Display(this));

      log.info("LittleRPG v0.1 enabled");
   }
   
   public void onDisable(){

      // Save ALL the data!
      BufferedWriter bw;
      try{
         bw = new BufferedWriter(new FileWriter(playerFile));
         
         // Save all players to file
         Iterator<Map.Entry<String, RPGPlayer>> it = playerMap.entrySet().iterator();
         while(it.hasNext()){
            Entry<String, RPGPlayer> player = it.next();
            bw.write(player.getKey() + " "
                  + player.getValue().getClassName() + " "
                  + player.getValue().getSubClassName() + " "
                  + Integer.toString(player.getValue().getLevel()) + "\n");
         }
         bw.close();
      }catch(IOException e){
         log.info("Error saving Arpeegy players");
      }
      
      try{
         bw = new BufferedWriter(new FileWriter(classFile));
         
         // Save classes to file
         Iterator<Map.Entry<String, RPGClass>> it = classMap.entrySet().iterator();
         while(it.hasNext()){
            Entry<String, RPGClass> classIter = it.next();
            bw.write(classIter.getKey() + " "
                  + Double.toString(classIter.getValue().getStr()) + " "
                  + Double.toString(classIter.getValue().getAcc()) + " "
                  + Double.toString(classIter.getValue().getIntel()) + "\n");
         }
         bw.close();
      }catch(IOException e){
         log.info("Error saving Arpeegy classes");
      }

      try{
         bw = new BufferedWriter(new FileWriter(subClassFile));
         
         // Save classes to file
         Iterator<Map.Entry<String, RPGSubClass>> it = subClassMap.entrySet().iterator();
         while(it.hasNext()){
            Entry<String, RPGSubClass> subClassIter = it.next();
            bw.write(subClassIter.getKey() + " "
                  + Double.toString(subClassIter.getValue().getArch()) + " "
                  + Double.toString(subClassIter.getValue().getBlade()) + " "
                  + Double.toString(subClassIter.getValue().getBlock()) + " "
                  + Double.toString(subClassIter.getValue().getEgg()) + " "
                  + Double.toString(subClassIter.getValue().getSpell()) + "\n");
         }
         bw.close();
      }catch(IOException e){
         log.info("Error saving Arpeegy sub-classes");
      }
      
      log.info("LittleRPG v0.1 disabled");
   }

   public HashMap<String, RPGPlayer> getPlayerMap() {
      return playerMap;
   }
   
   public HashMap<String, RPGClass> getClassMap() {
      return classMap;
   }
   
   public HashMap<String, RPGSubClass> getSubClassMap() {
      return subClassMap;
   }
}
