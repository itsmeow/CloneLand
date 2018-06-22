package its_meow.cloneland.block;

import its_meow.cloneland.TeleportController;
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
			
			int curWorldId = TeleportController.getInstance().getDimensionID(world);
			int destWorld = TeleportController.getInstance().getMatchingDimensionID(curWorldId);
			WorldServer destWorldServer = world.getMinecraftServer().getWorld(destWorld);
			Block blockAtLocation = destWorldServer.getBlockState(pos).getBlock();
			
			if(antiGrief && !(blockAtLocation == Blocks.AIR)) { //Opposite block isn't air and griefprotect is on
				player.sendMessage(new TextComponentString("You cannot place there as a block exists there in the opposite world and Grief Protection is active."));
				return false; //Block cannot be placed
			}
			
		}
		return true; //Block is placed
	}



	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, this.getDefaultState());
		EntityPlayer player = (EntityPlayer) placer;
		
		int curWorldId = TeleportController.getInstance().getDimensionID(worldIn);
		int destWorld = TeleportController.getInstance().getMatchingDimensionID(curWorldId);
		WorldServer destWorldServer = worldIn.getMinecraftServer().getWorld(destWorld);
		
		Block blockAtLocation = destWorldServer.getBlockState(pos).getBlock();
		
		destWorldServer.setBlockToAir(pos);
		IBlockState statenew = this.getDefaultState();
		destWorldServer.setBlockState(pos, statenew);


	}




	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos,
			IBlockState state) {
		if(!worldIn.isRemote) {
			int curWorldId = TeleportController.getInstance().getDimensionID(worldIn);
			int destWorld = TeleportController.getInstance().getMatchingDimensionID(curWorldId);
			WorldServer destWorldServer = worldIn.getMinecraftServer().getWorld(destWorld);
			destWorldServer.setBlockToAir(pos);
		}
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos,
			Explosion explosionIn) {
		if(!worldIn.isRemote) {
			int curWorldId = TeleportController.getInstance().getDimensionID(worldIn);
			int destWorld = TeleportController.getInstance().getMatchingDimensionID(curWorldId);
			WorldServer destWorldServer = worldIn.getMinecraftServer().getWorld(destWorld);
			destWorldServer.setBlockToAir(pos);
		}
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}
	
}
