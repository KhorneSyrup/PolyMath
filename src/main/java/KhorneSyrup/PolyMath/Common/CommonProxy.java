package KhorneSyrup.PolyMath.Common;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	public void registerRenders() {}



public EntityPlayer getPlayerEntity(MessageContext ctx) {
	PolyMath.logger.info("Retrieving player from CommonProxy for message on side" + ctx.side);
	return ctx.getServerHandler().playerEntity;
}

public IThreadListener getThreadFromContext(MessageContext ctx) {
	return ctx.getServerHandler().playerEntity.getServerForPlayer();
}

/*@Override
public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y,int z) {
	if (guiId == PolyMath.GUI_CUSTOM_INV) {
		return new ContainerCustomPlayer(player, player.inventory, PolyMathPlayer.get(player).inventory);
	} else if (guiId == PolyMath.GUI_ITEM_INV) {
		return new ContainerMagicBag(player, player.inventory, new InventoryMagicBag(player.getHeldItem()));
	} else {
		return null;
	}
}
*/

}