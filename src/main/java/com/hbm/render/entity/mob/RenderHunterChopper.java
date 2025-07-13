package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelHunterChopper;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class RenderHunterChopper extends Render {

	private static final ResourceLocation chopperTexture = new ResourceLocation(RefStrings.MODID + ":textures/entity/chopper.png");
    private final ModelHunterChopper chopperModel = new ModelHunterChopper();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {

        BossStatus.setBossStatus((EntityHunterChopper) entity, true);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.0625F * 0, 0.0625F * 32, 0.0625F * 0);
        GL11.glTranslatef(0.0625F * 0, 0.0625F * 12, 0.0625F * 0);
        GL11.glScalef(4F, 4F, 4F);
        GL11.glRotatef(180, 1, 0, 0);

        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0, 1.0F, 0);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0, 0, 1.0F);

        this.bindTexture(chopperTexture);

        //if(rocket instanceof EntityHunterChopper)
        //    mine2.setGunRotations((EntityHunterChopper)rocket, yaw, pitch);

		this.chopperModel.renderAll(0.0625F);
        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {

		return chopperTexture;
	}
}
