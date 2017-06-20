package its_meow.cloneland.item;

import its_meow.cloneland.CloneLandMod;
import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.dimension.clone.CloneTeleporter;
import its_meow.cloneland.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDimensionTeleporter extends Item {

	
	public ItemDimensionTeleporter() {
		this.setRegistryName("teleporter");
		this.setCreativeTab(CloneLandMod.tab_cloneland);
		this.setUnlocalizedName("teleporter");
		this.setMaxStackSize(1);
		

		this.setMaxDamage(CloneConfig.configMaxDamage);
	}
	
	protected boolean canRepair(){
		return true;
	}
	
	@Override
	 public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
		 ItemStack itemstack = playerIn.getHeldItem(handIn);
		 

		 if (!worldIn.isRemote){
			 //Is Server
			 if (playerIn instanceof EntityPlayerMP){
				 WorldServer worldserver = (WorldServer)worldIn;
				 EntityPlayerMP playerMP = (EntityPlayerMP)playerIn;
				 if (playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer && playerMP.dimension != CloneConfig.dimensionId && playerMP.dimension == 0){
					 playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, CloneConfig.dimensionId, new CloneTeleporter(playerIn.getServer().worldServerForDimension(CloneConfig.dimensionId)));
					 if(!playerIn.isCreative()){
						 itemstack.damageItem(CloneConfig.damageToDo, playerIn);
					 }
				 } else if (playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer && playerMP.dimension != 0 && playerMP.dimension == CloneConfig.dimensionId){
					 playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, 0, new CloneTeleporter(playerIn.getServer().worldServerForDimension(0)));
					 if(!playerIn.isCreative()){
						 itemstack.damageItem(CloneConfig.damageToDo, playerIn);
					 }
				 }
			 }
		 } else{
			 //Is Client
			 if (playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer && playerIn.dimension != CloneConfig.dimensionId && playerIn.dimension == 0){
				 playerIn.sendMessage(new TextComponentString("Dimension is now Clone Land."));
			 } else if (playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer && playerIn.dimension != 0 && playerIn.dimension == CloneConfig.dimensionId){
				 playerIn.sendMessage(new TextComponentString("Dimension is now Overworld."));
			 }
		 }
		 return ActionResult.newResult(EnumActionResult.PASS, itemstack);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
}
