package com.hbm.main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.hbm.blocks.ICustomBlockHighlight;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.armor.IArmorDisableModel;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.packet.PermaSyncHandler;
import com.hbm.render.model.ModelMan;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ModEventHandlerRenderer {

	private static ModelMan manlyModel;
	private static boolean[] partsHidden = new boolean[7];

	@SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {

		EntityPlayer player = event.entityPlayer;
		RenderPlayer renderer = event.renderer;

		boolean isManly = PermaSyncHandler.boykissers.contains(player.getEntityId());

		for(int j = 0; j < 7; j++) {

			if(isManly) {
				partsHidden[j] = true;
				EnumPlayerPart type = EnumPlayerPart.values()[j];
				ModelRenderer box = getBoxFromType(renderer, type);
				box.isHidden = true;
			} else {
				partsHidden[j] = false;
			}
		}

		if(isManly) {
			return;
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

	@SubscribeEvent
	public void onRenderArmorEvent(RenderPlayerEvent.SetArmorModel event) {

		EntityPlayer player = event.entityPlayer;
		RenderPlayer renderer = event.renderer;

		boolean isManly = PermaSyncHandler.boykissers.contains(player.getEntityId());

		if(isManly) {
			if(manlyModel == null)
				manlyModel = new ModelMan();
			float interp = event.partialRenderTick;
			float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
			float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
			float yaw = yawHead - yawOffset;
			float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
			float pitch = player.rotationPitch;
			float f6 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * interp;
			float f7 = player.limbSwing - player.limbSwingAmount * (1.0F - interp);
			if(f6 > 1.0F) {
				f6 = 1.0F;
			}
			manlyModel.render(event.entityPlayer, f7, f6, yawWrapped, yaw, pitch, 0.0625F, renderer);
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

	@SubscribeEvent
	public void onRenderHeldItem(RenderPlayerEvent.Specials.Pre event) {

		EntityPlayer player = event.entityPlayer;
		//RenderPlayer renderer = event.renderer;

		boolean isManly = PermaSyncHandler.boykissers.contains(player.getEntityId());

		if(!isManly)
			return;

		if(manlyModel == null)
			manlyModel = new ModelMan();
		
		event.renderItem = false;

		float f2 = 1.3333334F;

		ItemStack held = player.getHeldItem();
		
		if(held == null)
			return;
		
		GL11.glPushMatrix();
		manlyModel.rightArm.postRender(0.0625F);
		GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

		if(player.fishEntity != null) {
			held = new ItemStack(Items.stick);
		}

		EnumAction enumaction = null;

		if(player.getItemInUseCount() > 0) {
			enumaction = held.getItemUseAction();
		}

		net.minecraftforge.client.IItemRenderer customRenderer = net.minecraftforge.client.MinecraftForgeClient.getItemRenderer(held, net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED);
		boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED, held, net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D));

		if(is3D || held.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(held.getItem()).getRenderType())) {
			f2 = 0.5F;
			GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
			f2 *= 0.75F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(-f2, -f2, f2);
		} else if(held.getItem() == Items.bow) {
			f2 = 0.625F;
			GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
			GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f2, -f2, f2);
			GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		} else if(held.getItem().isFull3D()) {
			f2 = 0.625F;

			if(held.getItem().shouldRotateAroundWhenRendering()) {
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.0F, -0.125F, 0.0F);
			}

			if(player.getItemInUseCount() > 0 && enumaction == EnumAction.block) {
				GL11.glTranslatef(0.05F, 0.0F, -0.1F);
				GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
			}

			GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
			GL11.glScalef(f2, -f2, f2);
			GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		} else {
			f2 = 0.375F;
			GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
			GL11.glScalef(f2, f2, f2);
			GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
		}

		float f3;
		int k;
		float f12;

		if(held.getItem().requiresMultipleRenderPasses()) {
			for(k = 0; k < held.getItem().getRenderPasses(held.getItemDamage()); ++k) {
				int i = held.getItem().getColorFromItemStack(held, k);
				f12 = (float) (i >> 16 & 255) / 255.0F;
				f3 = (float) (i >> 8 & 255) / 255.0F;
				float f4 = (float) (i & 255) / 255.0F;
				GL11.glColor4f(f12, f3, f4, 1.0F);
				RenderManager.instance.itemRenderer.renderItem(player, held, k);
			}
		} else {
			k = held.getItem().getColorFromItemStack(held, 0);
			float f11 = (float) (k >> 16 & 255) / 255.0F;
			f12 = (float) (k >> 8 & 255) / 255.0F;
			f3 = (float) (k & 255) / 255.0F;
			GL11.glColor4f(f11, f12, f3, 1.0F);
			RenderManager.instance.itemRenderer.renderItem(player, held, 0);
		}

		GL11.glPopMatrix();
	}

	private static ModelRenderer getBoxFromType(RenderPlayer renderer, EnumPlayerPart part) {

		switch(part) {
		case BODY: return renderer.modelBipedMain.bipedBody;
		case HAT: return renderer.modelBipedMain.bipedHeadwear;
		case HEAD: return renderer.modelBipedMain.bipedHead;
		case LEFT_ARM: return renderer.modelBipedMain.bipedLeftArm;
		case LEFT_LEG: return renderer.modelBipedMain.bipedLeftLeg;
		case RIGHT_ARM: return renderer.modelBipedMain.bipedRightArm;
		case RIGHT_LEG: return renderer.modelBipedMain.bipedRightLeg;
		default: return null;
		}
	}
	
	@SubscribeEvent
	public void onDrawHighlight(DrawBlockHighlightEvent event) {
		MovingObjectPosition mop = event.target;
		
		if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
			Block b = event.player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			if(b instanceof ICustomBlockHighlight) {
				ICustomBlockHighlight cus = (ICustomBlockHighlight) b;
				
				if(cus.shouldDrawHighlight(event.player.worldObj, mop.blockX, mop.blockY, mop.blockZ)) {
					cus.drawHighlight(event, event.player.worldObj, mop.blockX, mop.blockY, mop.blockZ);
					event.setCanceled(true);
				}
			}
		}
	}

	//private ResourceLocation ashes = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_ash.png");
	public static int currentBrightness = 0;
	public static int lastBrightness = 0;

	/*@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {

		if(event.type == ElementType.PORTAL) {

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

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) (lX + (cX - lX) * interp) / 1.0F, (float) (lY + (cY - lY) * interp) / 1.0F);

			// mc.entityRenderer.enableLightmap((double)event.partialTicks);

			mc.getTextureManager().bindTexture(ashes);

			for(int i = 1; i < 3; i++) {

				GL11.glTranslated(w, h, 0);
				GL11.glRotatef(-15, 0, 0, 1);
				GL11.glTranslated(-w, -h, 0);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, BlockAshes.ashes / 256F * 0.98F / i);

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-w * 1.25, h * 1.25, aw, 0.0D + off * i, 1.0D);
				tessellator.addVertexWithUV(w * 1.25, h * 1.25, aw, 1.0D + off * i, 1.0D);
				tessellator.addVertexWithUV(w * 1.25, -h * 1.25, aw, 1.0D + off * i, 0.0D);
				tessellator.addVertexWithUV(-w * 1.25, -h * 1.25, aw, 0.0D + off * i, 0.0D);
				tessellator.draw();
			}

			mc.entityRenderer.disableLightmap((double) event.partialTicks);

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);

			GL11.glPopMatrix();
		}
	}*/
	
	float renderSoot = 0;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		
		if(event.phase == event.phase.START) {

			float step = 0.05F;
			float soot = PermaSyncHandler.pollution[PollutionType.SOOT.ordinal()];
			
			if(Math.abs(renderSoot - soot) < step) {
				renderSoot = soot;
			} else if(renderSoot < soot) {
				renderSoot += step;
			} else if(renderSoot > soot) {
				renderSoot -= step;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void thickenFog(FogDensity event) {
		float soot = renderSoot - 35;
		if(soot > 0) {
			//event.density = Math.min((soot - 5) * 0.01F, 0.5F);
			float farPlaneDistance = (float) (Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16);
			float fogDist = farPlaneDistance / (1 + soot * 0.05F);
			GL11.glFogf(GL11.GL_FOG_START, 0);
			GL11.glFogf(GL11.GL_FOG_END, fogDist);

			if(GLContext.getCapabilities().GL_NV_fog_distance) {
				GL11.glFogi(34138, 34139);
			}
			//GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			//GL11.glFogf(GL11.GL_FOG_DENSITY, 2F);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void tintFog(FogColors event) {
		float soot = renderSoot - 35;
		float sootColor = 0.15F;
		float sootReq = 100F;
		if(soot > 0) {
			float interp = Math.min(soot / sootReq, 1F);
			event.red = event.red * (1 - interp) + sootColor * interp;
			event.green = event.green * (1 - interp) + sootColor * interp;
			event.blue = event.blue * (1 - interp) + sootColor * interp;
		}
	}
}
