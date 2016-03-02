package KhorneSyrup.PolyMath;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import KhorneSyrup.PolyMath.Common.CommonProxy;
import KhorneSyrup.PolyMath.Common.lib.PolyMathEventHandler;
import KhorneSyrup.PolyMath.Common.lib.GUI.PolyMathGui;
import KhorneSyrup.PolyMath.Common.network.PacketDispatcher;
import KhorneSyrup.PolyMath.init.PolyMathItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(
		modid = PolyMath.MOD_ID,
		name = PolyMath.MOD_NAME,
		version = PolyMath.VERSION
	)

public final class PolyMath {

	public static final String MOD_ID = "PM";
	public static final String MOD_NAME = "PolyMath";
	public static final String VERSION = "0.001";
	public static final String CLIENT_PROXY_CLASS = "KhorneSyrup.PolyMath.Common.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "KhorneSyrup.PolyMath.Common.CommonProxy";





	private static int modGuiIndex = 10;
	private static int modEntityIndex = 0;

	@Mod.Instance(MOD_ID)
	public static PolyMath instance;
	/**public static final int
	 * GUI_CUSTOM_INV = modGuiIndex++,
	 * GUI_ITEM_INV = modGuiIndex++;
	 */
	@SidedProxy(clientSide = PolyMath.CLIENT_PROXY_CLASS, serverSide = PolyMath.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	public static final Logger logger = LogManager.getLogger(MOD_ID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Logger.info("Beginning Pre-Init");
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + "PolyMath.cfg"));
		config.load();
		config.save();

		PolyMathItems.init();
		PolyMathItems.register();
		//Rember to register your packets!! This applies whether or not you used a
		//custom class or direct implementation of SimpleNetworkWrapper
		PacketDispatcher.registerPackets();
	}
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.registerRenders();


		MinecraftForge.EVENT_BUS.register(new PolyMathEventHandler());

		PolyMathEventHandler events = new PolyMathEventHandler();
		MinecraftForge.EVENT_BUS.register(events);

		//NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

		//Crafting recipes should be loaded at this stage.


	}
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
			MinecraftForge.EVENT_BUS.register(new PolyMathGui(Minecraft.getMinecraft()));

	}
}
