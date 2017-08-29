package its_meow.cloneland; // The package (aka folder) that this class is stored in

import org.apache.logging.log4j.Logger;

import its_meow.cloneland.proxy.ClientProxy;
import its_meow.cloneland.proxy.CommonProxy;
import its_meow.cloneland.registry.CreativeTabRegistry;
import its_meow.cloneland.registry.ItemRegistry;
import its_meow.cloneland.registry.TextureRegistry;
import net.minecraft.client.resources.Language;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Ref.MOD_ID, name = Ref.NAME, version = Ref.VERSION, acceptedMinecraftVersions = Ref.acceptedMCV, updateJSON = Ref.updateJSON) // Shows this is the mod class and defines the mod's variables.
public class CloneLandMod {

	@Instance(Ref.MOD_ID) 
	public static CloneLandMod mod; //Sets the class as an Instance. I don't actually know what this does, ha
	
	@SidedProxy(clientSide = Ref.CLIENT_PROXY_C, serverSide = Ref.SERVER_PROXY_C) // I have no clue what these do, ha
	public static CommonProxy proxy;

	public static CreativeTabRegistry tab_cloneland = new CreativeTabRegistry("CloneLand");

	public static Logger logger;
	
	@EventHandler // Declares this function as an event handler.
	public void preInit(FMLPreInitializationEvent event) { //Run Before Initialization.
		System.out.println("Pre-Init");//prints "Pre-Init" into console to show when code is run.
		proxy.preInit(event);
	}
	
	

	@EventHandler // Declares this function as an event handler.
	public void init(FMLInitializationEvent e) { // Run After Pre-Initialization
		System.out.println("Init");//prints "Init" into console to show when code is run.
		proxy.init(e);
	}
	
	@EventHandler // Declares this function as an event handler.
	public void postInit(FMLPostInitializationEvent e) { //Run after Initialization.
		System.out.println("Post-Init");//prints "Post-Init" into console to show when code is run.
		proxy.postInit(e);
	}
}
