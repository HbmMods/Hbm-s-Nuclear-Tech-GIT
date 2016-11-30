package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMissileGeneric extends Render {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileV2.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
    private ResourceLocation missileIncendiaryTexture;
    private ResourceLocation missileClusterTexture;
    private ResourceLocation missileBusterTexture;
	
	public RenderMissileGeneric() {
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileV2.png");
		missileIncendiaryTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileIncendiary.png");
		missileClusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileCluster.png");
		missileBusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileBuster.png");
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
        
        if(p_76986_1_ instanceof EntityMissileGeneric)
        	bindTexture(boyTexture);
        if(p_76986_1_ instanceof EntityMissileIncendiary)
        	bindTexture(missileIncendiaryTexture);
        if(p_76986_1_ instanceof EntityMissileCluster)
        	bindTexture(missileClusterTexture);
        if(p_76986_1_ instanceof EntityMissileBunkerBuster)
        	bindTexture(missileBusterTexture);
        boyModel.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID +":textures/models/MissileV2.png");
	}

}
