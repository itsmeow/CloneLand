package its_meow.cloneland;

import its_meow.cloneland.config.CloneConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class EventHandler {

	@SubscribeEvent
	public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		World world = player.getEntityWorld();
		if(!world.isRemote) {
			boolean antiGrief = CloneConfig.antiGrief;
			player.sendMessage(new TextComponentString("You are running CloneLand " + Ref.VERSION + " by its_meow!"));
			player.sendMessage(new TextComponentString(antiGrief ? "AntiGrief is enabled, you cannot place teleporters in blocks that exist in the opposite dimension!" : "AntiGrief is disabled!"));
		}
	}

}
