package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMissileStrong extends Render {
	
	public RenderMissileStrong() { }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
        
        if(p_76986_1_ instanceof EntityMissileStrong)
        	bindTexture(ResourceManager.missileStrong_HE_tex);
        if(p_76986_1_ instanceof EntityMissileIncendiaryStrong)
        	bindTexture(ResourceManager.missileStrong_IN_tex);
        if(p_76986_1_ instanceof EntityMissileClusterStrong)
        	bindTexture(ResourceManager.missileStrong_CL_tex);
        if(p_76986_1_ instanceof EntityMissileBusterStrong)
        	bindTexture(ResourceManager.missileStrong_BU_tex);
        ResourceManager.missileStrong.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.missileStrong_HE_tex;
	}

}
