package its_meow.cloneland.config;

import org.apache.logging.log4j.Level;

import its_meow.cloneland.CloneLandMod;
import its_meow.cloneland.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;

public class CloneConfig {
	
	private static final String cI = "items";
	private static final String cD = "dimensions";
	private static final String cM = "misc";
	
	//public static boolean isExpensive = true;
	public static int dimensionId = 5705;
	public static int configMaxDamage = 150;
	public static int damageToDo = 1;
	public static boolean antiGrief = true;
	
	public static void readConfig(){
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initConfig(cfg);
		} catch (Exception e1) {
			CloneLandMod.logger.log(Level.ERROR, "Problem Loading Config!!", e1);
		} finally {
		if(cfg.hasChanged()){
			cfg.save();
		}
	}
	}
	
	public static void initConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(cI, "Items Configuration");
        cfg.addCustomCategoryComment(cD, "Dimensions Configuration");
        cfg.addCustomCategoryComment(cM, "Misc. Configuration");
        //isExpensive = cfg.getBoolean("craftingcosthigh", cI, isExpensive, "Set to false to cheapen the recipe for the teleporter by changing any block to the respective ingot/gem(set as \"true\" or \"false\")");
        configMaxDamage = cfg.getInt("teleporterhealth", cI, configMaxDamage, 50, 500, "The total amount of damage the item can take.(Allows: 50-500)");
        damageToDo = cfg.getInt("teleporterdamageperuse", cI, damageToDo, 1, 50, "The total amount of damage given to the item when used.(Allows:1-50)");
        dimensionId = cfg.getInt("dimensionId", cD, dimensionId, 2, 9999, "Set the dimension's ID. Don't really change unless you are having incompatabilites. WARNING: Changing dimension ID WILL cause you to lose built structures within the clone dimension. They can be regained if you change the ID back.");
        antiGrief = cfg.getBoolean("antigriefon", cM, antiGrief, "Stop griefing by clone teleporter blocks.");
	}
	
}
