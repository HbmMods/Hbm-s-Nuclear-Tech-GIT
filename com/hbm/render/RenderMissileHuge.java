package com.hbm.render;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.EntityMissileBunkerBuster;
import com.hbm.entity.EntityMissileBurst;
import com.hbm.entity.EntityMissileCluster;
import com.hbm.entity.EntityMissileDrill;
import com.hbm.entity.EntityMissileGeneric;
import com.hbm.entity.EntityMissileIncendiary;
import com.hbm.entity.EntityMissileInferno;
import com.hbm.entity.EntityMissileRain;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMissileHuge extends Render {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/MissileHuge.obj");
	private IModelCustom boyModel;
    private ResourceLocation boyTexture;
    private ResourceLocation missileIncendiaryTexture;
    private ResourceLocation missileClusterTexture;
    private ResourceLocation missileBusterTexture;
	
	public RenderMissileHuge() {
		boyModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		boyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHuge.png");
		missileIncendiaryTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHugeIncendiary.png");
		missileClusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHugeCluster.png");
		missileBusterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/MissileHugeBuster.png");
	}

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glScalef(2F, 2F, 2F);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);

        if(p_76986_1_ instanceof EntityMissileBurst)
        	bindTexture(boyTexture);
        if(p_76986_1_ instanceof EntityMissileInferno)
        	bindTexture(missileIncendiaryTexture);
        if(p_76986_1_ instanceof EntityMissileRain)
        	bindTexture(missileClusterTexture);
        if(p_76986_1_ instanceof EntityMissileDrill)
        	bindTexture(missileBusterTexture);
        boyModel.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID +":textures/models/MissileHuge.png");
	}
}
