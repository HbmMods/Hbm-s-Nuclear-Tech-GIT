package com.hbm.render.item.weapon.sedna;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

import com.hbm.config.ClientConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.SmokeNode;
import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemRenderWeaponBase implements IItemRenderer {
	
	public static final ResourceLocation flash_plume = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/lilmac_plume.png");
	
	public static float interp;
	
	public boolean isAkimbo() { return false; }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION;
	}

	@SuppressWarnings("incomplete-switch") //shut the fuck up
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		switch(type) {
		case EQUIPPED_FIRST_PERSON:	setupFirstPerson(item);	renderFirstPerson(item); break;
		case EQUIPPED:				setupThirdPerson(item);	renderEquipped(item); break;
		case INVENTORY:				setupInv(item);			renderInv(item); break;
		case ENTITY:				setupEntity(item);		renderEntity(item); break;
		}
		GL11.glPopMatrix();
	}

	public void renderEquipped(ItemStack stack) { renderOther(stack, ItemRenderType.EQUIPPED); }
	public void renderEquippedAkimbo(ItemStack stack) { renderOther(stack, ItemRenderType.EQUIPPED); }
	public void renderInv(ItemStack stack) { renderOther(stack, ItemRenderType.INVENTORY); }
	public void renderEntity(ItemStack stack) { renderOther(stack, ItemRenderType.ENTITY); }

	public void setPerspectiveAndRender(ItemStack stack, float interp) {
		
		this.interp = interp;
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityRenderer entityRenderer = mc.entityRenderer;
		float farPlaneDistance = mc.gameSettings.renderDistanceChunks * 16;

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		Project.gluPerspective(this.getFOVModifier(interp, ClientConfig.GUN_MODEL_FOV.get()), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glPushMatrix();

		if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping() && !mc.gameSettings.hideGUI && !mc.playerController.enableEverythingIsScrewedUpMode()) {
			entityRenderer.enableLightmap(interp);
			this.setupTransformsAndRender(stack);
			entityRenderer.disableLightmap(interp);
		}

		GL11.glPopMatrix();

		if(mc.gameSettings.thirdPersonView == 0 && !mc.renderViewEntity.isPlayerSleeping()) {
			entityRenderer.itemRenderer.renderOverlays(interp);
		}
	}

	private float getFOVModifier(float interp, boolean useFOVSetting) {
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityLivingBase entityplayer = (EntityLivingBase) mc.renderViewEntity;
		float fov = getBaseFOV(entityplayer.getHeldItem());

		if(useFOVSetting) fov = mc.gameSettings.fovSetting;

		if(entityplayer.getHealth() <= 0.0F) {
			float f2 = (float) entityplayer.deathTime + interp;
			fov /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
		}

		Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, entityplayer, interp);
		if(block.getMaterial() == Material.water) fov = fov * 60.0F / 70.0F;

		return fov;
	}

	protected float getBaseFOV(ItemStack stack) { return 70F; }
	public float getViewFOV(ItemStack stack, float fov) { return  fov; }
	protected float getSwayMagnitude(ItemStack stack) { return ItemGunBaseNT.getIsAiming(stack) ? 0.1F : 0.5F; }
	protected float getSwayPeriod(ItemStack stack) { return 0.75F; }
	protected float getTurnMagnitude(ItemStack stack) { return 2.75F; }
	
	protected void setupTransformsAndRender(ItemStack stack) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;

		float swayMagnitude = getSwayMagnitude(stack);
		float swayPeriod = getSwayPeriod(stack);
		float turnMagnitude = getTurnMagnitude(stack);

		//lighting setup (item lighting changes based on player rotation)
		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * interp;
		float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * interp;
		
		GL11.glPushMatrix();
		GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		
		//floppyness
		EntityPlayerSP entityplayersp = (EntityPlayerSP) player;
		float armPitch = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * interp;
		float armYaw = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * interp;
		GL11.glRotatef((player.rotationPitch - armPitch) * 0.1F * turnMagnitude, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((player.rotationYaw - armYaw) * 0.1F * turnMagnitude, 0.0F, 1.0F, 0.0F);

		//brightness setup
		int brightness = mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
		int j = brightness % 65536;
		int k = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		//color setup
		int color = stack.getItem().getColorFromItemStack(stack, 0);
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		GL11.glColor4f(r, g, b, 1.0F);

		GL11.glPushMatrix();
		
		//swing
		/*float swing = player.getSwingProgress(interp);
		float swingZ = MathHelper.sin(swing * (float) Math.PI);
		float swingX = MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI);
		GL11.glTranslatef(-swingX * 0.4F, MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI * 2.0F) * 0.2F, -swingZ * 0.2F);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float swingYaw = MathHelper.sin(swing * swing * (float) Math.PI);
		float swingPitchRoll = MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI);
		GL11.glRotatef(-swingYaw * 20.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-swingPitchRoll * 20.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-swingPitchRoll * 80.0F, 1.0F, 0.0F, 0.0F);*/

		GL11.glEnable(GL12.GL_RESCALE_NORMAL); //!
		
		GL11.glRotated(180, 0, 1, 0);

		//viewbob
		if(mc.renderViewEntity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) mc.renderViewEntity;
			float distanceDelta = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
			float distanceInterp = -(entityplayer.distanceWalkedModified + distanceDelta * interp);
			float camYaw = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * interp;
			float camPitch = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * interp;
			GL11.glTranslatef(MathHelper.sin(distanceInterp * (float) Math.PI * swayPeriod) * camYaw * 0.5F * swayMagnitude, -Math.abs(MathHelper.cos(distanceInterp * (float) Math.PI * swayPeriod) * camYaw) * swayMagnitude, 0.0F);
			GL11.glRotatef(MathHelper.sin(distanceInterp * (float) Math.PI * swayPeriod) * camYaw * 3.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(Math.abs(MathHelper.cos(distanceInterp * (float) Math.PI * swayPeriod - 0.2F) * camYaw) * 5.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(camPitch, 1.0F, 0.0F, 0.0F);
		}
		
		this.renderItem(ItemRenderType.EQUIPPED_FIRST_PERSON, stack, null, player);

		GL11.glPopMatrix();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}
	
	public void setupFirstPerson(ItemStack stack) {
		GL11.glTranslated(0, 0, 1);
		
		if(Minecraft.getMinecraft().thePlayer.isSneaking()) {
			GL11.glTranslated(0, -3.875 / 8D, 0);
		} else {
			float offset = 0.8F;
			GL11.glRotated(180, 0, 1, 0);
			GL11.glTranslatef(1.0F * offset, -0.75F * offset, -0.5F * offset);
			GL11.glRotated(180, 0, 1, 0);
		}
	}
	
	public void setupThirdPerson(ItemStack stack) {
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(12.5F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
		
		GL11.glTranslated(3.5, 0, 0);

	}
	
	public void setupThirdPersonAkimbo(ItemStack stack) {
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(12.5F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(10.0F, 1.0F, 0.0F, 0.0F);
		
		GL11.glTranslated(5, 0, 0);

	}
	
	public void setupInv(ItemStack stack) {
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
	}
	
	public void setupEntity(ItemStack stack) {
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
	}

	public abstract void renderFirstPerson(ItemStack stack);
	public void renderOther(ItemStack stack, ItemRenderType type) { }
	
	public static void standardAimingTransform(ItemStack stack, double sX, double sY, double sZ, double aX, double aY, double aZ) {
		float aimingProgress = ItemGunBaseNT.prevAimingProgress + (ItemGunBaseNT.aimingProgress - ItemGunBaseNT.prevAimingProgress) * interp;
		double x = sX + (aX - sX) * aimingProgress;
		double y = sY + (aY - sY) * aimingProgress;
		double z = sZ + (aZ - sZ) * aimingProgress;
		GL11.glTranslated(x, y, z);
	}
	
	public static void renderSmokeNodes(List<SmokeNode> nodes, double scale) {
		Tessellator tess = Tessellator.instance;
		
		if(nodes.size() > 1) {

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glDepthMask(false);

			tess.startDrawingQuads();
			tess.setNormal(0F, 1F, 0F);
			
			for(int i = 0; i < nodes.size() - 1; i++) {
				SmokeNode node = nodes.get(i);
				SmokeNode past = nodes.get(i + 1);
				
				tess.setColorRGBA_F(1F, 1F, 1F, (float) node.alpha);
				tess.addVertex(node.forward, node.lift, node.side);
				tess.setColorRGBA_F(1F, 1F, 1F, 0F);
				tess.addVertex(node.forward, node.lift, node.side + node.width * scale);
				tess.setColorRGBA_F(1F, 1F, 1F, 0F);
				tess.addVertex(past.forward, past.lift, past.side + past.width * scale);
				tess.setColorRGBA_F(1F, 1F, 1F, (float) past.alpha);
				tess.addVertex(past.forward, past.lift, past.side);
				
				tess.setColorRGBA_F(1F, 1F, 1F, (float) node.alpha);
				tess.addVertex(node.forward, node.lift, node.side);
				tess.setColorRGBA_F(1F, 1F, 1F, 0F);
				tess.addVertex(node.forward, node.lift, node.side - node.width * scale);
				tess.setColorRGBA_F(1F, 1F, 1F, 0F);
				tess.addVertex(past.forward, past.lift, past.side - past.width * scale);
				tess.setColorRGBA_F(1F, 1F, 1F, (float) past.alpha);
				tess.addVertex(past.forward, past.lift, past.side);
			}
			tess.draw();
			
			GL11.glDepthMask(true);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	public static void renderMuzzleFlash(long lastShot) {
		renderMuzzleFlash(lastShot, 75, 15);
	}
	
	public static void renderMuzzleFlash(long lastShot, int duration, double l) {
		Tessellator tess = Tessellator.instance;
		
		int flash = duration;
		
		if(System.currentTimeMillis() - lastShot < flash) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glPushMatrix();
			
			double fire = (System.currentTimeMillis() - lastShot) / (double) flash;
			
			double width = 6 * fire;
			double length = l * fire;
			double inset = 2;
			Minecraft.getMinecraft().renderEngine.bindTexture(flash_plume);
			tess.startDrawingQuads();
			tess.setNormal(0F, 1F, 0F);
			tess.setColorRGBA_F(1F, 1F, 1F, 1F);
			
			tess.addVertexWithUV(0, -width, - inset, 1, 1);
			tess.addVertexWithUV(0, width, - inset, 0, 1);
			tess.addVertexWithUV(0.1, width, length - inset, 0 ,0);
			tess.addVertexWithUV(0.1, -width, length - inset, 1, 0);

			tess.addVertexWithUV(0, width, inset, 0, 1);
			tess.addVertexWithUV(0, -width, inset, 1, 1);
			tess.addVertexWithUV(0.1, -width, -length + inset, 1, 0);
			tess.addVertexWithUV(0.1, width, -length + inset, 0 ,0);

			tess.addVertexWithUV(0, - inset, width, 0, 1);
			tess.addVertexWithUV(0, - inset, -width, 1, 1);
			tess.addVertexWithUV(0.1, length - inset, -width, 1, 0);
			tess.addVertexWithUV(0.1, length - inset, width, 0 ,0);

			tess.addVertexWithUV(0, inset, -width, 1, 1);
			tess.addVertexWithUV(0, inset, width, 0, 1);
			tess.addVertexWithUV(0.1, -length + inset, width, 0 ,0);
			tess.addVertexWithUV(0.1, -length + inset, -width, 1, 0);
			
			tess.draw();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	public static void renderGapFlash(long lastShot) {
		Tessellator tess = Tessellator.instance;
		
		int flash = 75;
		
		if(System.currentTimeMillis() - lastShot < flash) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glPushMatrix();
			
			double fire = (System.currentTimeMillis() - lastShot) / (double) flash;
			
			double height = 4 * fire;
			double length = 15 * fire;
			double lift = 3 * fire;
			double offset = 1 * fire;
			double lengthOffset = 0.125;
			Minecraft.getMinecraft().renderEngine.bindTexture(flash_plume);
			tess.startDrawingQuads();
			tess.setNormal(0F, 1F, 0F);
			tess.setColorRGBA_F(1F, 1F, 1F, 1F);
			
			tess.addVertexWithUV(0, -height, -offset, 1, 1);
			tess.addVertexWithUV(0, height, -offset, 0, 1);
			tess.addVertexWithUV(0, height + lift, length - offset, 0 ,0);
			tess.addVertexWithUV(0, -height + lift, length - offset, 1, 0);

			tess.addVertexWithUV(0, height, offset, 0, 1);
			tess.addVertexWithUV(0, -height, offset, 1, 1);
			tess.addVertexWithUV(0, -height + lift, -length + offset, 1, 0);
			tess.addVertexWithUV(0, height + lift, -length + offset, 0 ,0);
			
			tess.addVertexWithUV(0, -height, -offset, 1, 1);
			tess.addVertexWithUV(0, height, -offset, 0, 1);
			tess.addVertexWithUV(lengthOffset, height, length - offset, 0 ,0);
			tess.addVertexWithUV(lengthOffset, -height, length - offset, 1, 0);

			tess.addVertexWithUV(0, height, offset, 0, 1);
			tess.addVertexWithUV(0, -height, offset, 1, 1);
			tess.addVertexWithUV(lengthOffset, -height, -length + offset, 1, 0);
			tess.addVertexWithUV(lengthOffset, height, -length + offset, 0 ,0);
			
			tess.draw();
			GL11.glPopMatrix();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
}
