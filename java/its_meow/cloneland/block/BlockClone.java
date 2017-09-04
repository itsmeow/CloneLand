package its_meow.cloneland.block;

import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockClone extends Block {
	
	public BlockClone() {
		super(Material.ROCK);
	}


	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		super.canPlaceBlockAt(world, pos);
		EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, false); //Attempt to get player
		if(!world.isRemote) { //Server thread only
			boolean antiGrief = CloneConfig.antiGrief;
			WorldServer clonelandserver = world.getMinecraftServer().getWorld(CloneConfig.dimensionId);
			WorldServer worldserver0 = world.getMinecraftServer().getWorld(0);
			if(world == clonelandserver) { //If placement was in CloneLand
				Block blockAtLocation = worldserver0.getBlockState(pos).getBlock(); //Block at same location in Overworld
				//For debugs: System.out.println("pre tester placed in clone but overworld has " + blockAtLocation);
				if(antiGrief && !(blockAtLocation == Blocks.AIR)) { //Opposite block isn't air and griefprotect is on
					player.sendMessage(new TextComponentString("You cannot place there as a block exists there in the Overworld and Grief Protection is active."));
					return false; //Block cannot be placed
				}
			} else if(world == worldserver0) { //If placement was in Overworld
				Block blockAtLocation = clonelandserver.getBlockState(pos).getBlock(); //Block at same location in CloneLand
				//For debugs: System.out.println("pre tester placed in Overworld but clone has " + blockAtLocation);
				if(antiGrief && !(blockAtLocation == Blocks.AIR)) { //Opposite block isn't air and griefprotect is on
					player.sendMessage(new TextComponentString("You cannot place there as a block exists there in the Overworld and Grief Protection is active."));
					return false; //Block cannot be placed
				}
			}
		}
		return true; //Block is placed
	}



	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state, 2);
		EntityPlayer player = (EntityPlayer) placer;
		boolean antiGrief = CloneConfig.antiGrief;
		if(!worldIn.isRemote) {
			WorldServer clonelandserver = worldIn.getMinecraftServer().getWorld(CloneConfig.dimensionId);
			WorldServer worldserver0 = worldIn.getMinecraftServer().getWorld(0);
			if(worldIn == worldserver0) {
				Block blockAtLocation = clonelandserver.getBlockState(pos).getBlock();
				clonelandserver.setBlockToAir(pos);
				IBlockState statenew = BlockRegistry.cloneportal.getDefaultState();
				clonelandserver.setBlockState(pos, statenew);

			} else if(worldIn == clonelandserver) {
				Block blockAtLocation = worldserver0.getBlockState(pos).getBlock();
				worldserver0.setBlockToAir(pos);
				IBlockState statenew = BlockRegistry.cloneportal.getDefaultState();
				worldserver0.setBlockState(pos, statenew);
			}
		}

	}
	
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos,
			IBlockState state) {
		if(!worldIn.isRemote) {
			WorldServer clonelandserver = worldIn.getMinecraftServer().getWorld(CloneConfig.dimensionId);
			WorldServer worldserver0 = worldIn.getMinecraftServer().getWorld(0);
			if(worldIn == worldserver0) {
				clonelandserver.setBlockToAir(pos);
			} else if(worldIn == clonelandserver) {
				worldserver0.setBlockToAir(pos);
			}
		}
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos,
			Explosion explosionIn) {
		if(!worldIn.isRemote) {
			WorldServer clonelandserver = worldIn.getMinecraftServer().getWorld(CloneConfig.dimensionId);
			WorldServer worldserver0 = worldIn.getMinecraftServer().getWorld(0);
			if(worldIn == worldserver0) {
				clonelandserver.setBlockToAir(pos);
			} else if(worldIn == clonelandserver) {
				worldserver0.setBlockToAir(pos);
			}
		}
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}
	
}
