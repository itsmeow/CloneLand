package its_meow.cloneland.proxy;


import java.io.File;

import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.registry.BlockRegistry;
import its_meow.cloneland.registry.DimensionRegistry;
import its_meow.cloneland.registry.ItemRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

	public static Configuration config;
	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("Common Pre-Init");
		File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "cloneland.cfg")); 
        CloneConfig.readConfig();
        CloneConfig.initConfig(config);
		ItemRegistry.init();//Runs "init" in ItemRegistry.
		BlockRegistry.init();
		DimensionRegistry.register();
	}
	
	boolean craftingCost = true;
	
	public void init(FMLInitializationEvent e) {
		System.out.println("Common Init(blank)");
	}
	
	public void postInit(FMLPostInitializationEvent e){
		if(config.hasChanged()){
			config.save();
		}
	}
	

	
	
	
}
