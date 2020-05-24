package com.hbm.render.entity.mob;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelCrab;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCyberCrab extends RenderLiving {

	public RenderCyberCrab() {
        super(new ModelCrab(), 1.0F);
		this.shadowOpaque = 0.0F;
	}

	/*@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(180, 1, 0, 0);
		
		bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/Crab.png"));
		
		mine2.renderAll(0.0625F);
		GL11.glPopMatrix();
	}*/

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/Crab.png");
	}
}
