package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelMaskMan;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class RenderMaskMan extends RenderLiving {

	public RenderMaskMan() {
        super(new ModelMaskMan(), 1.0F);
		this.shadowOpaque = 0.0F;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
        BossStatus.setBossStatus((EntityMaskMan)entity, true);
        super.doRender(entity, x, y, z, f0, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.maskman_tex;
	}

}
