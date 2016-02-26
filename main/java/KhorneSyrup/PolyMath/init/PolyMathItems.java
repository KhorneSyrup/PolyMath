package KhorneSyrup.PolyMath.init;
import KhorneSyrup.PolyMath.PolyMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PolyMathItems {
	
	public static Item blackIron_sword;
	public static Item mana_Potion;
	public static Item ItemUseMana;
	
	public static void init() {
		blackIron_sword = new Item().setUnlocalizedName("blackIron_sword");
		mana_Potion = new Item().setUnlocalizedName("mana_potion");
		ItemUseMana = new Item().setUnlocalizedName("ItemUseMana");
		
		
	}
	
	public static void register()
	{
		GameRegistry.registerItem(blackIron_sword, blackIron_sword.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(mana_Potion, mana_Potion.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(ItemUseMana, ItemUseMana.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(blackIron_sword);
		registerRender(mana_Potion);
		registerRender(ItemUseMana);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(PolyMath.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
