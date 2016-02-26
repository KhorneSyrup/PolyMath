package KhorneSyrup.PolyMath.init.PMItems;

import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import KhorneSyrup.PolyMath.PolyMathCreativeTab.PolyMathCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;

public class mana_Potion extends Item {
	public mana_Potion()
	{
		this.setMaxStackSize(64);
		this.setCreativeTab(PolyMathCreativeTab.PolyMathCreativeTab);
	}

	/*00
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		PolyMathPlayer props = PolyMathPlayer.get(playerIn);
		
	}
*/
}
