package its_meow.cloneland.registry;

import its_meow.cloneland.item.ItemDimensionTeleporter;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRegistry {

	public static ItemDimensionTeleporter teleporter;
	
	public static void init(){
		registerItem(teleporter = new ItemDimensionTeleporter());
	}
	
	public static void registerItem(Item Item) {
		GameRegistry.register(Item);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels(){
		teleporter.initModel();
	}	
	
}
