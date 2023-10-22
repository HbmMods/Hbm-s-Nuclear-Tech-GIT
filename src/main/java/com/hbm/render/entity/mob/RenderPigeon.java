package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityPigeon;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPigeon extends RenderChicken {

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
}
