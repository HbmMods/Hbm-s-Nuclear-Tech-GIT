package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.siege.EntitySiegeTunneler;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.lib.RefStrings;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSiegeTunneler extends Render {

	public RenderSiegeTunneler() {
		this.shadowOpaque = 0.0F;
	}

	public static final IModelCustom body = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/mobs/tunneler.obj"));

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1 - 90, 0.0F, 0.0F, 1.0F);
		
		this.bindEntityTexture(entity);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		body.renderPart("Body");
		GL11.glRotated(System.currentTimeMillis() / 3L % 360, 0, -1, 0);
		body.renderPart("Drill");
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntitySiegeTunneler) entity);
	}

	protected ResourceLocation getEntityTexture(EntitySiegeTunneler entity) {
		SiegeTier tier = entity.getTier();
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/siege_drill_" + tier.name + ".png");
	}
}
