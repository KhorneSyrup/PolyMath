package KhorneSyrup.PolyMath.init.PMItems;

import java.util.List;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class mana_Potion extends PolyMathBaseItem
{
	private int strength = 10;
    public mana_Potion()
    {
        this.setMaxStackSize(64);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName("mana_Potion");
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
    	PolyMathPlayer props = PolyMathPlayer.get(playerIn);
        if (!playerIn.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }
       if (props.regenMana(strength)) {
        playerIn.addChatMessage(new ChatComponentText("Mana Restored" + ":" + strength));
        playerIn.addChatMessage(new ChatComponentText("CurrentMana" + ":" + props.getCurrentMana()));
       }
       else {
    	   playerIn.addChatMessage(new ChatComponentText("Mana full, no need to use this potion!!!"));
       }
        return stack;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 5;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
    	PolyMathPlayer props = PolyMathPlayer.get(playerIn);
    	if (!props.regenMana(strength)) {
    		playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
    		playerIn.addChatMessage(new ChatComponentText("Mana Restored" + ":" + strength));
    		playerIn.addChatMessage(new ChatComponentText("CurrentMana" + ":" + props.getCurrentMana()));
    	}
    return itemStackIn;
    }
}