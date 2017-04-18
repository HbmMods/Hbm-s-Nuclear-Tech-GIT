package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMissileThermo extends Render {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileThermal.obj");
	private IModelCustom boyModel;
    private ResourceLocation missileThermoEndoTexture;
    private ResourceLocation missileThermoExoTexture;
	
	public RenderMissileThermo() {
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		missileThermoEndoTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileThermalEndo.png");
		missileThermoExoTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileThermalExo.png");
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
        
		if(p_76986_1_ instanceof EntityMissileEndo)
			bindTexture(missileThermoEndoTexture);
		if(p_76986_1_ instanceof EntityMissileExo)
			bindTexture(missileThermoExoTexture);
        boyModel.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID +":textures/models/MissileThermal.png");
	}

}
