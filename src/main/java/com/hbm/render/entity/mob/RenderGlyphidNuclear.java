package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.entity.mob.glyphid.EntityGlyphidNuclear;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderGlyphidNuclear extends RenderLiving {
	
	public RenderGlyphidNuclear() {
		super(new ModelGlyphid(), 1.0F);
		this.shadowOpaque = 0.0F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		EntityGlyphid glyphid = (EntityGlyphid) entity;
		return glyphid.getSkin();
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float interp) {
		this.preRenderCallback((EntityGlyphidNuclear) entity, interp);
	}
	
	protected void preRenderCallback(EntityGlyphidNuclear entity, float interp) {
		float swell = (float) (entity.deathTicks + interp) / 95F;
		float flash = 1.0F + MathHelper.sin(swell * 100.0F) * swell * 0.01F;

		if(swell < 0.0F) {
			swell = 0.0F;
		}

		if(swell > 1.0F) {
			swell = 1.0F;
		}

		swell *= swell;
		swell *= swell;
		
		float scaleHorizontal = (1.0F + swell * 0.4F) * flash;
		float scaleVertical = (1.0F + swell * 0.1F) / flash;
		GL11.glScalef(scaleHorizontal, scaleVertical, scaleHorizontal);
	}

	@Override
	protected int getColorMultiplier(EntityLivingBase entity, float lightBrightness, float interp) {
		return this.getColorMultiplier((EntityGlyphidNuclear) entity, lightBrightness, interp);
	}
	
	protected int getColorMultiplier(EntityGlyphidNuclear entity, float lightBrightness, float interp) {
		float swell = (float) (entity.deathTicks + interp) / 20F;
		


		
		int a = (int) (swell * 0.2F * 255.0F);
		
		if((int) (swell * 10.0F) % 4 < 2)
			return a *= 0.75;

		if(a < 0) {
			a = 0;
		}

		if(a > 255) {
			a = 255;
		}

		short r = 255;
		short g = 255;
		short b = 255;
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	public static class ModelGlyphid extends ModelBase {
		
		double bite = 0;

		@Override
		public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float interp) {
			bite = entity.getSwingProgress(interp);
		}

		@Override
		public void render(Entity entity, float limbSwing, float limbSwingAmount, float rotationYaw, float rotationHeadYaw, float rotationPitch, float scale) {
			GL11.glPushMatrix();

			GL11.glRotatef(180, 1, 0, 0);
			GL11.glTranslatef(0, -1.5F, 0);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			
			double s = ((EntityGlyphid) entity).getScale();
			GL11.glScaled(s, s, s);
			
			EntityLivingBase living = (EntityLivingBase) entity;
			byte armor = living.getDataWatcher().getWatchableObjectByte(17);
			
			double walkCycle = limbSwing;

			double cy0 = Math.sin(walkCycle % (Math.PI * 2));
			double cy1 = Math.sin(walkCycle % (Math.PI * 2) - Math.PI * 0.5);
			double cy2 = Math.sin(walkCycle % (Math.PI * 2) - Math.PI);
			double cy3 = Math.sin(walkCycle % (Math.PI * 2) - Math.PI * 0.75);

			double bite = MathHelper.clamp_double(Math.sin(this.bite * Math.PI * 2 - Math.PI * 0.5), 0, 1) * 20;
			double headTilt = Math.sin(this.bite * Math.PI) * 30;
			
			ResourceManager.glyphid.renderPart("Body");
			if((armor & (1 << 0)) > 0) ResourceManager.glyphid.renderPart("ArmorFront");
			if((armor & (1 << 1)) > 0) ResourceManager.glyphid.renderPart("ArmorLeft");
			if((armor & (1 << 2)) > 0) ResourceManager.glyphid.renderPart("ArmorRight");

			/// LEFT ARM ///
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, 0.625, 0.0625);
			GL11.glRotated(10, 0, 1, 0);
			GL11.glRotated(35 + cy1 * 20, 1, 0, 0);
			GL11.glTranslated(-0.25, -0.625, -0.0625);
			ResourceManager.glyphid.renderPart("ArmLeftUpper");
			GL11.glTranslated(0.25, 0.625, 0.4375);
			GL11.glRotated(-75 - cy1 * 20 + cy0 * 20, 1, 0, 0);
			GL11.glTranslated(-0.25, -0.625, -0.4375);
			ResourceManager.glyphid.renderPart("ArmLeftMid");
			GL11.glTranslated(0.25, 0.625, 0.9375);
			GL11.glRotated(90 - cy0 * 45, 1, 0, 0);
			GL11.glTranslated(-0.25, -0.625, -0.9375);
			ResourceManager.glyphid.renderPart("ArmLeftLower");
			if((armor & (1 << 3)) > 0) ResourceManager.glyphid.renderPart("ArmLeftArmor");
			GL11.glPopMatrix();

			/// RIGHT ARM ///
			GL11.glPushMatrix();
			GL11.glTranslated(-0.25, 0.625, 0.0625);
			GL11.glRotated(-10, 0, 1, 0);
			GL11.glRotated(35 + cy2 * 20, 1, 0, 0);
			GL11.glTranslated(0.25, -0.625, -0.0625);
			ResourceManager.glyphid.renderPart("ArmRightUpper");
			GL11.glTranslated(-0.25, 0.625, 0.4375);
			GL11.glRotated(-75 - cy2 * 20 + cy3 * 20, 1, 0, 0);
			GL11.glTranslated(0.25, -0.625, -0.4375);
			ResourceManager.glyphid.renderPart("ArmRightMid");
			GL11.glTranslated(-0.25, 0.625, 0.9375);
			GL11.glRotated(90 - cy3 * 45, 1, 0, 0);
			GL11.glTranslated(0.25, -0.625, -0.9375);
			ResourceManager.glyphid.renderPart("ArmRightLower");
			if((armor & (1 << 4)) > 0) ResourceManager.glyphid.renderPart("ArmRightArmor");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			
			GL11.glTranslated(0, 0.5, 0.25);
			GL11.glRotated(headTilt, 0, 0, 1);
			GL11.glTranslated(0, -0.5, -0.25);
			
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.5, 0.25);
			GL11.glRotated(-bite, 1, 0, 0);
			GL11.glTranslated(0, -0.5, -0.25);
			ResourceManager.glyphid.renderPart("JawTop");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.5, 0.25);
			GL11.glRotated(bite, 0, 1, 0);
			GL11.glRotated(bite, 1, 0, 0);
			GL11.glTranslated(0, -0.5, -0.25);
			ResourceManager.glyphid.renderPart("JawLeft");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(0, 0.5, 0.25);
			GL11.glRotated(-bite, 0, 1, 0);
			GL11.glRotated(bite, 1, 0, 0);
			GL11.glTranslated(0, -0.5, -0.25);
			ResourceManager.glyphid.renderPart("JawRight");
			GL11.glPopMatrix();
			GL11.glPopMatrix();

			double steppy = 15;
			double bend = 60;
			
			for(int i = 0; i < 3; i++) {
				
				double c0 = cy0 * (i == 1 ? -1 : 1);
				double c1 = cy1 * (i == 1 ? -1 : 1);
				
				GL11.glPushMatrix();
				GL11.glTranslated(0, 0.25, 0);
				GL11.glRotated(i * 30 - 15 + c0 * 7.5, 0, 1, 0);
				GL11.glRotated(steppy + c1 * steppy, 0, 0, 1);
				GL11.glTranslated(0, -0.25, 0);
				ResourceManager.glyphid.renderPart("LegLeftUpper");
				GL11.glTranslated(0.5625, 0.25, 0);
				GL11.glRotated(-bend - c1 * steppy, 0, 0, 1);
				GL11.glTranslated(-0.5625, -0.25, 0);
				ResourceManager.glyphid.renderPart("LegLeftLower");
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				GL11.glTranslated(0, 0.25, 0);
				GL11.glRotated(i * 30 - 45 + c0 * 7.5, 0, 1, 0);
				GL11.glRotated(-steppy + c1 * steppy, 0, 0, 1);
				GL11.glTranslated(0, -0.25, 0);
				ResourceManager.glyphid.renderPart("LegRightUpper");
				GL11.glTranslated(-0.5625, 0.25, 0);
				GL11.glRotated(bend - c1 * steppy, 0, 0, 1);
				GL11.glTranslated(0.5625, -0.25, 0);
				ResourceManager.glyphid.renderPart("LegRightLower");
				GL11.glPopMatrix();
			}
			

			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glTranslated(0, 1, 0);
			GL11.glRotated(90, 1, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mini_nuke_tex);
			ResourceManager.projectiles.renderPart("MiniNuke");
			GL11.glShadeModel(GL11.GL_FLAT);
			
			GL11.glPopMatrix();
		}
	}
}
