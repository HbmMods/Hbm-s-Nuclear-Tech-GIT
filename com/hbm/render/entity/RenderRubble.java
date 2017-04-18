package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelChopperMine;
import com.hbm.render.model.ModelRubble;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRubble extends Render {
	
	ModelRubble mine;

	public RenderRubble() {
		mine = new ModelRubble();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef((rocket.ticksExisted % 360) * 10, 1, 1, 1);
		
		byte b = rocket.getDataWatcher().getWatchableObjectByte(16);

		if(b == 0)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleMetal.png"));
		if(b == 1)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleRock.png"));
		if(b == 2)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubblePlant.png"));
		if(b == 3)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleSand.png"));
		if(b == 4)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleGround.png"));
		if(b == 5)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleWood.png"));
		if(b == 6)
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleScrap.png"));
		
		mine.renderAll(0.0625F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleScrap.png");
	}
}
