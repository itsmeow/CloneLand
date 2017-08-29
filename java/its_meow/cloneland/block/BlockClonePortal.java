package its_meow.cloneland.block;

import its_meow.cloneland.CloneLandMod;
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
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.NORTH), 2);
		EntityPlayer player = (EntityPlayer) placer;
		boolean antiGrief = CloneConfig.antiGrief;
		if(!worldIn.isRemote) {
			WorldServer clonelandserver = worldIn.getMinecraftServer().getWorld(CloneConfig.dimensionId);
			WorldServer worldserver0 = worldIn.getMinecraftServer().getWorld(0);
			if(worldIn == worldserver0) {
				Block blockAtLocation = clonelandserver.getBlockState(pos).getBlock();
				clonelandserver.setBlockToAir(pos);
				IBlockState statenew = BlockRegistry.cloneportal.getDefaultState();
				clonelandserver.setBlockState(pos, statenew.withProperty(FACING, EnumFacing.NORTH));

			} else if(worldIn == clonelandserver) {
				Block blockAtLocation = worldserver0.getBlockState(pos).getBlock();
				worldserver0.setBlockToAir(pos);
				IBlockState statenew = BlockRegistry.cloneportal.getDefaultState();
				worldserver0.setBlockState(pos, statenew.withProperty(FACING, EnumFacing.NORTH));
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

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote){
			if (playerIn instanceof EntityPlayerMP){
				EntityPlayerMP playerMP = (EntityPlayerMP)playerIn;
				if (playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer && playerMP.dimension != CloneConfig.dimensionId && playerMP.dimension == 0){
					playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, CloneConfig.dimensionId, new CloneTeleporter(playerIn.getServer().getWorld(CloneConfig.dimensionId)));
				} else if (playerIn.getRidingEntity() == null | false && playerIn.isBeingRidden() == false && playerIn instanceof EntityPlayer && playerMP.dimension != 0 && playerMP.dimension == CloneConfig.dimensionId){
					playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, 0, new CloneTeleporter(playerIn.getServer().getWorld(0)));
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
