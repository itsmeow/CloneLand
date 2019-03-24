package its_meow.cloneland.common.dimension;

import its_meow.cloneland.registry.ModDimensions;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CloneWorldProvider extends WorldProvider {
	
	private WorldType terrainType;
	public void createBiomeProvider(){
        this.biomeProvider = terrainType.getBiomeProvider(world);
	}
	
	 public float calculateCelestialAngle(long worldTime, float partialTicks)
	    {
	        int i = (int)(worldTime % 24000L);
	        float f = ((float)i + partialTicks) / 24000.0F - 0.25F;

	        if (f < 0.0F)
	        {
	            ++f;
	        }

	        if (f > 1.0F)
	        {
	            --f;
	        }

	        float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
	        f = f + (f1 - f) / 3.0F;
	        return f;
	    }

	    public int getMoonPhase(long worldTime)
	    {
	        return (int)(worldTime / 24000L % 8L + 8L) % 8;
	    }

	@Override
	public DimensionType getDimensionType() {
		return ModDimensions.CloneDimension;
	}
	

	
    public String getWelcomeMessage()
    {
    	return "You are in Clone Land";
    }
    
    public String getDepartMessage()
    {
    	return "You are returning to the Overworld";
    }
	
	@Override
	public boolean isSurfaceWorld(){
		return true;
	}
	

	public boolean doesWaterVaporize(){
        return false;
    }
	
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return true;
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesXZShowFog(int par1, int par2) {
		return false;
	}
	


	
}
