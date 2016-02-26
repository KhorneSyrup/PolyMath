package KhorneSyrup.PolyMath;

import KhorneSyrup.PolyMath.Common.CommonProxy;
import KhorneSyrup.PolyMath.Common.lib.PolyMathEventHandler;
import KhorneSyrup.PolyMath.Common.lib.GUI.PolyMathGui;
import KhorneSyrup.PolyMath.init.PolyMathItems;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = PolyMath.MOD_ID, 
		name = PolyMath.MOD_NAME, 
		version = PolyMath.VERSION
	)

public class PolyMath {
	
	public static final String MOD_ID = "PM";
	public static final String MOD_NAME = "PolyMath";
	public static final String VERSION = "0.001";
	public static final String CLIENT_PROXY_CLASS = "KhorneSyrup.PolyMath.Common.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "KhorneSyrup.PolyMath.Common.CommonProxy";

	@SidedProxy(clientSide = PolyMath.CLIENT_PROXY_CLASS, serverSide = PolyMath.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		PolyMathItems.init();
		PolyMathItems.register();
	}
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
		MinecraftForge.EVENT_BUS.register(new PolyMathEventHandler());
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
			MinecraftForge.EVENT_BUS.register(new PolyMathGui(Minecraft.getMinecraft()));
		
	}
}
