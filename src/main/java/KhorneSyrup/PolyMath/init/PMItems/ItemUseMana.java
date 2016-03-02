package KhorneSyrup.PolyMath.init.PMItems;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemUseMana extends PolyMathBaseItem {
    public ItemUseMana() {
    	super();
        setMaxStackSize(6);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("ItemUseMana");
    }

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			PolyMathPlayer props = PolyMathPlayer.get(player);

			if (props.consumeMana(10)) {
				System.out.println("[MANA CONSUMED] Sufficient Mana for action!");
				PolyMath.logger.info("Player had enough mana. Do something Awesome!");
			}
			else {
				System.out.println("[INSUFFICIENT MANA]");
				PolyMath.logger.info("Player ran out of mana!!!");
			}
		}
		return stack;
	}
}

