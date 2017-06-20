package its_meow.cloneland.registry;

import its_meow.cloneland.RecipeHandler;
import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingRegistry {
	
	public static boolean expensive = CloneConfig.isExpensive;
	
	public static final void register() {
		
		if(expensive){
			RecipeHandler.addShapedRecipe("Clone Land Teleporter", new ItemStack(ItemRegistry.teleporter, 1), new Object[]{
			" ed",
			" ge",
			"g  ",
			Character.valueOf('g'), Blocks.GOLD_BLOCK, 
			Character.valueOf('d'), Blocks.DIAMOND_BLOCK, 
			Character.valueOf('e'), Blocks.EMERALD_BLOCK});
		} else if(!expensive){
			RecipeHandler.addShapedRecipe("Clone Land Teleporter", new ItemStack(ItemRegistry.teleporter, 1), new Object[]{
			" ed",
			" ge",
			"g  ",
			Character.valueOf('g'), Items.GOLD_INGOT, 
			Character.valueOf('d'), Items.DIAMOND, 
			Character.valueOf('e'), Items.EMERALD});
		} else {
			RecipeHandler.addShapedRecipe("Clone Land teleporter", new ItemStack(ItemRegistry.teleporter, 1), new Object[]{
			" ed",
			" ge",
			"g  ",
			Character.valueOf('g'), Blocks.GOLD_BLOCK, 
			Character.valueOf('d'), Blocks.DIAMOND_BLOCK, 
			Character.valueOf('e'), Blocks.EMERALD_BLOCK});
		}
		
		RecipeHandler.addShapedRecipe("Clone Portal Block", new ItemStack(BlockRegistry.cloneportal, 1), new Object[]{
			"dld",
			"gdg",
			"ggg",
			Character.valueOf('g'), Items.GOLD_INGOT, 
			Character.valueOf('d'), Items.DIAMOND, 
			Character.valueOf('l'), Blocks.LAPIS_BLOCK});
		
	}
	
	
}
