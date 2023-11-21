package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityPigeon;
import com.hbm.entity.mob.EntityScutterfish;
import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderScutter extends RenderLiving {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/entity/lob2.png");

	public RenderScutter(ModelBase p_i1252_1_, float p_i1252_2_) {
		super(p_i1252_1_, p_i1252_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityScutterfish) entity);
	}

	protected ResourceLocation getEntityTexture(EntityScutterfish entity) {
		return texture;
	}


}
