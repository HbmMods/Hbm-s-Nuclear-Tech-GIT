package com.hbm.render.item.weapon.sedna;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.Project;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemRenderWeaponBase implements IItemRenderer {
	
	public static float interp;

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
		switch(type) {
		case EQUIPPED_FIRST_PERSON:	setupFirstPerson(item);	renderFirstPerson(item); break;
		case EQUIPPED:				setupThirdPerson(item);	renderOther(item, type); break;
		case INVENTORY:				setupInv(item);			renderOther(item, type); break;
		case ENTITY:				setupEntity(item);		renderOther(item, type); break;
		}
		GL11.glPopMatrix();
	}

	public void setPerspectiveAndRender(ItemStack stack, float interp) {
		
		this.interp = interp;
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityRenderer entityRenderer = mc.entityRenderer;
		float farPlaneDistance = mc.gameSettings.renderDistanceChunks * 16;

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		Project.gluPerspective(this.getFOVModifier(interp, false), (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);

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
		float fov = 70.0F;

		if(useFOVSetting) fov = mc.gameSettings.fovSetting;

		if(entityplayer.getHealth() <= 0.0F) {
			float f2 = (float) entityplayer.deathTime + interp;
			fov /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
		}

		Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, entityplayer, interp);
		if(block.getMaterial() == Material.water) fov = fov * 60.0F / 70.0F;

		return fov;
	}
	
	protected void setupTransformsAndRender(ItemStack itemstack) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;

		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * interp;
		GL11.glPushMatrix();
		GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * interp, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		EntityPlayerSP entityplayersp = (EntityPlayerSP) player;
		float armPitch = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * interp;
		float armYaw = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * interp;
		GL11.glRotatef((player.rotationPitch - armPitch) * 0.1F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((player.rotationYaw - armYaw) * 0.1F, 0.0F, 1.0F, 0.0F);

		int i = mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if(itemstack != null) {
			int l = itemstack.getItem().getColorFromItemStack(itemstack, 0);
			float r = (float) (l >> 16 & 255) / 255.0F;
			float g = (float) (l >> 8 & 255) / 255.0F;
			float b = (float) (l & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}

		float f8;
		float f13;

		GL11.glPushMatrix();
		
		f13 = 0.8F;

		float swing = player.getSwingProgress(interp);
		float swingZ = MathHelper.sin(swing * (float) Math.PI);
		float swingX = MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI);
		GL11.glTranslatef(-swingX * 0.4F, MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI * 2.0F) * 0.2F, -swingZ * 0.2F);

		GL11.glTranslatef(0.7F * f13, -0.65F * f13 - (1.0F - 1/* raiseprogress */) * 0.6F, -0.9F * f13);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float swingYaw = MathHelper.sin(swing * swing * (float) Math.PI);
		float swingPitchRoll = MathHelper.sin(MathHelper.sqrt_float(swing) * (float) Math.PI);
		GL11.glRotatef(-swingYaw * 20.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-swingPitchRoll * 20.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-swingPitchRoll * 80.0F, 1.0F, 0.0F, 0.0F);
		
		f8 = 0.4F;
		GL11.glScalef(f8, f8, f8);

		this.renderItem(ItemRenderType.EQUIPPED_FIRST_PERSON, itemstack, null, player);

		GL11.glPopMatrix();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}
	
	protected void setupFirstPerson(ItemStack stack) {
		//GL11.glRotated(90, 0, 1, 0);
		//GL11.glRotated(40, -1, 0, 0);
	}
	
	protected void setupThirdPerson(ItemStack stack) {
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
	}
	
	protected void setupInv(ItemStack stack) {
		GL11.glScaled(1, 1, -1);
		GL11.glTranslated(8, 8, 0);
		GL11.glRotated(225, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
	}
	
	protected void setupEntity(ItemStack stack) {
		
	}

	public abstract void renderFirstPerson(ItemStack stack);
	public abstract void renderOther(ItemStack stack, ItemRenderType type);
}
