package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelTaintCrab;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderTaintCrab extends RenderLiving {

	public RenderTaintCrab() {
        super(new ModelTaintCrab(), 1.0F);
		this.shadowOpaque = 0.0F;
	}
	
    public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
    	
    	if(entity instanceof EntityTaintCrab) {
    		GL11.glPushMatrix();
            GL11.glTranslated(x, y + 1.25, z);

            double sx = entity.posX;
            double sy = entity.posY + 1.25;
            double sz = entity.posZ;
            
            for(double[] target : ((EntityTaintCrab)entity).targets) {
            	
            	double length = Math.sqrt(Math.pow(target[0] - sx, 2) + Math.pow(target[1] - sy, 2) + Math.pow(target[2] - sz, 2));
            	
    	        BeamPronter.prontBeam(Vec3.createVectorHelper(target[0] - sx, target[1] - sy, target[2] - sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x404040, (int) (entity.worldObj.getTotalWorldTime() % 1000 + 1), (int) (length * 5), 0.125F, 2, 0.03125F, 256);
            }
            
    		GL11.glPopMatrix();
    	}
    	
        super.doRender(entity, x, y, z, p_76986_8_, p_76986_9_);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.taintcrab_tex;
	}
}
