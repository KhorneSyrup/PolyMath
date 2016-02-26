package KhorneSyrup.PolyMath.Common.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PolyMathEventHandler {
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		//Ensure the entity being constructed is the correct type.
		if (event.entity instanceof EntityPlayer && PolyMathPlayer.get((EntityPlayer) event.entity) == null)
			//Register PolyMath Properties
			PolyMathPlayer.register((EntityPlayer) event.entity);	
	}

}
