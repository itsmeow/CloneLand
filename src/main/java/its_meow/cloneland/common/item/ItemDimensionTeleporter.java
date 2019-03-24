package its_meow.cloneland.common.item;

import its_meow.cloneland.CloneLandMod;
import its_meow.cloneland.TeleportController;
import its_meow.cloneland.config.CloneConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDimensionTeleporter extends Item {

	public ItemDimensionTeleporter() {
		this.setRegistryName("teleporter");
		this.setCreativeTab(CloneLandMod.tab_cloneland);
		this.setTranslationKey("cloneland.teleporter");
		this.setMaxStackSize(1);
		

		this.setMaxDamage(CloneConfig.configMaxDamage);
	}
	
	protected boolean canRepair(){
		return true;
	}
	
	@Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		
		TeleportController.getInstance().teleport(worldIn, playerIn, true, handIn);
		
		return ActionResult.newResult(EnumActionResult.PASS, itemstack);
	}
	
}
