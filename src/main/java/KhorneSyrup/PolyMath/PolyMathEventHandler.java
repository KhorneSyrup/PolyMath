package KhorneSyrup.PolyMath;

import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PolyMathEventHandler
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			if (PolyMathPlayer.get((EntityPlayer) event.entity) == null) {
				PolyMath.logger.info("Registering PolyMath Player properties");
				PolyMathPlayer.register((EntityPlayer) event.entity);
			}
		}
	}

}
