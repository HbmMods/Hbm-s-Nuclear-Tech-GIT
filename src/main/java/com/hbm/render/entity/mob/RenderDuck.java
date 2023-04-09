package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityDuck;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDuck extends RenderChicken {

	public static final ResourceLocation ducc = new ResourceLocation(RefStrings.MODID, "textures/entity/duck.png");

	public RenderDuck(ModelBase p_i1252_1_, float p_i1252_2_) {
		super(p_i1252_1_, p_i1252_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityDuck) entity);
	}

	protected ResourceLocation getEntityTexture(EntityDuck entity) {
		return ducc;
	}
}
