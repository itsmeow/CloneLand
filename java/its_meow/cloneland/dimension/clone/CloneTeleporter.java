package its_meow.cloneland.dimension.clone;

import java.util.Random;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class CloneTeleporter extends Teleporter{
	private final WorldServer worldServerInstance;
    private final Random random;
    private final Long2ObjectMap<Teleporter.PortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap(4096);
	
	public CloneTeleporter(WorldServer worldIn) {
		super(worldIn);
		this.worldServerInstance = worldIn;
		this.random = new Random(worldIn.getSeed());
	}
	

	double ii;
	double kk;
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw){
        if (this.worldServerInstance.provider.getDimensionType().getId() != 1){
        	this.placeInExistingPortal(entityIn, rotationYaw);
        }else{
            double i = entityIn.posX; ii=i;
            double j = entityIn.posY - 1;
            double k = entityIn.posZ; kk=k;
            int l = 1;
            int i1 = 0;

        

            for (int j1 = -2; j1 <= 2; ++j1){
                for (int k1 = -2; k1 <= 2; ++k1){
                    for (int l1 = -1; l1 < 3; ++l1){
                        double i2 = i + k1 * 1 + j1 * 0;
                        double j2 = j + l1;
                        double k2 = k + k1 * 0 - j1 * 1;
                        boolean flag = l1 < 0;
                    }
                }
            }
            

            
            
            
            entityIn.setLocationAndAngles(getPointSign(ii), j, getPointSign(kk), entityIn.rotationYaw, 0.0F);
            entityIn.motionX = 0.0D;
            entityIn.motionY = 0.0D;
            entityIn.motionZ = 0.0D;
        }
    }
	
    

	public double getPointSign(double doubleIn) {
		if(doubleIn >= 0){
			return 0.5D;
		} else if(doubleIn < 0){
			return -0.5D;
		} else {
			return 0D;
		}
	}

	
	
	
	@Override
	public boolean placeInExistingPortal(Entity entityIn, float p_180620_2_) {
		return false;
	}
	
	private boolean isPositionClear(BlockPos pos) {
		return !this.worldServerInstance.isAirBlock(pos) || !this.worldServerInstance.isAirBlock(pos.up());
	}
	
	public void removeStalePortalLocations(long worldTime){
        if (worldTime % 100L == 0L){
            long i = worldTime - 300L;
            ObjectIterator<Teleporter.PortalPosition> objectiterator = this.destinationCoordinateCache.values().iterator();

            while (objectiterator.hasNext()){
                Teleporter.PortalPosition teleporter$portalposition = (Teleporter.PortalPosition)objectiterator.next();

                if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i){
                    objectiterator.remove();
                }
            }
        }
    }
	
	public class PortalPosition extends BlockPos{
        public long lastUpdateTime;

        public PortalPosition(BlockPos pos, long lastUpdate){
            super(pos.getX(), pos.getY(), pos.getZ());
            this.lastUpdateTime = lastUpdate;
        }
    }

}