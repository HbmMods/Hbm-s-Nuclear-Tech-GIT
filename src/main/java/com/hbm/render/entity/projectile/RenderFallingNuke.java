package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderFallingNuke extends Render {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(RefStrings.MODID, "models/LilBoy1.obj");
	private IModelCustom boyModel;
	private ResourceLocation boyTexture;

	public RenderFallingNuke() {
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/CustomNuke.png");
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);

		switch(p_76986_1_.getDataWatcher().getWatchableObjectByte(20)) {
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glTranslated(-2.0D, 0.0D, 0.0D);
			break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F);
			GL11.glTranslated(-2.0D, 0.0D, 0.0D);
			break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F);
			GL11.glTranslated(-2.0D, 0.0D, 0.0D);
			break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F);
			GL11.glTranslated(-2.0D, 0.0D, 0.0D);
			break;
		}

		float f = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;

		if(f < -80)
			f = 0;

		// GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw -
		// p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f, 0.0F, 0.0F, 1.0F);

		bindTexture(boyTexture);
		boyModel.renderAll();

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/TheGadget3_.png");
	}
}
