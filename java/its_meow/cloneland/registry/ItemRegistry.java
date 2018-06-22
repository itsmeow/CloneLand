package its_meow.cloneland.registry;

import java.util.HashSet;
import java.util.Set;

import its_meow.cloneland.item.ItemDimensionTeleporter;
import its_meow.cloneland.item.ItemDimensionalCore;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemRegistry {

	public static ItemDimensionTeleporter teleporter = new ItemDimensionTeleporter();
	public static ItemDimensionalCore core = new ItemDimensionalCore();
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		public static final Set<Item> ITEMS = new HashSet<>();

		/**
		 * Register this mod's {@link Item}s.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			final Item[] items = {
					teleporter,
					core
			};
			
			
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			for (final Item item : items) {
				registry.register(item);
				ITEMS.add(item);
			}
		}
		
		@SubscribeEvent
		public static void registerItemBlockModels(final ModelRegistryEvent event) {
			core.initModel();
			teleporter.initModel();
		}
		
	}

	
}
