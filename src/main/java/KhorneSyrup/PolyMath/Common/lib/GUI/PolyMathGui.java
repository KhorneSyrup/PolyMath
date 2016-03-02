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
	private static final ResourceLocation GuiBackground = new ResourceLocation("PM", "textures/gui/gui_background.png");
	private static final ResourceLocation ManaBarTexture = new ResourceLocation( "PM", "textures/gui/mana_bar.png");
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
		int xPosGuiBase = 2;
		int yPosGuiBase = 2;
		int xPosBars = 25;
		int yPosBars = 4;

		//set mana bar width based on max mana

		/* TO DO: Need to change the way this works so that it can handle any amount of
		 * max mana-current mana ratio while keeping the ability to fill the bar completely.
		 * currently *55 does not make me happy.
		 */

		int healthBarWidth = (int)(((float) props.getCurrentHealth() / props.getMaxHealth()*91));
		int staminaBarWidth = (int)(((float) props.getCurrentStamina() / props.getMaxStamina()*91));
		int manaBarWidth = (int)(((float) props.getCurrentMana() / props.getMaxMana()*91));

		//Make sure texture appears true
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		//Vanilla minecraft lighting bug counteraction
		GL11.glDisable(GL11.GL_LIGHTING);

		//Bind texture to render engine
		this.mc.getTextureManager().bindTexture(GuiBackground);
		this.drawTexturedModalRect(xPosGuiBase, yPosGuiBase, 0, 0, 200, 31);

		//Draw Health Bar
		this.mc.getTextureManager().bindTexture(HealthBarTexture);
		this.drawTexturedModalRect(xPosBars, yPosBars, 0, 0, healthBarWidth, 8);
		String healthStatus = props.player.getHealth() + "/" + props.player.getMaxHealth();
		this.mc.fontRendererObj.drawString(healthStatus, xPosBars+23, yPosBars, 9999999);
		//Draw Stamina Bar
		this.mc.getTextureManager().bindTexture(StaminaBarTexture);
		this.drawTexturedModalRect(xPosBars, yPosBars+10, 0, 0, manaBarWidth, 8);
		String staminaStatus = props.getCurrentMana() + "/" + props.getMaxMana();
		this.mc.fontRendererObj.drawString(staminaStatus, xPosBars+23, yPosBars+10, 9999999);
		//Draw Mana Bar
		this.mc.getTextureManager().bindTexture(ManaBarTexture);
		this.drawTexturedModalRect(xPosBars, yPosBars+20, 0, 0, manaBarWidth, 8);
		String manaStatus = props.getCurrentMana() + "/" + props.getMaxMana();
		this.mc.fontRendererObj.drawString(manaStatus, xPosBars+23, yPosBars+20, 9999999);

		GlStateManager.popAttrib();

		}


    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRendererObj;
    }

}
