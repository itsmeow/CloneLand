package its_meow.cloneland.common.dimension;

import its_meow.cloneland.registry.ModDimensions;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderHell;

public class CloneWorldProviderHell extends WorldProviderHell {
	
	
	public void createBiomeProvider(){
        this.biomeProvider = super.biomeProvider;
	}
	
	@Override
	public DimensionType getDimensionType() {
		return ModDimensions.CloneNetherDimension;
	}
        
}
