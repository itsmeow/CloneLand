package its_meow.cloneland;

import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.dimension.clone.CloneTeleporter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportController {


	private static TeleportController tpcontroller;

	private TeleportController() {
		if(tpcontroller == null) {
			//Object goes out of scope otherwise, cannot directly store a singleton
			tpcontroller = this;
		}
	}

	public static TeleportController getInstance() {
		return tpcontroller == null ? new TeleportController() : tpcontroller;
	}

	public void teleport(World worldIn, EntityPlayer playerIn, boolean shrinkStack, EnumHand handIn) {
		if(!worldIn.isRemote){
			if (playerIn instanceof EntityPlayerMP){
				EntityPlayerMP playerMP = (EntityPlayerMP)playerIn;
				if(playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer) {
					
					int matching = getMatchingDimensionID(playerMP.dimension);
					
					playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, matching, new CloneTeleporter(playerIn.getServer().getWorld(matching)));
	
					if(!playerIn.isCreative() && shrinkStack){
						ItemStack itemstack = playerIn.getHeldItem(handIn);
						itemstack.damageItem(CloneConfig.damageToDo, playerIn);
					}
				}
			}
		} else{
			//Is Client
			if(playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer) {
				if (playerIn.dimension == 0){ //Going to CL
					playerIn.sendMessage(new TextComponentString("Dimension is now Clone Land."));
				} else if (playerIn.dimension == CloneConfig.dimensionId){ //Going to OVW
					playerIn.sendMessage(new TextComponentString("Dimension is now Overworld."));
				} else if (playerIn.dimension == -1) { //Going to CLN
					playerIn.sendMessage(new TextComponentString("Dimension is now Clone Nether."));
				} else if (playerIn.dimension == CloneConfig.dimensionId2) { //Going to NTR
					playerIn.sendMessage(new TextComponentString("Dimension is now Nether."));
				}
			}
		}
	}
	
	public int getMatchingDimensionID(int in) {
		if(in == 0)
			return CloneConfig.dimensionId;
		
		if(in == -1)
			return CloneConfig.dimensionId2;
		
		if(in == CloneConfig.dimensionId)
			return 0;
		
		if(in == CloneConfig.dimensionId2)
			return -1;
		
		return 0;
	}
	
	public int getDimensionID(World worldIn) {
		return worldIn.provider.getDimensionType().getId();
	}

}
