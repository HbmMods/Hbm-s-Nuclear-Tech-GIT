package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.item.EntityBoatRubber;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBoatRubber extends Render {

	private static final ResourceLocation boatTextures = new ResourceLocation(RefStrings.MODID + ":textures/entity/boat_rubber.png");
	protected ModelBase modelBoat;

	public RenderBoatRubber() {
		this.shadowSize = 0.5F;
		this.modelBoat = new ModelBoat();
	}

	public void doRender(EntityBoatRubber entity, double x, double y, double z, float yaw, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
		float f2 = (float) entity.getTimeSinceHit() - interp;
		float f3 = entity.getDamageTaken() - interp;

		if(f3 < 0.0F) {
			f3 = 0.0F;
		}

		if(f2 > 0.0F) {
			GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0F * (float) entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}
		
		EntityPlayer me = Minecraft.getMinecraft().getMinecraft().thePlayer;
		
		if(entity.riddenByEntity == me) {
			float diff = MathHelper.wrapAngleTo180_float(entity.rotationYaw - entity.prevRenderYaw);
			me.rotationYaw += diff;
			me.rotationYawHead += diff;
		}
		
		entity.prevRenderYaw = entity.rotationYaw;

		float f4 = 0.75F;
		GL11.glScalef(f4, f4, f4);
		GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
		this.bindEntityTexture(entity);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.modelBoat.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(EntityBoatRubber entity) {
		return boatTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityBoatRubber) entity);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		this.doRender((EntityBoatRubber) entity, x, y, z, f0, f1);
	}
}
