package KhorneSyrup.PolyMath.Common.lib.GUI;

import org.lwjgl.opengl.GL11;

import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
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
	private static final ResourceLocation manaBarTextureFill = new ResourceLocation( "PM", "textures/gui/mana_bar2.png");
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

		/* TO DO: Need to change the way this works so that it can handle any amount of
		 * max mana-current mana ratio while keeping the ability to fill the bar completely.
		 * currently *55 does not make me happy.
		 */

		int manaBarWidth = (int)(((float) props.getCurrentMana() / props.getMaxMana()*55));

		//Make sure texture appears true
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		//Vanilla minecraft lighting bug counteraction
		GL11.glDisable(GL11.GL_LIGHTING);

		//Bind texture to render engine
		this.mc.getTextureManager().bindTexture(manaBarTexture);
		this.drawTexturedModalRect(xPos, yPos, 0, 0, 200, 6);
		this.mc.getTextureManager().bindTexture(manaBarTextureFill);
		this.drawTexturedModalRect(xPos, yPos, 0, 0, manaBarWidth, 6);
		String s = props.getCurrentMana() + "/" + props.getMaxMana();
		this.mc.fontRendererObj.drawString(s, xPos, yPos, 9999999);
		GlStateManager.popAttrib();

		}


    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRendererObj;
    }

}
