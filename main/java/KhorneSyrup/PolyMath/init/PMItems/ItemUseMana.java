package KhorneSyrup.PolyMath.init.PMItems;

import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUseMana extends Item {
	public ItemUseMana(int par1) {
		super();
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			PolyMathPlayer props = PolyMathPlayer.get(player);
			
			if (props.consumeMana(15))
			{
				System.out.println("[MANA CONSUMED] Sufficient Mana for action!");
			}
			else
			{
				System.out.println("[INSUFFICIENT MANA]");
			}
		}
		return itemstack;
	}
}

