package com.hbm.main;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockAshes;
import com.hbm.items.armor.IArmorDisableModel;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ModEventHandlerRenderer {
	
	private static boolean[] partsHidden = new boolean[7];

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
		
		EntityPlayer player = event.entityPlayer;
		RenderPlayer renderer = event.renderer;
		
		for(int j = 0; j < 7; j++) {
			partsHidden[j] = false;
		}
		
		for(int i = 1; i < 5; i++) {
			ItemStack stack = player.getEquipmentInSlot(i);
			
			if(stack != null && stack.getItem() instanceof IArmorDisableModel) {
				IArmorDisableModel disable = (IArmorDisableModel) stack.getItem();
				
				for(int j = 0; j < 7; j++) {
					EnumPlayerPart type = EnumPlayerPart.values()[j];
					ModelRenderer box = getBoxFromType(renderer, type);
					if(disable.disablesPart(player, stack, type) && !box.isHidden) {
						partsHidden[j] = true;
						box.isHidden = true;
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
		
		RenderPlayer renderer = event.renderer;
		
		for(int j = 0; j < 7; j++) {
			EnumPlayerPart type = EnumPlayerPart.values()[j];
			if(partsHidden[j]) {
				getBoxFromType(renderer, type).isHidden = false;
			}
		}
	}
	
	private static ModelRenderer getBoxFromType(RenderPlayer renderer, EnumPlayerPart part) {
		
		switch(part) {
		case BODY:		return renderer.modelBipedMain.bipedBody;
		case HAT:		return renderer.modelBipedMain.bipedHeadwear;
		case HEAD:		return renderer.modelBipedMain.bipedHead;
		case LEFT_ARM:	return renderer.modelBipedMain.bipedLeftArm;
		case LEFT_LEG:	return renderer.modelBipedMain.bipedLeftLeg;
		case RIGHT_ARM:	return renderer.modelBipedMain.bipedRightArm;
		case RIGHT_LEG:	return renderer.modelBipedMain.bipedRightLeg;
		default:		return null;
		}
	}
	
	private ResourceLocation ashes = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_ash.png");
	public static int currentBrightness = 0;
	public static int lastBrightness = 0;
	
	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
		
		if(event.type == ElementType.AIR) {
			
			Minecraft mc = Minecraft.getMinecraft();
			
			ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
			
			int w = resolution.getScaledWidth();
			int h = resolution.getScaledHeight();
			double off = System.currentTimeMillis() / 10000D % 10000D;
			double aw = 1;
			
			Tessellator tessellator = Tessellator.instance;

			int cX = currentBrightness % 65536;
			int cY = currentBrightness / 65536;
			int lX = lastBrightness % 65536;
			int lY = lastBrightness / 65536;
			float interp = (mc.theWorld.getTotalWorldTime() % 20) * 0.05F;
			
			if(mc.theWorld.getTotalWorldTime() == 1)
				lastBrightness = currentBrightness;
			
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(lX + (cX - lX) * interp) / 1.0F, (float)(lY + (cY - lY) * interp) / 1.0F);

			//mc.entityRenderer.enableLightmap((double)event.partialTicks);
			
			mc.getTextureManager().bindTexture(ashes);
			
			for(int i = 1; i < 3; i++) {
				
				GL11.glTranslated(w, h, 0);
				GL11.glRotatef(-15, 0, 0, 1);
				GL11.glTranslated(-w, -h, 0);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, BlockAshes.ashes / 256F * 0.98F / i);
				
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-w * 1.25, 	h * 1.25, 	aw, 0.0D + off * i, 1.0D);
				tessellator.addVertexWithUV(w * 1.25, 	h * 1.25, 	aw, 1.0D + off * i, 1.0D);
				tessellator.addVertexWithUV(w * 1.25, 	-h * 1.25, 	aw, 1.0D + off * i, 0.0D);
				tessellator.addVertexWithUV(-w * 1.25, 	-h * 1.25, 	aw, 0.0D + off * i, 0.0D);
				tessellator.draw();
			}

			mc.entityRenderer.disableLightmap((double)event.partialTicks);
			
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);

			GL11.glPopMatrix();
		}
	}
}
