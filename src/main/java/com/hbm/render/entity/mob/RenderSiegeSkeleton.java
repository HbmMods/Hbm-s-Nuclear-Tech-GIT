package com.hbm.render.entity.mob;

import com.hbm.entity.mob.siege.EntitySiegeSkeleton;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderSiegeSkeleton extends RenderBiped {
	
	public RenderSiegeSkeleton() {
		super(new ModelSkeleton() {
			
			@Override
			public void setLivingAnimations(EntityLivingBase entity, float f0, float f1, float f2) {
				this.aimedBow = true;
			}
		}, 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return this.getEntityTexture((EntitySiegeSkeleton) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntitySiegeSkeleton) entity);
	}

	protected ResourceLocation getEntityTexture(EntitySiegeSkeleton entity) {
		SiegeTier tier = entity.getTier();
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/siege_" + tier.name + ".png");
	}
}
