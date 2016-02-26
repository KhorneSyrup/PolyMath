package KhorneSyrup.PolyMath.Common.lib.GUI;

import org.lwjgl.opengl.GL11;

import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class PolyMathGui extends Gui{
	private Minecraft mc;
	//Declare resource Locations for HSM bars.
	private static final ResourceLocation manaBarTexture = new ResourceLocation("PM", "textures/gui/mana_bar.png");
	private static final ResourceLocation StaminaBarTexture = new ResourceLocation("PM", "textures/gui/stamina_bar.png");
	private static final ResourceLocation HealthBarTexture = new ResourceLocation("PM", "textures/gui/health_bar.png");
	
	public  PolyMathGui(Minecraft mc)
	{
		super();
		//Invoke the render engine.
		this.mc = mc;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		//Draw these after the experience bar has drawn.
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
		{
			return;
		}
		PolyMathPlayer props = PolyMathPlayer.get(this.mc.thePlayer);
		//if the properties don't exist, then return.
		if (props == null || props.getMaxMana() == 0)
		{
			return;
		}
		int xPos = 2;
		int yPos = 2;
		//set mana bar width based on max mana
		int manaBarWidth = (int)(((float) props.getCurrentMana() / props.getMaxMana() * 50));
		//Make sure texture appears true
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		//Vanilla minecraft lighting bug counteraction
		GL11.glDisable(GL11.GL_LIGHTING);
		//Bind texture to render engine
		this.mc.getTextureManager().bindTexture(manaBarTexture);
		this.drawTexturedModalRect(xPos, yPos, 0, 0, manaBarWidth, 4);
		
		
		
	}
	
}
