package its_meow.cloneland;

import its_meow.cloneland.config.CloneConfig;
import its_meow.cloneland.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventHandler {

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		World world = player.getEntityWorld();
		if(!world.isRemote) {
			boolean antiGrief = CloneConfig.antiGrief;
			player.sendMessage(new TextComponentString("You are running CloneLand " + Ref.VERSION + " by its_meow!"));
			player.sendMessage(new TextComponentString(antiGrief ? "AntiGrief is enabled, you cannot place teleporters in blocks that exist in the opposite dimension!" : "AntiGrief is disabled!"));
		}
	}

}
