package com.hbm.render.entity.mob;

import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderMaggot extends RenderLiving {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/entity/parasite_maggot.png");

	public RenderMaggot() {
		super(new ModelSilverfish(), 0.3F);
	}

	@Override
	protected float getDeathMaxRotation(EntityLivingBase entity) {
		return 180.0F;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}
