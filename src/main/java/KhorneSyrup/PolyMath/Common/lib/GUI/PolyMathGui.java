package KhorneSyrup.PolyMath.Common.lib.GUI;

import org.lwjgl.opengl.GL11;

import KhorneSyrup.PolyMath.Common.lib.PolyMathPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;

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
		int manaBarWidth = (int)(((float) props.getCurrentMana() / props.getMaxMana() * 100));
		//Make sure texture appears true
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		//Vanilla minecraft lighting bug counteraction
		GL11.glDisable(GL11.GL_LIGHTING);
		//Bind texture to render engine
		this.mc.getTextureManager().bindTexture(manaBarTexture);
		this.drawTexturedModalRect(xPos, yPos, 0, 0, 100, 6);
		this.mc.getTextureManager().bindTexture(manaBarTextureFill);
		this.drawTexturedModalRect(xPos, yPos, 0, 0, manaBarWidth, 6);
		}

	 /*
		@SubscribeEvent(priority = EventPriority.NORMAL)
	  protected void renderManaBar(RenderGameOverlayEvent event)
	  {
		PolyMathPlayer props = PolyMathPlayer.get(this.mc.thePlayer);
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
		{
			return;
		}
	    {
            --BossStatus.statusBarTime;
            FontRenderer fontrenderer = this.mc.fontRendererObj;
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = scaledresolution.getScaledWidth();
            int j = -5;
            int k = i / 2 - j / 2;
            int l = (int)(props.getCurrentMana() * (float)(j + 1));
            int i1 = 12;
            this.drawTexturedModalRect(k, i1, 0, 74, j, 5);
            this.drawTexturedModalRect(k, i1, 0, 74, j, 5);

            if (l > 0)
            {
                this.drawTexturedModalRect(k, i1, 0, 79, l, 5);
            }

            String s = props.getCurrentMana() + "/" + props.getMaxMana();
            this.getFontRenderer().drawStringWithShadow(s, (float)(i / 2 - this.getFontRenderer().getStringWidth(s) / 2), (float)(i1 - 10), 16777215);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(manaBarTexture);
        }
	  }


/*
    public void renderManaBar(ScaledResolution p_175176_1_, int p_175176_2_)
    {
    	PolyMathPlayer props = PolyMathPlayer.get(this.mc.thePlayer);
        this.mc.mcProfiler.startSection("expBar");
        this.mc.getTextureManager().bindTexture(Gui.icons);
        int maxMana = props.getMaxMana();

        if (maxMana > 0)
        {
            int j = 182;
            int k = (int)(props.getCurrentMana() * (float)(j + 1));
            int l = p_175176_1_.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRect(p_175176_2_, l, 0, 64, j, 5);

            if (k > 0)
            {
                this.drawTexturedModalRect(p_175176_2_, l, 0, 69, k, 5);
            }
        }

        this.mc.mcProfiler.endSection();

        if (this.mc.thePlayer.experienceLevel > 0)
        {
            this.mc.mcProfiler.startSection("expLevel");
            int k1 = 8453920;
            String s = "" + props.getCurrentMana() +":" + props.getMaxMana();
            int l1 = (p_175176_1_.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int i1 = p_175176_1_.getScaledHeight() - 31 - 4;
            int j1 = 0;
            this.getFontRenderer().drawString(s, l1 + 1, i1, 0);
            this.getFontRenderer().drawString(s, l1 - 1, i1, 0);
            this.getFontRenderer().drawString(s, l1, i1 + 1, 0);
            this.getFontRenderer().drawString(s, l1, i1 - 1, 0);
            this.getFontRenderer().drawString(s, l1, i1, k1);
            this.mc.mcProfiler.endSection();
        }
    }
    */
    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRendererObj;
    }

}
