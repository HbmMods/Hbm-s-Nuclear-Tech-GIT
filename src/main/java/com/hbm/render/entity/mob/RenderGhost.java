package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityGhost;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSiegeZombie;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderGhost extends RenderBiped {

	public RenderGhost() {
		super(new ModelSiegeZombie(0.0F), 0.5F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return this.getEntityTexture((EntityGhost) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityGhost) entity);
	}

	protected ResourceLocation getEntityTexture(EntityGhost entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/ghost.png");
	}
}
