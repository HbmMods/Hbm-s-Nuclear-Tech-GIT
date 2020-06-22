package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelHunterChopper;
import com.hbm.render.model.ProtoCopter;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class RenderHunterChopper extends Render {

	ProtoCopter mine;
	ModelHunterChopper mine2;

	public RenderHunterChopper() {
		mine = new ProtoCopter();
		mine2 = new ModelHunterChopper();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
        BossStatus.setBossStatus((EntityHunterChopper)rocket, true);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glTranslatef(0.0625F * 0, 0.0625F * 32, 0.0625F * 0);
		GL11.glTranslatef(0.0625F * 0, 0.0625F * 12, 0.0625F * 0);
		GL11.glScalef(4F, 4F, 4F);
		GL11.glRotatef(180, 1, 0, 0);
		
		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * p_76986_9_ - 90.0F, 0, 1.0F, 0);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * p_76986_9_, 0, 0, 1.0F);
		
		bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/chopper.png"));
		
        //if(rocket instanceof EntityHunterChopper)
        //	mine2.setGunRotations((EntityHunterChopper)rocket, yaw, pitch);
		
		mine2.renderAll(0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/chopper.png");
	}
}
