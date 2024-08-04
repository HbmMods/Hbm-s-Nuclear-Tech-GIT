package com.hbm.main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.hbm.blocks.ICustomBlockHighlight;
import com.hbm.config.RadiationConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.WorldProviderCelestial;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.armor.IArmorDisableModel;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.items.armor.ItemModOxy;
import com.hbm.packet.PermaSyncHandler;
import com.hbm.render.model.ModelMan;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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
		
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
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
	
	float renderSoot = 0;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		
		if(event.phase == WorldTickEvent.Phase.START && RadiationConfig.enableSootFog) {

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
		if(event.entity.worldObj.provider instanceof WorldProviderCelestial) {
			WorldProviderCelestial provider = (WorldProviderCelestial) event.entity.worldObj.provider;
			float fogDensity = provider.fogDensity();
			
			if(fogDensity > 0) {
				if(GLContext.getCapabilities().GL_NV_fog_distance) {
					GL11.glFogi(34138, 34139);
				}
				GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
	
				event.density = fogDensity;
				event.setCanceled(true);

				return;
			}
		}

		float soot = (float) (renderSoot - RadiationConfig.sootFogThreshold);

		if(soot > 0 && RadiationConfig.enableSootFog) {
			
			float farPlaneDistance = (float) (Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16);
			float fogDist = farPlaneDistance / (1 + soot * 5F / (float) RadiationConfig.sootFogDivisor);
			GL11.glFogf(GL11.GL_FOG_START, 0);
			GL11.glFogf(GL11.GL_FOG_END, fogDist);

			if(GLContext.getCapabilities().GL_NV_fog_distance) {
				GL11.glFogi(34138, 34139);
			}
			
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void tintFog(FogColors event) {
		
		float soot = (float) (renderSoot - RadiationConfig.sootFogThreshold);
		float sootColor = 0.15F;
		float sootReq = (float) RadiationConfig.sootFogDivisor;
		if(soot > 0 && RadiationConfig.enableSootFog) {
			float interp = Math.min(soot / sootReq, 1F);
			event.red = event.red * (1 - interp) + sootColor * interp;
			event.green = event.green * (1 - interp) + sootColor * interp;
			event.blue = event.blue * (1 - interp) + sootColor * interp;
		}
	}
	
	@SubscribeEvent
	public void onRenderHUD(RenderGameOverlayEvent.Pre event) {
		Tessellator tess = Tessellator.instance;
		
		if(event.type == ElementType.HOTBAR && (ModEventHandlerClient.shakeTimestamp + ModEventHandlerClient.shakeDuration - System.currentTimeMillis()) > 0) {
			double mult = (ModEventHandlerClient.shakeTimestamp + ModEventHandlerClient.shakeDuration - System.currentTimeMillis()) / (double) ModEventHandlerClient.shakeDuration * 2;
			double horizontal = MathHelper.clamp_double(Math.sin(System.currentTimeMillis() * 0.02), -0.7, 0.7) * 15;
			double vertical = MathHelper.clamp_double(Math.sin(System.currentTimeMillis() * 0.01 + 2), -0.7, 0.7) * 3;
			GL11.glTranslated(horizontal * mult, vertical * mult, 0);
		} else if(event.type == ElementType.AIR) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			int width = event.resolution.getScaledWidth();
			int height = event.resolution.getScaledHeight();

			// If we're suffocating for a reason other than water, render the HUD bubbles
			int air = HbmLivingProps.getOxy(player);
			if(air < 100) {
				GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
	
				GL11.glEnable(GL11.GL_BLEND);
				int left = width / 2 + 91;
				int top = height - GuiIngameForge.right_height;
	
				int full = MathHelper.ceiling_double_int((double)(air - 2) * 10.0D / 100.0D);
				int partial = MathHelper.ceiling_double_int((double)air * 10.0D / 100.0D) - full;

				for(int i = 0; i < full + partial; ++i) {
					gui.drawTexturedModalRect(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
				}
				GuiIngameForge.right_height += 10;
	
				GL11.glDisable(GL11.GL_BLEND);
	
				// Prevent regular bubbles rendering
				event.setCanceled(true);
			}
			
			ItemStack tankStack = ArmorUtil.getOxygenTank(player);
			if(tankStack != null) {
				ItemModOxy tank = (ItemModOxy)tankStack.getItem();
				
				float tot = (float)ItemModOxy.getFuel(tankStack) / (float)tank.getMaxFuel();
				
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				int right = width / 2 + 91;
				int top = height - GuiIngameForge.right_height + 3;

				tess.startDrawingQuads();
				tess.setColorOpaque_F(0.25F, 0.25F, 0.25F);
				tess.addVertex(right - 81.5, top - 0.5, 0);
				tess.addVertex(right - 81.5, top + 4.5, 0);
				tess.addVertex(right + 0.5, top + 4.5, 0);
				tess.addVertex(right + 0.5, top - 0.5, 0);

				tess.setColorOpaque_F(1F - tot, tot, tot);
				tess.addVertex(right - 81 * tot, top, 0);
				tess.addVertex(right - 81 * tot, top + 4, 0);
				tess.addVertex(right, top + 4, 0);
				tess.addVertex(right, top, 0);
				tess.draw();

				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				GuiIngameForge.right_height += 6;

				event.setCanceled(true);
			}
		}
	}
}
