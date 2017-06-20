package its_meow.cloneland.registry;

import its_meow.cloneland.block.BlockClonePortal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
	public static BlockClonePortal cloneportal;
	
	public static void init(){
		registerItem(cloneportal = new BlockClonePortal());
	}
	
	public static void registerItem(Block Block) {
		GameRegistry.register(Block);
		GameRegistry.register(new ItemBlock(Block), Block.getRegistryName());
	}

	
	public static void initModels(){
		cloneportal.initModel();
	}
	
}
