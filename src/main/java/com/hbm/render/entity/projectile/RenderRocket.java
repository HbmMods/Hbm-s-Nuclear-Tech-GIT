package com.hbm.render.entity.projectile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelBullet;

@SideOnly(Side.CLIENT)
public class RenderRocket extends Render {

	private ModelBullet miniNuke;

	public RenderRocket() {
		miniNuke = new ModelBullet();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * p_76986_9_ - 90.0F,
				0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * p_76986_9_ + 180,
				0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		

		GL11.glRotatef(new Random(rocket.getEntityId()).nextInt(360),
				1.0F, 0.0F, 0.0F);

		if (rocket instanceof EntityBullet && ((EntityBullet) rocket).getIsChopper()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png"));
		} else if (rocket instanceof EntityBullet && ((EntityBullet) rocket).getIsCritical()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png"));
		} else if (rocket instanceof EntityBullet) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png"));
		}
		miniNuke.renderAll(0.0625F);
		
		//renderFlechette();
		//renderDart();
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		if (p_110775_1_ instanceof EntityBullet && ((EntityBullet) p_110775_1_).getIsChopper()) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png");
		} else if (p_110775_1_ instanceof EntityBullet && ((EntityBullet) p_110775_1_).getIsCritical()) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png");
		} else if (p_110775_1_ instanceof EntityBullet) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png");
		}

		return null;
	}
}