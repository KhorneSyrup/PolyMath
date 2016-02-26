package KhorneSyrup.PolyMath.PolyMathCreativeTab;

import KhorneSyrup.PolyMath.init.PolyMathItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PolyMathCreativeTab {
	public static CreativeTabs PolyMathCreativeTab = new CreativeTabs("PolyMathTab") 
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem(){
			ItemStack iStack = new ItemStack(PolyMathItems.mana_Potion);
			return iStack.getItem();
		}
		public int func_151243_F()
		{
			return 7;
		}
	};
}
