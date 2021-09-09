package com.hbm.render.entity.mob;

import com.hbm.entity.mob.siege.EntitySiegeZombie;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderSiegeZombie extends RenderBiped {

	public RenderSiegeZombie() {
		super(new ModelZombie(), 0.5F, 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return this.getEntityTexture((EntitySiegeZombie) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntitySiegeZombie) entity);
	}

	protected ResourceLocation getEntityTexture(EntitySiegeZombie entity) {
		SiegeTier tier = entity.getTier();
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/siege_" + tier.name + ".png");
	}

	@Override
	protected void func_82421_b() {
		this.field_82423_g = new ModelZombie(1.0F, true);	//armor slots 1, 2, 4
		this.field_82425_h = new ModelZombie(0.5F, true);	//armor slot 3
	}
}
