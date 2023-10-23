package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityPigeon;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderPigeon extends RenderLiving {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/entity/pigeon.png");

	public RenderPigeon(ModelBase p_i1252_1_, float p_i1252_2_) {
		super(p_i1252_1_, p_i1252_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityPigeon) entity);
	}

	protected ResourceLocation getEntityTexture(EntityPigeon entity) {
		return texture;
	}
	
	protected float handleRotationFloat(EntityPigeon entity, float interp) {
		float f1 = entity.prevFallTime + (entity.fallTime - entity.prevFallTime) * interp;
		float f2 = entity.prevDest + (entity.dest - entity.prevDest) * interp;
		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entity, float interp) {
		return this.handleRotationFloat((EntityPigeon) entity, interp);
	}
}
