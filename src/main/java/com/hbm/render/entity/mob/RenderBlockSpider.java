package com.hbm.render.entity.mob;

import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBlockSpider;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBlockSpider extends RenderLiving {

	public RenderBlockSpider() {
		super(new ModelBlockSpider(), 1.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.spider_tex;
	}
}
