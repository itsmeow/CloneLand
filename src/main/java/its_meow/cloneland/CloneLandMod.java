package its_meow.cloneland;

import java.io.File;

import org.apache.logging.log4j.Logger;

import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.registry.ModDimensions;
import its_meow.cloneland.registry.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Ref.MOD_ID, name = Ref.NAME, version = Ref.VERSION, acceptedMinecraftVersions = Ref.acceptedMCV, updateJSON = Ref.updateJSON)
public class CloneLandMod {

    @Instance(Ref.MOD_ID) 
    public static CloneLandMod mod;

    public static CreativeTabs tab_cloneland = new CreativeTabs("CloneLand") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.core);
        }
    };

    public static Logger logger;
    
    public static Configuration config;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "cloneland.cfg")); 
        CloneConfig.readConfig();
        CloneConfig.initConfig(config);
        ModDimensions.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        if(config.hasChanged()){
            config.save();
        }
    }
}
