package KhorneSyrup.PolyMath.Common;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.init.PolyMathItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;

import KhorneSyrup.PolyMath.init.PolyMathItems;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders()
	{
		PolyMathItems.registerRenders();
	}
}
