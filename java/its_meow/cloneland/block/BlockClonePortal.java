package its_meow.cloneland.block;

import its_meow.cloneland.CloneLandMod;
import its_meow.cloneland.TeleportController;
import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.dimension.clone.CloneTeleporter;
import its_meow.cloneland.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockClonePortal extends Block  {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockClonePortal() {
		super(Material.ROCK);
		this.setCreativeTab(CloneLandMod.tab_cloneland);
		this.setUnlocalizedName("cloneportalblock");
		this.setRegistryName("cloneportalblock");
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHardness(3F);
		this.setHarvestLevel("Iron", 2);
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
		if(!worldIn.isRemote) {
			placer.getEntityWorld().setBlockState(pos, state.withProperty(FACING, EnumFacing.NORTH), 2);
			EntityPlayer player = (EntityPlayer) placer;

			int curWorldId = TeleportController.getInstance().getDimensionID(worldIn);
			int destWorld = TeleportController.getInstance().getMatchingDimensionID(curWorldId);
			WorldServer destWorldServer = worldIn.getMinecraftServer().getWorld(destWorld);

			Block blockAtLocation = destWorldServer.getBlockState(pos).getBlock();

			destWorldServer.setBlockToAir(pos);
			IBlockState statenew = BlockRegistry.cloneportal.getDefaultState();
			destWorldServer.setBlockState(pos, statenew.withProperty(FACING, EnumFacing.NORTH));
		}

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

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TeleportController.getInstance().teleport(worldIn, playerIn, false, null);
		return true;
	}



	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}


	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
