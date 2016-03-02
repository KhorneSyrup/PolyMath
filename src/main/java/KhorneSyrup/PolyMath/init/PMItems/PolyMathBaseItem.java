package KhorneSyrup.PolyMath.init.PMItems;

import KhorneSyrup.PolyMath.PolyMath;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PolyMathBaseItem extends Item
{
	public PolyMathBaseItem() {
		super();
	}
	@SideOnly(Side.CLIENT)
	public void registerRender(ItemModelMesher mesher) {
		String name = getUnlocalizedName();
		name = PolyMath.MOD_ID + ":" + name.substring(name.lastIndexOf(".") + 1);
		PolyMath.logger.info("Registering Renderer for" + name);
		mesher.register(this, 0, new ModelResourceLocation(name, "inventory"));
		}
	}

