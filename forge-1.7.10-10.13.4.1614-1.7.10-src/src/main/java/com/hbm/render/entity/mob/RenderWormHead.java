package com.hbm.render.entity.mob;

import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelWormHead;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class RenderWormHead extends RenderLiving {

	public RenderWormHead() {
        super(new ModelWormHead(), 1.0F);
		this.shadowOpaque = 0.0F;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {

        BossStatus.setBossStatus((EntityBOTPrimeHead)entity, true);
        super.doRender(entity, x, y, z, f0, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.universal;
	}
}
