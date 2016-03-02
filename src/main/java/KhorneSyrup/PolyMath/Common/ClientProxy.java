package KhorneSyrup.PolyMath.Common;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.lib.GUI.PolyMathGui;
import KhorneSyrup.PolyMath.init.PolyMathItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import KhorneSyrup.PolyMath.init.PolyMathItems;

public class ClientProxy extends CommonProxy{

	private final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void registerRenders()
	{
		ItemModelMesher mesher = mc.getRenderItem().getItemModelMesher();
		PolyMathItems.registerRenders();

		MinecraftForge.EVENT_BUS.register(new PolyMathGui(mc));

		//FMLCommonHandler.instance().bus().register(new KeyHandler(mc));
	}

	private void registerItemRenderer(ItemModelMesher mesher, Item item) {
		registerItemRenderer(mesher, item, 0);
	}

	private void registerItemRenderer(ItemModelMesher mesher, Item item, int meta) {
		String name = item.getUnlocalizedName();
		name = PolyMath.MOD_ID + ":" + name.substring(name.lastIndexOf(".") + 1);
		registerItemRenderer(mesher, item, name, meta);
	}

	private void registerItemRenderer(ItemModelMesher mesher, Item item, String name, int meta) {
		PolyMath.logger.info("Registering Meshes for " + name);
		mesher.register(item, meta,new ModelResourceLocation(name, "inventory"));
	}

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
	PolyMath.logger.info("Retrieving player from ClientProxy for message on side" + ctx.side);
	return (ctx.side.isClient() ? mc.thePlayer : super.getPlayerEntity(ctx));
	}
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return(ctx.side.isClient() ? mc : super.getThreadFromContext(ctx));
	}
}
