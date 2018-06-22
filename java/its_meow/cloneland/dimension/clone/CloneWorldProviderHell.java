package its_meow.cloneland.dimension.clone;

import its_meow.cloneland.registry.DimensionRegistry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderHell;

public class CloneWorldProviderHell extends WorldProviderHell {
	
	
	public void createBiomeProvider(){
        this.biomeProvider = super.biomeProvider;
	}
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.CloneNetherDimension;
	}
        
}
