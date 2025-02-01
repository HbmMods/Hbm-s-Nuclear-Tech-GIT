package com.hbm.render.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.config.ClientConfig;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.interfaces.Spaghetti;
import com.hbm.interfaces.Untested;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.impl.ItemGunStinger;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;

public class RenderScreenOverlay {

	public static final ResourceLocation misc = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_misc.png");
	public static final RenderItem itemRenderer = RenderItem.getInstance();
	
	private static long lastSurvey;
	private static float prevResult;
	private static float lastResult;
	
	private static float fadeOut = 0F;
	
	public static void renderRadCounter(ScaledResolution resolution, float in, Gui gui) {
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		float radiation = 0;

		radiation = lastResult - prevResult;

		if(System.currentTimeMillis() >= lastSurvey + 1000) {
			lastSurvey = System.currentTimeMillis();
			prevResult = lastResult;
			lastResult = in;
		}

		int length = 74;
		int maxRad = 1000;

		int bar = getScaled(in, maxRad, 74);

		int posX = 16 + ClientConfig.GEIGER_OFFSET_HORIZONTAL.get();
		int posY = resolution.getScaledHeight() - 20 - ClientConfig.GEIGER_OFFSET_VERTICAL.get();

		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		gui.drawTexturedModalRect(posX, posY, 0, 0, 94, 18);
		gui.drawTexturedModalRect(posX + 1, posY + 1, 1, 19, bar, 16);

		if(radiation >= 25) {
			gui.drawTexturedModalRect(posX + length + 2, posY - 18, 36, 36, 18, 18);

		} else if(radiation >= 10) {
			gui.drawTexturedModalRect(posX + length + 2, posY - 18, 18, 36, 18, 18);

		} else if(radiation >= 2.5) {
			gui.drawTexturedModalRect(posX + length + 2, posY - 18, 0, 36, 18, 18);

		}

		if(radiation > 1000) {
			Minecraft.getMinecraft().fontRenderer.drawString(">1000 RAD/s", posX, posY - 8, 0xFF0000);
		} else if(radiation >= 1) {
			Minecraft.getMinecraft().fontRenderer.drawString(((int) Math.round(radiation)) + " RAD/s", posX, posY - 8, 0xFF0000);
		} else if(radiation > 0) {
			Minecraft.getMinecraft().fontRenderer.drawString("<1 RAD/s", posX, posY - 8, 0xFF0000);
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	private static int getScaled(double cur, double max, double scale) {
		
		return (int) Math.min(cur / max * scale, scale);
	}

	
	public static void renderCustomCrosshairs(ScaledResolution resolution, Gui gui, Crosshair cross) {

		if(cross == Crosshair.NONE) {
			Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
			return;
		}

		int size = cross.size;

		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
		gui.drawTexturedModalRect(resolution.getScaledWidth() / 2 - (size / 2), resolution.getScaledHeight() / 2 - (size / 2), cross.x, cross.y, size, size);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static void renderStingerLockon(ScaledResolution resolution, Gui gui) {

		int progress = (int) (ItemGunStinger.lockon * 28);
		
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		GL11.glDisable(GL11.GL_BLEND);
		gui.drawTexturedModalRect(resolution.getScaledWidth() / 2 - 15, resolution.getScaledHeight() / 2  + 18, 146, 18, 30, 10);
		gui.drawTexturedModalRect(resolution.getScaledWidth() / 2 - 14, resolution.getScaledHeight() / 2  + 19, 147, 29, progress, 8);
		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static void renderAmmo(ScaledResolution resolution, Gui gui, ItemStack ammo, int count, int max, int dura, boolean renderCount) {
		
		GL11.glPushMatrix();
        
		Minecraft mc = Minecraft.getMinecraft();
		
		int pX = resolution.getScaledWidth() / 2 + 62 + 36;
		int pZ = resolution.getScaledHeight() - 21;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		gui.drawTexturedModalRect(pX, pZ + 16, 94, 0, 52, 3);
		gui.drawTexturedModalRect(pX + 1, pZ + 16, 95, 3, 50 - dura, 3);
		
		String cap = max == -1 ? ("∞") : ("" + max);
		
		if(renderCount)
			Minecraft.getMinecraft().fontRenderer.drawString(count + " / " + cap, pX + 16, pZ + 6, 0xFFFFFF);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.enableGUIStandardItemLighting();
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ammo, pX, pZ);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	public static void renderAmmoAlt(ScaledResolution resolution, Gui gui, ItemStack ammo, int count) {
		
		GL11.glPushMatrix();
        
		Minecraft mc = Minecraft.getMinecraft();
		
		int pX = resolution.getScaledWidth() / 2 + 62 + 36 + 18;
		int pZ = resolution.getScaledHeight() - 21 - 16;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		
		Minecraft.getMinecraft().fontRenderer.drawString(count + "x", pX + 16, pZ + 6, 0xFFFFFF);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        	itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), ammo, pX, pZ);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	@Spaghetti ("like a fella once said, aint that a kick in the head")
	public static void renderDashBar(ScaledResolution resolution, Gui gui, HbmPlayerProps props) {
		
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		Minecraft mc = Minecraft.getMinecraft();
		
		int width = 30;
		
		int posX = 16;//(int)(resolution.getScaledWidth()/2 - ((props.getDashCount()*(width+2))/2));
		int posY = resolution.getScaledHeight() - 40 - 2;
		
		mc.renderEngine.bindTexture(misc);
		
		gui.drawTexturedModalRect(posX-10, posY, 107, 18, 7, 10); 
		
		int stamina = props.getStamina();
		
		int dashes = props.getDashCount();
		
		//int count = props.getDashCount();
		//int x3count = count / 3;
		
		int rows = dashes / 3;
		int finalColumns = dashes % 3;
		
		for(int y = 0; y < rows; y++) {
			for(int x = 0; x < 3; x++) {
				if(y == rows && x > finalColumns) 
					break;
				gui.drawTexturedModalRect(posX + (width+2)*x, posY - 12*y, 76, 48, width, 10);
				int staminaDiv = stamina / 30;
				int staminaMod = stamina % 30;
				int barID = (3*y)+x;
				int barStatus = 1; //0 = red, 1 = normal, 2 = greyed, 3 = dashed, 4 = ascended
				int barSize = width;
				if(staminaDiv < barID) {
					barStatus = 3;
				} else if(staminaDiv == barID) {
					barStatus = 2;
					barSize = (int)((float)(stamina % 30) * (width/30F) );
					if(barID == 0)
						barStatus = 0;
				}
				gui.drawTexturedModalRect(posX + (width+2)*x, posY - 12*y, 76, 18+(10*barStatus), barSize, 10);
				
				if(staminaDiv == barID && staminaMod >= 27) {
					fadeOut = 1F;
				}
				if(fadeOut > 0 && staminaDiv-1 == barID) {
					GL11.glColor4f(1F, 1F, 1F, fadeOut);
					int bar = barID;
					if(stamina % 30 >= 25) 
						bar++;
					if(bar / 3 != y)
						y++;
					bar = bar % 3;
					gui.drawTexturedModalRect(posX + (width + 2) * bar, posY - 12 * y, 76, 58, width, 10);
					fadeOut -= 0.04F;
					GL11.glColor4f(1F, 1F, 1F, 1F);
				}
			}
		}
		
		/*for(int x = 0; x < props.getDashCount(); x++) {
			int status = 3;
			gui.drawTexturedModalRect(posX + (24)*x, posY, 76, 48, 24, 10);
			int staminaDiv = stamina / 60;
			if(staminaDiv > x) {
				status = 1;
			} else if(staminaDiv == x) {
				width = (int)( (float)(stamina % 60) * (width/60F) );
				status = 2;
				if(staminaDiv == 0)
					status = 0;
			}
			/*if(staminaDiv-1 == x && (stamina % 60 < 20 && stamina % 60 != 0)) {
				status = 4;
			}
			/*if(((staminaDiv == x && stamina % 60 >= 55) || (staminaDiv-1 == x && stamina % 60 <= 5)) && !(stamina == props.totalDashCount * 60)) {
				status = 4;
			}
			gui.drawTexturedModalRect(posX + (24)*x, posY, 76, 18+(10*status), width, 10); 
			
			if(staminaDiv == x && stamina % 60 >= 57) {
				fadeOut = 1F;
			}
			if(fadeOut > 0 && staminaDiv-1 == x) {
				GL11.glColor4f(1F, 1F, 1F, fadeOut);
				int bar = x;
				if(stamina % 60 >= 50)
					bar++;
				System.out.println(bar);
				gui.drawTexturedModalRect(posX + 24*bar, posY, 76, 58, width, 10);
				fadeOut -= 0.04F;
				GL11.glColor4f(1F, 1F, 1F, 1F);
			}
		}*/
		
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
		mc.renderEngine.bindTexture(Gui.icons);
	}
	
	//call in post health bar rendering event
	public static void renderShieldBar(ScaledResolution resolution, Gui gui) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;

		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		int left = width / 2 - 91;
		int top = height - GuiIngameForge.left_height;

		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
		gui.drawTexturedModalRect(left, top, 146, 0, 81, 9);
		int i = (int) Math.ceil(props.shield * 79 / props.getEffectiveMaxShield());
		gui.drawTexturedModalRect(left + 1, top, 147, 9, i, 9);
		
		String label = "" + ((int) (props.shield * 10F)) / 10D;
		font.drawString(label, left + 41 - font.getStringWidth(label) / 2, top + 1, 0x0000);
		font.drawString(label, left + 39 - font.getStringWidth(label) / 2, top + 1, 0x0000);
		font.drawString(label, left + 40 - font.getStringWidth(label) / 2, top, 0x0000);
		font.drawString(label, left + 40 - font.getStringWidth(label) / 2, top + 2, 0x0000);
		font.drawString(label, left + 40 - font.getStringWidth(label) / 2, top + 1, 0xFFFF80);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GuiIngameForge.left_height += 10;
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	@Untested
	public static void renderScope(ScaledResolution res, ResourceLocation tex) {

		GL11.glEnable(GL11.GL_BLEND);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		//GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(tex);
		Tessellator tess = Tessellator.instance;

		double w = res.getScaledWidth();
		double h = res.getScaledHeight();
		
		double smallest = Math.min(w, h);
		double divisor = smallest / (9D / 16D);
		smallest = 9D / 16D;
		double largest = Math.max(w, h) / divisor;

		double hMin = h < w ? 0.5 - smallest / 2D : 0.5 - largest / 2D;
		double hMax = h < w ? 0.5 + smallest / 2D : 0.5 + largest / 2D;
		double wMin = w < h ? 0.5 - smallest / 2D : 0.5 - largest / 2D;
		double wMax = w < h ? 0.5 + smallest / 2D : 0.5 + largest / 2D;
		
		double depth = -300D;
		
		tess.startDrawingQuads();
		
		tess.addVertexWithUV(0, h, depth, wMin, hMax);
		tess.addVertexWithUV(w, h, depth, wMax, hMax);
		tess.addVertexWithUV(w, 0, depth, wMax, hMin);
		tess.addVertexWithUV(0, 0, depth, wMin, hMin);
		tess.draw();
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
