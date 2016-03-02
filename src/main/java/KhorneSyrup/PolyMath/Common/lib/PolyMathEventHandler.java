package KhorneSyrup.PolyMath.Common.lib;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.network.PacketDispatcher;
import KhorneSyrup.PolyMath.Common.network.client.SyncPlayerPropsMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PolyMathEventHandler {
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		//Ensure the entity being constructed is the correct type.
		if (event.entity instanceof EntityPlayer) {
			if (PolyMathPlayer.get((EntityPlayer) event.entity) == null) {

				//Register PolyMath Properties
				PolyMathPlayer.register((EntityPlayer) event.entity);
				PolyMath.logger.info("Registering extended properties for player");

			}
		}
	}
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {

		if (event.entity instanceof EntityPlayerMP) {
			PolyMath.logger.info("Player joined world, sending properties to client.");
			PacketDispatcher.sendTo(new SyncPlayerPropsMessage((EntityPlayer) event.entity), (EntityPlayerMP) event.entity);
			}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		PolyMath.logger.info("Cloning player properties");
		PolyMathPlayer.get(event.entityPlayer).copy(PolyMathPlayer.get(event.original));
	}

	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			PolyMathPlayer props = PolyMathPlayer.get((EntityPlayer) event.entity);

		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			PolyMathPlayer.get(player).onUpdate();
			if (player.isPlayerFullyAsleep()) {
				player.addChatMessage(new ChatComponentText("After a full nights rest, you feel refreshed!!"));
				PolyMathPlayer.get(player).replenishHealth();
				PolyMathPlayer.get(player).replenishStamina();
				PolyMathPlayer.get(player).replenishMana();
			}
		}
	}



}
