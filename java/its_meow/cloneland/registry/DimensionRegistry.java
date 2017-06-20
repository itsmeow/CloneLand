package its_meow.cloneland.registry;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.dimension.clone.CloneWorldProvider;

public class DimensionRegistry {
	
	public static int id1 = CloneConfig.dimensionId;
	public static final DimensionType CloneDimension = DimensionType.register("Clone Dimension", "-CloneDimension", id1, CloneWorldProvider.class, true);
	
	public static void register(){
		DimensionManager.registerDimension(id1, CloneDimension);
	}
	
	
}
