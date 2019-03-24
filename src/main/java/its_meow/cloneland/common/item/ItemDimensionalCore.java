package its_meow.cloneland.common.item;

import its_meow.cloneland.CloneLandMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemDimensionalCore extends Item {

	public ItemDimensionalCore() {
		this.setRegistryName("dimensionalcore");
		this.setCreativeTab(CloneLandMod.tab_cloneland);
		this.setTranslationKey("cloneland.dimensionalcore");
		this.setMaxStackSize(1);
	}
	
	@Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote) {
			playerIn.sendMessage(new TextComponentString("You can feel its power emanating from within."));
		}
		
		return ActionResult.newResult(EnumActionResult.PASS, itemstack);
	}
	
}
